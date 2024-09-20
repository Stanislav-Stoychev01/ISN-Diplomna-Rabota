package com.graduationproject.isn.scraping.strategies;

import com.graduationproject.isn.domain.constants.ScrapingConstants;
import com.graduationproject.isn.domain.entity.ProductEntity;
import com.graduationproject.isn.domain.records.response.ScrapedProduct;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class DivScrapingStrategy implements ScrapingStrategy {

    @Override
    public ScrapedProduct scrapeContent(WebDriver webDriver, String productName) {
        ProductEntity productEntity;

        String productTitle = findProductTitle(webDriver, productName);
        Double productPrice = findProductPrice(webDriver);
        String productPicturePath = findAndStoreProductPicture(webDriver, productName);

        if (productTitle != null && productPrice != null && productPicturePath != null) {
            productEntity = new ProductEntity();
        }

        return new ScrapedProduct();
    }

    private String findProductTitle(WebDriver webDriver, String productName) {
        String productTitle = null;
        List<WebElement> foundTitles = webDriver.findElements(By.tagName(
            ScrapingConstants.HTML_PRODUCT_FULL_NAME));

        for (WebElement title : foundTitles) {
            if (title.getText().contains(productName)) {
                productTitle = title.getText();
            }
        }

        return productTitle;
    }

    private Double findProductPrice(WebDriver webDriver) {
        Double productPrice = null;

        List<WebElement> priceElements = webDriver.findElements(
            By.cssSelector(ScrapingConstants.HTML_PRICE_CLASS_NAME_PATTERN));

        for (WebElement priceElement : priceElements) {
            WebElement supElement = priceElement.findElement(
                By.tagName(ScrapingConstants.HTML_SUPERSCRIPT));

            String priceText = priceElement.getText();
            String supText = supElement.getText();

            if (priceText != null && supText != null) {
                int basePrice = Integer.parseInt(priceText);
                double decimalPrice = Integer.parseInt(priceText);

                productPrice = basePrice + decimalPrice;
            }

            if (productPrice != null) {
                break;
            }
        }

        return productPrice;
    }

    private String findAndStoreProductPicture(WebDriver webDriver, String productName) {
        throw new NotImplementedException();
    }
}
