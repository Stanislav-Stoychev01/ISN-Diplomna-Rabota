package com.graduationproject.isn.services;

import com.graduationproject.isn.domain.entity.WebsiteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GoogleSearchService {

    private final GoogleApiService googleApiService;

    public Collection<WebsiteEntity> getSearchingUrls(String searchInput) {
        String googleSearchApiUrl = googleApiService.getGoogleApiUrl(searchInput);
        return googleApiService.loadAssociatedUrls(googleSearchApiUrl, searchInput);
    }

}
