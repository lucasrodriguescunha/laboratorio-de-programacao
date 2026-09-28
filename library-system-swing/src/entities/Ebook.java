package entities;

import java.time.Year;

public class Ebook extends Book {

    public Ebook(String title, String author, Year year, String isbn) {
        super(title, author, year, isbn);
    }

    @Override
    public String getType() {
        return "E-book";
    }
}
