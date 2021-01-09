package com.mycompany.demo.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mycompany.demo.repositories.AuthorRepository;
import com.mycompany.demo.documents.Author;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthorHandler {

	Logger logger =  LoggerFactory.getLogger(AuthorHandler.class);
	
	private final AuthorRepository repository;

	public AuthorHandler(AuthorRepository repository) {
		this.repository = repository;
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		Flux<Author> people = repository.findAll();
		return ok().contentType(APPLICATION_JSON).body(people, Author.class);
	}

	public Mono<ServerResponse> findOne(ServerRequest request) {
		String id = request.pathVariable("id");
		return repository.findById(id).flatMap(author -> ok().contentType(APPLICATION_JSON).bodyValue(author))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> save(ServerRequest request) {
		Mono<Author> author = request.bodyToMono(Author.class);
		Mono<Author> authorRes = author.flatMap(repository::save);
		return created(null).body(authorRes, Author.class);
	}
	
	public Mono<ServerResponse> delete(ServerRequest request) {
		String id = request.pathVariable("id");
		repository.deleteById(id);
		return noContent().build();
	}

	public Mono<ServerResponse> update(ServerRequest request) { // not yet implemented
		return null;
	}

}
