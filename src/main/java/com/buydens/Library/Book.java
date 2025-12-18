package com.buydens.Library;

public class Book {
	private static int nextId = 1;
	private String id;
	private String title;
	private String author;
	private int stock;
	private String coverUrl;
	private String description;

	public Book(int idNoIncr, String title, String author, int stock) {
		this.id = String.valueOf(0);
		this.title = title;
		this.author = author;
		this.stock = stock;
		this.coverUrl = null;
		this.description = null;
		System.out.println("Book created\nid: " + this.id + "\ntitle: " + this.title + "\nauthor: " + this.author + "\nstock: " + this.stock);
	}

	public Book(String title, String author, int stock) {
		this.id = String.valueOf(nextId++);
		this.title = title;
		this.author = author;
		this.stock = stock;
		this.coverUrl = null;
		this.description = null;
		System.out.println("Book created\nid: " + this.id + "\ntitle: " + this.title + "\nauthor: " + this.author + "\nstock: " + this.stock);

	}

	public Book(String title, String author, int stock, String coverUrl, String description) {
		this.id = String.valueOf(nextId++);
		this.title = title;
		this.author = author;
		this.stock = stock;
		this.coverUrl = coverUrl;
		this.description = description;
	}

	


	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public String getDescription() {
		return description;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}