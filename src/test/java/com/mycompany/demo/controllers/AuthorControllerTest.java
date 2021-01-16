package com.mycompany.demo.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.mycompany.demo.dtos.AuthorDto;
import com.mycompany.demo.services.AuthorService;

import reactor.core.publisher.Flux;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebFluxTest(AuthorController.class)
public class AuthorControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private AuthorService service;

	@Test
	public void testGetAuthors() throws Exception {
		AuthorDto author1 = AuthorDto.builder().firstName("John").lastName("Doe").build();
		AuthorDto author2 = AuthorDto.builder().firstName("Jane").lastName("Doe").build();
		Flux<AuthorDto> authorsFlux = Flux.just(author1, author2);

		given(service.findAll()).willReturn(authorsFlux);

		webTestClient.get().uri("/api/v1/authors").accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectStatus().isOk()
		.expectBodyList(AuthorDto.class).hasSize(2);

		verify(service).findAll();
	}

	@Test
	public void testPostAuthor200() throws Exception {
		AuthorDto author = AuthorDto.builder().firstName("John").lastName("Doe").build();

		webTestClient.post().uri("/api/v1/authors").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters
						.fromValue(
								"{\n" + 
								"  \"firstName\": \"John\",\n" + 
								"  \"lastName\": \"Doe\"\n" + 
								"}"))
				.exchange()
				.expectStatus().isCreated();
		verify(service, times(1)).save(author);
	}

	@Test
	public void testPostAuthor400() throws Exception {
		webTestClient.post().uri("/api/v1/authors").contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(
						"{\n" + 
						"  \"firstName\": \"J\",\n" + 
						"  \"lastName\": \"Doe\"\n" + 
						"}"))
				.exchange()
				.expectStatus().isBadRequest();
	}

}
