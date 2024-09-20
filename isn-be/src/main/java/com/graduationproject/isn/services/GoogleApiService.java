package com.graduationproject.isn.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.graduationproject.isn.domain.constants.GoogleApiConstants;
import com.graduationproject.isn.domain.entity.WebsiteEntity;
import com.graduationproject.isn.domain.enums.errorreasons.GoogleApiErrorReason;
import com.graduationproject.isn.exceptions.APIException;
import com.graduationproject.isn.mappers.WebsiteMapper;
import com.graduationproject.isn.repositories.WebsiteRepository;
import com.graduationproject.isn.util.UrlUtil;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class GoogleApiService {

    private static final String URL_TARGET_PROPERTY = "link";

    private static final List<String> BLACKLIST = List.of("pazaruvaj.com");

    @Value("${google.search-engine.api.key}")
    private String apiKey;

    @Value("${google.search-engine.id}")
    private String engineId;

    private final Gson gson;

    private final WebsiteRepository websiteRepository;

    public String getGoogleApiUrl(String searchInput) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(GoogleApiConstants.GOOGLE_API_BASE_URL);
        stringBuilder.append(apiKey);
        stringBuilder.append(GoogleApiConstants.GOOGLE_ENGINE_QUERY_PARAM);
        stringBuilder.append(engineId);
        stringBuilder.append(GoogleApiConstants.GOOGLE_SEARCH_INPUT_QUERY_PARAM);
        stringBuilder.append(UrlUtil.parseToUrlQueryParamFormat(searchInput));
        stringBuilder.append(GoogleApiConstants.GOOGLE_SEARCH_INPUT_RESULT_ENHANCEMENT);
        stringBuilder.append(GoogleApiConstants.GOOGLE_GEOLOCATION);
        stringBuilder.append(GoogleApiConstants.GOOGLE_INTERFACE_LANGUAGE);
        stringBuilder.append(GoogleApiConstants.GOOGLE_COUNTRY_ORIGIN);
        stringBuilder.append(GoogleApiConstants.GOOGLE_DOCUMENT_LANGUAGE);
        stringBuilder.append(GoogleApiConstants.GOOGLE_SEARCH_RESULT);

        return stringBuilder.toString();
    }

    public Collection<WebsiteEntity> loadAssociatedUrls(String googleSearchUrl, String targetProduct) {
        // TODO: This is slow operation so maybe consider timeout?
        Collection<WebsiteEntity> websiteEntities = websiteRepository
            .findAllByProductsName(targetProduct)
            .stream()
            .filter(websiteEntity -> isBlacklistUrl(websiteEntity.getUrl()))
            .toList();

        if (Collections.isEmpty(websiteEntities)) {
            websiteEntities = scrapeWebForWebsites(googleSearchUrl);

            websiteEntities = websiteEntities
                .stream()
                .filter(websiteEntity -> isBlacklistUrl(websiteEntity.getUrl()))
                .toList();
        }

        return websiteEntities;
    }

    private Collection<WebsiteEntity> scrapeWebForWebsites(String googleSearchUrl) {
        Collection<WebsiteEntity> foundUrls = new ArrayList<>();

        try {
            InputStreamReader foundContent = getTargetContent(googleSearchUrl);
            BufferedReader bufferedReader = new BufferedReader(foundContent);
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(currentLine);
            }

            JsonElement jsonContent = gson.fromJson(stringBuilder.toString(), JsonElement.class);

            getFoundKeyValues(jsonContent, URL_TARGET_PROPERTY)
                .stream()
                .filter((url) -> isBlacklistUrl(
                    url.toString()))
                .forEach((url) -> foundUrls.add(
                    WebsiteMapper.INSTANCE.toWebsiteEntity(url.toString())));
        } catch (IOException exception) {
            throw new APIException(GoogleApiErrorReason.CONNECTION_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);
        }

        return foundUrls;
    }

    private InputStreamReader getTargetContent(String googleSearchUrl) {
        InputStreamReader foundContent;

        try {
            URL url = new URL(googleSearchUrl);
            HttpURLConnection googleApiConnection = (HttpURLConnection) url.openConnection();
            googleApiConnection.setRequestMethod(HttpMethod.GET.name());

            foundContent = new InputStreamReader(googleApiConnection.getInputStream());
        } catch (IOException exception) {
            throw new APIException(GoogleApiErrorReason.CONNECTION_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE);
        }

        return foundContent;
    }

    private Collection<JsonElement> getFoundKeyValues(JsonElement jsonContent, String key) {
        Collection<JsonElement> foundKeyValues = new ArrayList<>();

        if (jsonContent.isJsonObject()) {
            JsonObject jsonObject = jsonContent.getAsJsonObject();
            if (jsonObject.has(key)) {
                foundKeyValues.add(jsonObject.get(key));
            }

            for (String jsonKeys : jsonObject.keySet()) {
                Collection<JsonElement> nestedJson =
                    getFoundKeyValues(jsonObject.get(jsonKeys), key);
                foundKeyValues.addAll(nestedJson);
            }
        } else if (jsonContent.isJsonArray()) {
            JsonArray jsonArray = jsonContent.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                Collection<JsonElement> nestedJson =
                    getFoundKeyValues(element, key);
                foundKeyValues.addAll(nestedJson);
            }
        }

        return foundKeyValues;
    }

    private boolean isBlacklistUrl(String url) {
        AtomicBoolean isBlackListed = new AtomicBoolean(true);
        BLACKLIST.forEach((blacklitedUrl) -> {
            if (url.contains(blacklitedUrl)) {
                isBlackListed.set(false);
            }
        });
        return isBlackListed.get();
    }
}
