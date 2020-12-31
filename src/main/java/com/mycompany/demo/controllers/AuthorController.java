
package com.mycompany.demo.controllers;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.server.ResponseStatusException;

import com.mycompany.demo.documents.Author;
import com.mycompany.demo.dtos.AuthorDto;
import com.mycompany.demo.repositories.AuthorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

	Logger logger = LoggerFactory.getLogger(AuthorController.class);

	@Autowired
	private AuthorRepository repo;

	@GetMapping(path = "")
	public ResponseEntity<Flux<AuthorDto>> getAuthors() {

		return ResponseEntity.status(HttpStatus.OK).body(repo.findAll().map(a -> {
			ModelMapper modelMapper = new ModelMapper();
			AuthorDto authorDto = modelMapper.map(a, AuthorDto.class);
			return authorDto;
		}));
	}

	@GetMapping(path = "/{id}")
	public Mono<ResponseEntity<AuthorDto>> getAuthor(@PathVariable("id") String id) {

		return this.repo.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found")))
				.map(a -> {
					ModelMapper modelMapper = new ModelMapper();
					AuthorDto authorDto = modelMapper.map(a, AuthorDto.class);
					return ResponseEntity.ok(authorDto);
				});
	}

	@PostMapping(path = "")
	public Mono<ResponseEntity<AuthorDto>> postAuthor(@RequestBody AuthorDto authorDto) {

		ModelMapper modelMapper = new ModelMapper();
		Author author = modelMapper.map(authorDto, Author.class);

		return repo.save(author).map(a -> {
			AuthorDto responseDto = modelMapper.map(a, AuthorDto.class);
			return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		});

	}

	@PutMapping(path = "/{id}")
	public Mono<ResponseEntity<Author>> updateAuthor(@PathVariable String id, @RequestBody AuthorDto authorDto) {
		
//		ModelMapper modelMapper = new ModelMapper();

		return repo.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found")))
				.flatMap(a -> {
//			a = modelMapper.map(authorDto, Author.class);
					a.setFirstName(authorDto.getFirstName());
					a.setLastName(authorDto.getLastName());
					return repo.save(a);
				}).map(a -> ResponseEntity.ok(a));

	}

	@DeleteMapping(path = "/{id}")
	public Mono<ResponseEntity<?>> deleteAuthor(@PathVariable String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found")))
				.flatMap(a -> repo.delete(a).then(Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body(""))));
	}

}
