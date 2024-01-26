package com.lnhuynh.bookstoremanagement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Book {
  private String id;
  private String title;
  private String author;
  private int price;
}
