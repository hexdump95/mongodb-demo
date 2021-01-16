package com.mycompany.demo.documents;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

	@Id
	private String id;
    @Size(min = 2, max = 35, message = "firstName must be between 2 and 35 characters!")
	private String firstName;
    @Size(min = 2, max = 35, message = "lastName must be between 2 and 35 characters!")
	private String lastName;

}
