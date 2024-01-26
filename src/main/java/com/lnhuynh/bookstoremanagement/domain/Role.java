package com.lnhuynh.bookstoremanagement.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String roleName;

  @ManyToMany(mappedBy = "roleList", fetch = FetchType.LAZY)
  private List<Account> accountList;
}
