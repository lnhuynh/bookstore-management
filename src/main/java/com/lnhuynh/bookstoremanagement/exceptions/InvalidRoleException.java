package com.lnhuynh.bookstoremanagement.exceptions;

public class InvalidRoleException extends RuntimeException{
  public InvalidRoleException(String roleName) {
    super(String.format("Role \"%s\" is not exists", roleName));
  }
}
