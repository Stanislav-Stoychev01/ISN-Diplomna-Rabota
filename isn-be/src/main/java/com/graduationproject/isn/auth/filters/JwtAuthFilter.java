package com.graduationproject.isn.auth.filters;

import com.graduationproject.isn.auth.JwtToken;
import com.graduationproject.isn.domain.constants.AuthConstants;
import com.graduationproject.isn.domain.entity.IdentityEntity;
import com.graduationproject.isn.domain.enums.errorreasons.AuthErrorReason;
import com.graduationproject.isn.exceptions.APIException;
import com.graduationproject.isn.repositories.IdentityRepository;
import com.graduationproject.isn.util.JwtSigningUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final IdentityRepository identityRepository;

    private final RequestMatcher requestMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            String jwtToken = null;
            String jwtUsernameClaim;

            String authHeader = request.getHeader(AuthConstants.AUTH_HEADER);

            if (authHeader != null && authHeader.startsWith(AuthConstants.SIGNED_TOKEN_PREFIX)) {
                jwtToken = authHeader.substring(7);
                jwtUsernameClaim = JwtSigningUtil.extractUsername(jwtToken);

                if (SecurityContextHolder.getContext().getAuthentication() == null
                    && jwtUsernameClaim != null) {
                    IdentityEntity identityEntity = getAssociatedIdentity(jwtUsernameClaim);
                    boolean isValidUsername = JwtSigningUtil.extractUsername(jwtToken)
                        .equals(identityEntity.getUsername());

                    if (isValidUsername && !JwtSigningUtil.isTokenExpired(jwtToken)) {
                        Authentication auth = new JwtToken(jwtToken);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        throw new APIException(AuthErrorReason.INVALID_AUTHENTICATION, HttpStatus.UNAUTHORIZED);
                    }
                }
            } else {
                throw new APIException(AuthErrorReason.INVALID_AUTHENTICATION, HttpStatus.UNAUTHORIZED);
            }
        }

        filterChain.doFilter(request, response);
    }

    private IdentityEntity getAssociatedIdentity(String username) {
        return identityRepository.findByUsername(username)
            .orElseThrow(() -> new APIException(
                AuthErrorReason.INVALID_LOGIN_CREDENTIALS, HttpStatus.UNAUTHORIZED));
    }
}
