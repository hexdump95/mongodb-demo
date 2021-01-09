package com.mycompany.demo.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mycompany.demo.documents.Author;
import com.mycompany.demo.dtos.AuthorDto;
import com.mycompany.demo.repositories.AuthorRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository repo;

	@Override
	public Flux<AuthorDto> findAll() {
		return repo.findAll().map(a -> {
			ModelMapper modelMapper = new ModelMapper();
			AuthorDto authorDto = modelMapper.map(a, AuthorDto.class);
			return authorDto;
		});
	}

	@Override
	public Mono<AuthorDto> findOne(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found")))
				.map(a -> {
					ModelMapper modelMapper = new ModelMapper();
					return modelMapper.map(a, AuthorDto.class);
				});
	}

	@Override
	public Mono<AuthorDto> save(AuthorDto authorDto) {
		ModelMapper modelMapper = new ModelMapper();
		Author author = modelMapper.map(authorDto, Author.class);

		return repo.save(author).map(a -> {
			return modelMapper.map(a, AuthorDto.class);
		});
	}

	@Override
	public Mono<AuthorDto> update(String id, AuthorDto authorDto) {
//	ModelMapper modelMapper = new ModelMapper();

		return repo.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found")))
				.map(a -> {
//		a = modelMapper.map(authorDto, Author.class);
					a.setFirstName(authorDto.getFirstName());
					a.setLastName(authorDto.getLastName());
					repo.save(a);
					return authorDto;
				});
	}

	@Override
	public Mono<String> delete(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found")))
				.flatMap(a -> repo.delete(a).then(Mono.just("{\"message\": \"deleted\"}")));
	}

}
