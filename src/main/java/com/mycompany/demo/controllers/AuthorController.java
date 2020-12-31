
package com.mycompany.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.demo.documents.Author;
import com.mycompany.demo.repositories.AuthorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class AuthorController {

	@Autowired
	private AuthorRepository repo;
	
	@GetMapping("")
	public ResponseEntity<Flux<Author>> getAuthors() {
		return ResponseEntity.status(HttpStatus.OK).body(repo.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Mono<Author>> getOneAuthor(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(repo.findById(id));
	}
	
	@PostMapping()
	public ResponseEntity<Mono<Author>> postAuthor(@RequestBody Author author){
		return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(author));
	}
	
	
}
