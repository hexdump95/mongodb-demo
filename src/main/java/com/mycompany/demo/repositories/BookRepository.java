package com.mycompany.demo.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.mycompany.demo.documents.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {

}
