package com.lnhuynh.bookstoremanagement.exceptions;

public class DuplicateRoleException extends RuntimeException{
  public DuplicateRoleException(String roleName) {
    super(String.format("Role \"%s\" already exists", roleName));
  }
}
