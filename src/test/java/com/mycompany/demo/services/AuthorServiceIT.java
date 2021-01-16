package com.mycompany.demo.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mycompany.demo.documents.Author;
import com.mycompany.demo.dtos.AuthorDto;
import com.mycompany.demo.repositories.AuthorRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthorServiceIT {
	@Autowired
	private AuthorService service;

	@Autowired
	private AuthorRepository repo;

	@BeforeEach
	public void beforeEach() {
		repo.deleteAll().block();
	}
	
	@Test
	public void testPostAuthor() {
		AuthorDto authorReq = AuthorDto.builder().firstName("John").lastName("Doe").build();		
		service.save(authorReq).block();

		Author author = repo.findAll().blockLast();
		
		assertThat(1L).isEqualTo(repo.count().block());
		assertThat(author.getFirstName()).isEqualTo(authorReq.getFirstName());
		assertThat(author.getLastName()).isEqualTo(authorReq.getLastName());
	}
	
	@AfterEach
	public void afterEach() {
		repo.deleteAll().block();
	}
	

}
