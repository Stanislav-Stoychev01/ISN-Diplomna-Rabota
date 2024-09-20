package com.graduationproject.isn.scraping;

import com.graduationproject.isn.domain.entity.WebsiteEntity;
import com.graduationproject.isn.domain.records.response.ScrapedProduct;
import com.graduationproject.isn.domain.records.response.ScrapedProductsResponse;
import com.graduationproject.isn.scraping.strategies.ScrapingStrategy;
import com.graduationproject.isn.services.GoogleSearchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScrapingExecutorImpl implements ScrapingExecutor {

    private final GoogleSearchService googleSearchService;

    private final HTMLContentLoader htmlContentLoader;

    private final List<ScrapingStrategy> scrapingStrategies;

    @Override
    @Transactional
    public ScrapedProductsResponse scrape(String productName) {
        Set<ScrapedProduct> scrapedProducts = new HashSet<>();
        Collection<WebsiteEntity> foundWebsites = googleSearchService.getSearchingUrls(productName);

        for (WebsiteEntity website : foundWebsites) {
            try {
                String foundUrl = website.getUrl();
                WebDriver htmlContent = htmlContentLoader.loadData(foundUrl);

                // Output is null given that content was not parsable.
                if (htmlContent != null) {
                    ScrapedProduct scrapedProduct = extractResultSet(htmlContent, productName);
                    scrapedProducts.add(scrapedProduct);
                }
            } catch (Exception e) {
                int a = 1;
            }
        }

        return new ScrapedProductsResponse(scrapedProducts);
    }

    private ScrapedProduct extractResultSet(WebDriver webDriver, String productName) {
        ScrapedProduct result = null;

        for (ScrapingStrategy strategy : scrapingStrategies) {
            result = strategy.scrapeContent(webDriver, productName);
            if (result != null) {
                break;
            }
        }

        return result;
    }

}
