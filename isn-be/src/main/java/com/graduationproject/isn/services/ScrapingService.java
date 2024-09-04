package com.graduationproject.isn.services;

import com.graduationproject.isn.domain.records.response.ScrapedProductsResponse;
import com.graduationproject.isn.scraping.ScrapingExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScrapingService {

    private final ScrapingExecutor scrapingExecutor;

    public ScrapedProductsResponse scrapedProductResponse(String productName) {
        return scrapingExecutor.scrape(productName);
    }

}
