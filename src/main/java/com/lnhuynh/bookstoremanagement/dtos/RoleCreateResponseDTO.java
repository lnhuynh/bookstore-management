package com.lnhuynh.bookstoremanagement.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RoleCreateResponseDTO {
  private String id;
  private String roleName;
}
