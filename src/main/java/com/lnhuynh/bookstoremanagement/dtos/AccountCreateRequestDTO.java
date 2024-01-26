package com.lnhuynh.bookstoremanagement.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class AccountCreateRequestDTO {
  private String username;
  private String password;
  private List<String> role;
}
