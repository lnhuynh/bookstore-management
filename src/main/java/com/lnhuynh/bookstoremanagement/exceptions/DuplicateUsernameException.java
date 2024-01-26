package com.lnhuynh.bookstoremanagement.exceptions;

public class DuplicateUsernameException extends RuntimeException {
  public DuplicateUsernameException(String username) {
    super(String.format("Username \"%s\" already exists", username));
  }
}
