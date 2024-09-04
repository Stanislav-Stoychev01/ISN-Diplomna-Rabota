package com.graduationproject.isn.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoogleApiConstants {

    public static final String GOOGLE_API_BASE_URL = "https://www.googleapis.com/customsearch/v1?key=";

    public static final String GOOGLE_ENGINE_QUERY_PARAM = "&cx=";

    public static final String GOOGLE_SEARCH_INPUT_QUERY_PARAM = "&q=";

    public static final String GOOGLE_SEARCH_INPUT_RESULT_ENHANCEMENT = "%20цена";

    public static final String GOOGLE_SEARCH_RESULT = "&num=10";

    public static final String GOOGLE_GEOLOCATION = "&gl=bg";

    public static final String GOOGLE_INTERFACE_LANGUAGE = "&hl=bg";

    public static final String GOOGLE_COUNTRY_ORIGIN = "&cr=countryBG";

    public static final String GOOGLE_DOCUMENT_LANGUAGE = "&lr=lang_bg";

}
