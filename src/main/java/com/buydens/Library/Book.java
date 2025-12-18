package com.buydens.Library;

public class Book {
	private static int nextId = 1;
	private int id;
	private String title;
	private String author;
	private int stock;
	private String coverUrl;
	private String description;

	public Book(String title, String author, int stock,String coverUrl, String description) {
		this.id = nextId++;
		this.title = title;
		this.author = author;
		this.stock = stock;
		this.coverUrl = coverUrl;
		this.description = description;
		System.out.println("Book created\nid: " + this.id + "\ntitle: " + this.title + "\nauthor: " + this.author + "\nstock: " + this.stock);

	}

	public Book(int id,String title, String author, int stock, String coverUrl, String description) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.stock = stock;
		this.coverUrl = coverUrl;
		this.description = description;
	}



	public int getId() {
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