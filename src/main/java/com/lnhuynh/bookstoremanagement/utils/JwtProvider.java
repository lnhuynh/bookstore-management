package com.lnhuynh.bookstoremanagement.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.lnhuynh.bookstoremanagement.constants.DateRange.ONE_MONTH_IN_MILLISECOND;

@Component
public class JwtProvider {
  private SecretKey secretKey;

  public String generateToken(Authentication authentication) {
    DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();
    Date expirationDate = new Date(System.currentTimeMillis() + ONE_MONTH_IN_MILLISECOND);
    secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    return Jwts.builder()
      .setSubject(principal.getAttribute("login"))
      .claim("avatar_url", principal.getAttribute("avatar_url"))
      .setIssuedAt(new Date())
      .setExpiration(expirationDate)
      .signWith(secretKey)
      .compact();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException ex) {
    } catch (ExpiredJwtException ex) {
    } catch (UnsupportedJwtException ex) {
    } catch (IllegalArgumentException ex) {
    }
    return false;
  }
}
