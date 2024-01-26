package com.lnhuynh.bookstoremanagement.services;


import com.lnhuynh.bookstoremanagement.domain.Account;
import com.lnhuynh.bookstoremanagement.domain.Role;
import com.lnhuynh.bookstoremanagement.dtos.AccountCreateRequestDTO;
import com.lnhuynh.bookstoremanagement.exceptions.DuplicateUsernameException;
import com.lnhuynh.bookstoremanagement.exceptions.InvalidRoleException;
import com.lnhuynh.bookstoremanagement.repositories.AccountRepository;
import com.lnhuynh.bookstoremanagement.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder,
                             RoleRepository roleRepository) {
  public String signUp(AccountCreateRequestDTO accountInfo) {
    Optional<Account> duplicateAccount = accountRepository.findByUsername(accountInfo.getUsername());
    List<Role> roleList = new ArrayList<>();
    if (duplicateAccount.isPresent()) {
      throw new DuplicateUsernameException(duplicateAccount.get().getUsername());
    }
    accountInfo.getRole().forEach(role -> {
      Optional<Role> resultRole = roleRepository.findByRoleName(role);
      if (resultRole.isEmpty()) {
        throw new InvalidRoleException(role);
      }
      roleList.add(resultRole.get());
    });

    Account account = Account.builder()
      .username(accountInfo.getUsername())
      .password(passwordEncoder.encode((accountInfo.getPassword())))
      .roleList(roleList)
      .build();
    accountRepository.save(account);
    return "User is added successfully!";
  }
}
