package com.graduationproject.isn.domain.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthConstants {

    public static final String NO_AUTH_PATHS = "/identity/**";

    public static final String AUTH_HEADER = "Authorization";

    public static final String SIGNED_TOKEN_PREFIX = "Bearer ";

}
