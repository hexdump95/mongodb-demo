package com.mycompany.demo.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.mycompany.demo.documents.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {

}
