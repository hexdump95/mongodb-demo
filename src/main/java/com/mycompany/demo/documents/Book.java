package com.mycompany.demo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {

	@Id
	private String id;
	private int pages;
	private String title;

	@DBRef
	private Author authors[];

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author[] getAuthors() {
		return authors;
	}

	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}

	public Book() {
		super();
	}

	public Book(String id, int pages, String title, Author[] authors) {
		super();
		this.id = id;
		this.pages = pages;
		this.title = title;
		this.authors = authors;
	}

}
