package com.mycompany.demo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.mycompany.demo.documents.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

}
