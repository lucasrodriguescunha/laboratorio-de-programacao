package entities;

import java.util.ArrayList;

public class Library {

    private ArrayList<Book> books = new ArrayList<>();

    public Library() {

    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void listBooks() {
        for (Book book : books) {
            book.description();
        }
    }

    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }

        return null;
    }
}