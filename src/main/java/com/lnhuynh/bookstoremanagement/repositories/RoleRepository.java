package com.lnhuynh.bookstoremanagement.repositories;

import com.lnhuynh.bookstoremanagement.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findById(String id);
  Optional<Role> findByRoleName(String roleName);
}
