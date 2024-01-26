package com.lnhuynh.bookstoremanagement.services;

import com.lnhuynh.bookstoremanagement.configs.AccountUserDetails;
import com.lnhuynh.bookstoremanagement.domain.Account;
import com.lnhuynh.bookstoremanagement.repositories.AccountRepository;
import com.lnhuynh.bookstoremanagement.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

  private final AccountRepository accountRepository;
//  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Account> resultAccount = accountRepository.findByUsername(username);
    return resultAccount.map(AccountUserDetails::new)
      .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }
}
