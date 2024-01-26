package com.lnhuynh.bookstoremanagement.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RoleCreateRequestDTO {
  private String roleName;
}
