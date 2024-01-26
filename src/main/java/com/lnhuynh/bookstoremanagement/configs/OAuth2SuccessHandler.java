package com.lnhuynh.bookstoremanagement.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
    log.info("AUTHENTICATION BY GITHUB SUCCESSFULLY");
    ResponseCookie springCookie = ResponseCookie.from("jwtToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
      .httpOnly(true)
      .secure(false)
      .path("/")
      .maxAge(86400)
      .build();
    // Add the JWT token cookie to the response
    response.addHeader(HttpHeaders.SET_COOKIE, springCookie.toString());
    log.info("SETTING REDIRECTING URL");
    this.setAlwaysUseDefaultTargetUrl(true);
    this.setDefaultTargetUrl("http://localhost:3000");
    super.onAuthenticationSuccess(request, response, authentication);
  }
}
