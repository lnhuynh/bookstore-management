package com.lnhuynh.bookstoremanagement.configs;

import com.lnhuynh.bookstoremanagement.repositories.AccountRepository;
import com.lnhuynh.bookstoremanagement.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AccountRepository accountRepository;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  @Value("${spring.baseUrl}")
  private String BASE_URL;

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserInfoService(accountRepository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository() {
    return new InMemoryClientRegistrationRepository(getGithubClientRegistration());
  }

  private ClientRegistration getGithubClientRegistration() {
    return ClientRegistration.withRegistrationId("github")
      .clientId("4a966f061d96b0a342be")
      .clientSecret("d9fd667b73c3630c82312b909aaa6cdb594b2bef")
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .redirectUri(String.format("%s/login/oauth2/code/github", BASE_URL))
      .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
      .authorizationUri("https://github.com/login/oauth/authorize")
      .tokenUri("https://github.com/login/oauth/access_token")
      .userInfoUri("https://api.github.com/user")
      .userNameAttributeName("login")
      .clientName("GitHub")
      .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(HttpMethod.GET, "/hello").permitAll()
        .requestMatchers(HttpMethod.POST, "/auth/sign-up", "/role/create").permitAll()
      )
      .authorizeHttpRequests(authorize -> authorize.requestMatchers("/book/**").authenticated())
      .oauth2Login(oauth2Login -> oauth2Login.successHandler(oAuth2SuccessHandler))
//      .formLogin(formLogin -> formLogin.defaultSuccessUrl("/book"))
      .build();
  }
}
