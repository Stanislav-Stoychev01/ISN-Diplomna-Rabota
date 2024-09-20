package com.graduationproject.isn.configs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver webDriver() {
        WebDriverManager.chromedriver().browserVersion("128.0.6613.137").setup();
        return new ChromeDriver();
    }

}
