package com.graduationproject.isn.controllers;

import com.graduationproject.isn.domain.records.response.ScrapedProductsResponse;
import com.graduationproject.isn.services.ScrapingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/scraper")
public class ScrapingController {

    private final ScrapingService scrapingService;

    // TODO: Consider header that extracts the current geolocation.
    @GetMapping("/{productName}")
    public ResponseEntity<ScrapedProductsResponse> getScrapedInformation(
        @PathVariable("productName") String productName) {
        return new ResponseEntity<>(scrapingService.scrapedProductResponse(productName), HttpStatus.OK);
    }
}
