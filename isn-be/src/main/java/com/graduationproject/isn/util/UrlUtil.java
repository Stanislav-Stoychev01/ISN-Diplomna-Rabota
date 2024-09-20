package com.graduationproject.isn.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlUtil {

    public static String parseToUrlQueryParamFormat(String url) {
        return url.replace(" ", "%20");
    }

}
