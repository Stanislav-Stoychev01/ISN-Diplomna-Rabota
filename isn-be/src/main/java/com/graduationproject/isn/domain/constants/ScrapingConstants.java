package com.graduationproject.isn.domain.constants;

import com.graduationproject.isn.scraping.strategies.ScrapingStrategy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrapingConstants {

    public static String HTML_BODY_TAG_NAME = "body";

    public static String HTML_PRODUCT_FULL_NAME = "h1";

    public static String HTML_PRICE_CLASS_NAME_PATTERN = "div[class*='price']";

    public static String HTML_SUPERSCRIPT = "sup";

}
