package com.lnhuynh.bookstoremanagement.services;

import com.lnhuynh.bookstoremanagement.domain.Role;
import com.lnhuynh.bookstoremanagement.dtos.RoleCreateRequestDTO;
import com.lnhuynh.bookstoremanagement.dtos.RoleCreateResponseDTO;
import com.lnhuynh.bookstoremanagement.exceptions.DuplicateRoleException;
import com.lnhuynh.bookstoremanagement.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;

  public ResponseEntity<RoleCreateResponseDTO> addRole(RoleCreateRequestDTO roleInfo) {
    Optional<Role> duplicateRole = roleRepository.findByRoleName(roleInfo.getRoleName());
    if (duplicateRole.isPresent()) {
      throw new DuplicateRoleException(duplicateRole.get().getRoleName());
    }
//    Role requestRole = Role.builder().roleName(roleInfo.getRoleName()).build();
    Role role = roleRepository.save(modelMapper.map(roleInfo, Role.class));
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    RoleCreateResponseDTO roleResponse = modelMapper.map(role, RoleCreateResponseDTO.class); // test model mapper
    return new ResponseEntity<RoleCreateResponseDTO>(roleResponse, headers, HttpStatus.CREATED);
  }
}
