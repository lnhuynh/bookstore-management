package com.lnhuynh.bookstoremanagement.repositories;

import com.lnhuynh.bookstoremanagement.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository {
  Book findBook(int id);
}
