package com.lnhuynh.bookstoremanagement.configs;

import com.lnhuynh.bookstoremanagement.constants.RoleName;
import com.lnhuynh.bookstoremanagement.domain.Account;
import com.lnhuynh.bookstoremanagement.domain.Role;
import com.lnhuynh.bookstoremanagement.repositories.AccountRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.lnhuynh.bookstoremanagement.constants.Platform.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  private final AccountRepository accountRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
    OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
    String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
    switch (authorizedClientRegistrationId) {
      case GITHUB -> {
        Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
        String username = attributes.getOrDefault("login", "").toString();
        String password = attributes.getOrDefault("id", "r@nd0m.pa$$w0rd").toString();
        accountRepository.findByUsername(username).ifPresentOrElse(
          (user) -> {
            setSecurityContextHolder(user, attributes, authorizedClientRegistrationId);
          },
          () -> {
            List<Role> roleList = new ArrayList<>();
            roleList.add(new Role(RoleName.ADMIN));
            Account newAccount = Account.builder()
              .username(username)
              .password(password)
              .roleList(roleList)
              .build();
            accountRepository.save(newAccount);
            setSecurityContextHolder(newAccount, attributes, authorizedClientRegistrationId);
          }
        );
        ResponseCookie springCookie = ResponseCookie.from("jwtToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
          .httpOnly(true)
          .secure(false)
          .path("/")
          .maxAge(86400)
          .build();
        response.addHeader(HttpHeaders.SET_COOKIE, springCookie.toString());
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:3000");
        super.onAuthenticationSuccess(request, response, authentication);
      }
      case GOOGLE -> {
        log.info("gg");
      }
      case FACEBOOK -> {
        log.info("fb");
      }
    }
  }

  private void setSecurityContextHolder(Account account, Map<String, Object> attributes, String authorizedClientRegistrationId) {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    account.getRoleList().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
    });
    DefaultOAuth2User newUser = new DefaultOAuth2User(authorities, attributes, "login");
    Authentication newAuthentication = new OAuth2AuthenticationToken(newUser, authorities, authorizedClientRegistrationId);
    SecurityContextHolder.getContext().setAuthentication(newAuthentication);
  }
}
