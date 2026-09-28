package entities;

import java.time.Year;

public class PhysicalBook extends Book {

    public PhysicalBook(String title, String author, Year year, String isbn) {
        super(title, author, year, isbn);
    }

    @Override
    public String getType() {
        return "Físico";
    }
}
