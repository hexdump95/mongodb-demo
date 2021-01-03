package com.mycompany.demo.services;

import com.mycompany.demo.dtos.AuthorDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
	Flux<AuthorDto> findAll();

	Mono<AuthorDto> findOne(String id);

	Mono<AuthorDto> save(AuthorDto authorDto);

	Mono<AuthorDto> update(String id, AuthorDto authorDto);

	Mono<String> delete(String id);
}
