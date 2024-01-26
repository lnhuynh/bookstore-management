package com.lnhuynh.bookstoremanagement.configs;

import com.lnhuynh.bookstoremanagement.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AccountUserDetails implements UserDetails {

  private String username;
  private String password;
  private String authorities;

  public AccountUserDetails(Account account) {
    this.username = account.getUsername();
    this.password = account.getPassword();
//    Optional<Role> resultRole = roleRepository.findById(account.getRoleList().get(0).getId());
//    resultRole.ifPresent(role -> this.authorities = role.getRoleName());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
