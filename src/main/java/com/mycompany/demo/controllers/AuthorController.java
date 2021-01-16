package com.mycompany.demo.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.demo.dtos.AuthorDto;
import com.mycompany.demo.services.AuthorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

	Logger logger = LoggerFactory.getLogger(AuthorController.class);

	@Autowired
	private AuthorService service;

	@GetMapping(path = "")
	public ResponseEntity<Flux<AuthorDto>> getAuthors() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Mono<AuthorDto>> getAuthor(@PathVariable("id") String id) {
		return ResponseEntity.ok(service.findOne(id));
	}
	
	@PostMapping(path = "")
	public ResponseEntity<Mono<AuthorDto>> postAuthor(@Valid @RequestBody AuthorDto authorDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(authorDto));

	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Mono<AuthorDto>> updateAuthor(@PathVariable String id, @RequestBody AuthorDto authorDto) {
		return ResponseEntity.status(HttpStatus.OK).body(service.update(id, authorDto));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Mono<String>> deleteAuthor(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
	}

}
