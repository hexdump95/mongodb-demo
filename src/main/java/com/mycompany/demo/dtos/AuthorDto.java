package com.mycompany.demo.dtos;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = Include.NON_NULL)
public class AuthorDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
    @Size(min = 2, max = 35, message = "firstName must be between 2 and 35 characters!")
	private String firstName;
    @Size(min = 2, max = 35, message = "lastName must be between 2 and 35 characters!")
	private String lastName;
}
