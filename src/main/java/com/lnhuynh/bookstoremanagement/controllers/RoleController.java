package com.lnhuynh.bookstoremanagement.controllers;

import com.lnhuynh.bookstoremanagement.domain.Role;
import com.lnhuynh.bookstoremanagement.dtos.RoleCreateRequestDTO;
import com.lnhuynh.bookstoremanagement.dtos.RoleCreateResponseDTO;
import com.lnhuynh.bookstoremanagement.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;


  @PostMapping("/create")
  public ResponseEntity<RoleCreateResponseDTO> addRole(@RequestBody RoleCreateRequestDTO roleInfo) {
    return roleService.addRole(roleInfo);
  }
}
