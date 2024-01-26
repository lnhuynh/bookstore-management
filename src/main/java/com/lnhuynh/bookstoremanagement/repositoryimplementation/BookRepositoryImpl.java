package com.lnhuynh.bookstoremanagement.repositoryimplementation;

import com.lnhuynh.bookstoremanagement.domain.Book;
import com.lnhuynh.bookstoremanagement.repositories.BookRepository;

import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {
  @Override
  public Book findBook(int id) {
    //This should be replaced with query from DB
    return new Book();
  }
}
