package com.graduationproject.isn.scraping.strategies;

import com.graduationproject.isn.domain.constants.ScrapingConstants;
import com.graduationproject.isn.domain.records.response.ScrapedProduct;
import org.openqa.selenium.WebDriver;

public interface ScrapingStrategy {

    ScrapedProduct scrapeContent(WebDriver webDriver, String productName);

}
