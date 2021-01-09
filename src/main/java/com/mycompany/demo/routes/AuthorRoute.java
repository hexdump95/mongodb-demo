package com.mycompany.demo.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mycompany.demo.handlers.AuthorHandler;

@Configuration
public class AuthorRoute {

	String baseUrl = "/api/v1/authors";

	@Bean
	public RouterFunction<ServerResponse> route(AuthorHandler authorHandler) {

		return RouterFunctions
				.route(RequestPredicates.GET(baseUrl).and(RequestPredicates.accept(APPLICATION_JSON)),
						authorHandler::findAll)

				.andRoute(RequestPredicates.GET(baseUrl + "/{id}").and(RequestPredicates.accept(APPLICATION_JSON)),
						authorHandler::findOne)

				.andRoute(RequestPredicates.POST(baseUrl).and(RequestPredicates.accept(APPLICATION_JSON)),
						authorHandler::save)

				.andRoute(RequestPredicates.PUT(baseUrl + "/{id}").and(RequestPredicates.accept(APPLICATION_JSON)),
						authorHandler::update)

				.andRoute(RequestPredicates.DELETE(baseUrl + "/{id}").and(RequestPredicates.accept(APPLICATION_JSON)),
						authorHandler::delete)
		;
	}
}
