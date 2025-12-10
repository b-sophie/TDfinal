package com.buydens.Library;

import com.buydens.Database;

public class Book {
	private String id;
	private String title;
	private String author;

	public Book(String id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
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

	public void saveToDatabase() {
		String sql = "INSERT INTO books (id, title, author, available) VALUES (?, ?, ?, ?)";
		try (java.sql.Connection conn = com.buydens.Database.connect();
			 java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, this.getId());
			pstmt.setString(2, this.getTitle());
			pstmt.setString(3, this.getAuthor());
			pstmt.setInt(4, 1); // available = 1 (true)
			pstmt.executeUpdate();
			System.out.println("Book added to database: " + this.getTitle());
		} catch (Exception e) {
			System.out.println("Error adding book: " + e.getMessage());
		}
	}

	public void addBookToDatabase(Book b) {
		String sql = "INSERT INTO books (id, title, author, available) VALUES (?, ?, ?, ?)";
		try (java.sql.Connection conn = Database.connect();
			 java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, b.getId());
			pstmt.setString(2, b.getTitle());
			pstmt.setString(3, b.getAuthor());
			pstmt.setInt(4, 1); // available = 1 (true)
			pstmt.executeUpdate();
			System.out.println("Book added to database: " + b.getTitle());
		} catch (Exception e) {
			System.out.println("Error adding book: " + e.getMessage());
		}
	}

	public void deleteBookFromDatabase() {
		String sql = "DELETE FROM books WHERE id = ?";
		try (java.sql.Connection conn = Database.connect();
			 java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, this.getId());
			int affected = pstmt.executeUpdate();
			if (affected > 0) {
				System.out.println("Book deleted from database: " + this.getTitle());
			} else {
				System.out.println("No book found with id: " + this.getId());
			}
		} catch (Exception e) {
			System.out.println("Error deleting book: " + e.getMessage());
		}
	}
}
