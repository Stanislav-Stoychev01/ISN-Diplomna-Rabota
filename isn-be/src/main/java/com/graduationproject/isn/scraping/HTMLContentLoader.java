package com.graduationproject.isn.scraping;

import com.graduationproject.isn.domain.constants.ScrapingConstants;
import com.graduationproject.isn.domain.enums.errorreasons.GoogleApiErrorReason;
import com.graduationproject.isn.exceptions.APIException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class HTMLContentLoader {

    private final WebDriver webDriver;

    public WebDriver loadData(String url) {
        webDriver.get(url);

        try {
            WebDriverWait waitTime = new WebDriverWait(webDriver, Duration.ofSeconds(3));
            waitTime.until(ExpectedConditions.presenceOfElementLocated(
                By.tagName(ScrapingConstants.HTML_BODY_TAG_NAME)));
        } catch (Exception ex) {
            throw new APIException(GoogleApiErrorReason.INTERRUPTED, HttpStatus.REQUEST_TIMEOUT);
        }

        return webDriver;
    }

}
