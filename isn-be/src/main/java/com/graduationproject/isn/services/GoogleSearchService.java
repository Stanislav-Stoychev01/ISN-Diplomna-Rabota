package com.graduationproject.isn.services;

import com.graduationproject.isn.domain.entity.WebsiteEntity;
import com.graduationproject.isn.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GoogleSearchService {

    private final GoogleApiService googleApiService;

    private final ProductRepository productRepository;

    public Set<String> getSearchingUrls(String searchInput) {
        Set<String> uniqueWebsites = new HashSet<>();

        try {
            String googleSearchApiUrl = googleApiService.getGoogleApiUrl(searchInput);
            Collection<WebsiteEntity> searchResult = googleApiService
                .performGoogleSearch(googleSearchApiUrl, searchInput);
            int a = 1;
        } catch (Throwable throwable) {

        }

        return uniqueWebsites;
    }
}
