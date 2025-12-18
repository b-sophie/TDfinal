package com.buydens.Library;

public class Book {
	private static int nextId = 1;
	private String id;
	private String title;
	private String author;
	private int stock;

	public Book(int idNoIncr,String title, String author, int stock) {
		this.id = String.valueOf(0);
		System.out.println("Création du livre avec ID: " + this.id);
		System.out.println("Next ID: " + nextId);
		this.title = title;	
		this.author = author;
		this.stock = stock;
	}
	
	public Book(String title, String author, int stock) {
		this.id = String.valueOf(nextId++);
		System.out.println("Création du livre avec ID: " + this.id);
		System.out.println("Next ID: " + nextId);
		this.title = title;
		this.author = author;
		this.stock = stock;
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

}