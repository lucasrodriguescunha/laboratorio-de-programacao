package entities;

import util.IsbnUtils;

import java.time.Year;

public abstract class Book {

    private static int nextId = 1;

    private final int id;
    private String title;
    private String author;
    private Year year;
    private String isbn;
    private boolean available = true;

    public Book(String title, String author, Year year, String isbn) {
        this.id = nextId++;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
    }

    public abstract String getType();

    public int getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Year getYear() { return year; }
    public void setYear(Year year) { this.year = year; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getFormattedIsbn() { return IsbnUtils.format(isbn); }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "#" + id + " - " + title + " (" + author + ")";
    }
}
