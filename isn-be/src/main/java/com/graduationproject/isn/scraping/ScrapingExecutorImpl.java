package com.graduationproject.isn.scraping;

import com.graduationproject.isn.domain.records.response.ScrapedProduct;
import com.graduationproject.isn.domain.records.response.ScrapedProductsResponse;
import com.graduationproject.isn.services.GoogleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScrapingExecutorImpl implements ScrapingExecutor {

    private final List<WebsiteScraper> scrapers;

    private final GoogleSearchService googleSearchService;

    @Override
    public ScrapedProductsResponse scrape(String productName) {
        Set<String> foundUrls = googleSearchService.getSearchingUrls(productName);

        Set<ScrapedProduct> scrapedProducts = new HashSet<>();
        return new ScrapedProductsResponse(scrapedProducts);
    }

}
