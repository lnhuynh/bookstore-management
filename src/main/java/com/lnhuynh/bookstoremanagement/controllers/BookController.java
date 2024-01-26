package com.lnhuynh.bookstoremanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BookController {

  @GetMapping("/")
  public ResponseEntity<String> cc() {
    return ResponseEntity.ok("cc");
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<String> user() {
    return ResponseEntity.ok("user");
  }

  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("hello");
  }

  @GetMapping("/book")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> getBook() {
    return ResponseEntity.ok("login successfully");
  }
}
