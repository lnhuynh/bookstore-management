package com.lnhuynh.bookstoremanagement.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorld {

  @GetMapping("/hello")
  private String sayHello() {
    return "Hello world!";
  }
}
