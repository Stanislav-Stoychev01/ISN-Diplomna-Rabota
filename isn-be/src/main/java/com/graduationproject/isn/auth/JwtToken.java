package com.graduationproject.isn.auth;

import com.graduationproject.isn.util.JwtSigningUtil;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtToken extends AbstractAuthenticationToken {

    private final String jwtToken;

    public JwtToken(String authToken) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.jwtToken = authToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return JwtSigningUtil.extractUsername(this.jwtToken);
    }
}
