package com.lnhuynh.bookstoremanagement.controllers;

import com.lnhuynh.bookstoremanagement.dtos.AccountCreateRequestDTO;
import com.lnhuynh.bookstoremanagement.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {
  private final AccountService accountService;
  private final OAuth2AuthorizedClientService authorizedClientService;

  @PostMapping("/sign-up")
  public String signUp(@RequestBody @Valid AccountCreateRequestDTO accountInfo) {
    return accountService.signUp(accountInfo);
  }
}
