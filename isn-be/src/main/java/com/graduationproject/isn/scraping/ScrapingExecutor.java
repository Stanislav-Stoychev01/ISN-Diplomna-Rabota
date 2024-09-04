package com.graduationproject.isn.scraping;

import com.graduationproject.isn.domain.records.response.ScrapedProductsResponse;

public interface ScrapingExecutor {

    ScrapedProductsResponse scrape(String productName);

}
