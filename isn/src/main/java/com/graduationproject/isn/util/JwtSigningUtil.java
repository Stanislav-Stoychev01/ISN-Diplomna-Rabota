package com.graduationproject.isn.util;

import com.graduationproject.isn.domain.enums.errorreasons.AuthErrorReason;
import com.graduationproject.isn.exceptions.APIException;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtSigningUtil {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private static final Key SECRET_KEY = Keys.secretKeyFor(SIGNATURE_ALGORITHM);

    private static final int VALIDITY_IN_MINUTES = 30;

    public static String generateJWT(String username) {
        Instant issuedAt = Instant.now();
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(issuedAt))
            .setExpiration(Date.from(issuedAt.plus(VALIDITY_IN_MINUTES, ChronoUnit.MINUTES)))
            .signWith(SECRET_KEY, SIGNATURE_ALGORITHM)
            .compact();
    }

    public static String extractUsername(String token) {
        return getTokenPayload(token).getSubject();
    }

    public static Date extractExpirationOffset(String token) {
        return getTokenPayload(token).getExpiration();
    }

    public static boolean isTokenExpired(String token) {
        Date currentDate = new Date();
        Claims claims = getTokenPayload(token);
        return claims.getExpiration().before(currentDate);
    }

    public static Claims getTokenPayload(String token) throws ExpiredJwtException, UnsupportedJwtException {
        int i = token.lastIndexOf(".");
        String withoutSignature = token.substring(0, i + 1);
        return Jwts.parserBuilder().build()
            .parseClaimsJwt(withoutSignature)
            .getBody();
    }

}
