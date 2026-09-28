package entities.book;

import exceptions.BookUnavailableException;
import interfaces.Borrowable;
import interfaces.Describable;

public abstract class Book implements Borrowable, Describable {

    private final String code;
    private String title;
    private String author;
    private int numberOfPages;
    private boolean available;

    public Book(String code, String title, String author, int numberOfPages) {
        this.code = code;
        this.title = title;
        this.author = author;
        setNumberOfPages(numberOfPages);
        this.available = true;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = Math.max(numberOfPages, 0);
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void borrow() {
        if (!isAvailable()) {
            throw new BookUnavailableException(
                    "O livro \"" + title + "\" (código " + code + ") já está emprestado."
            );
        }
        this.available = false;
    }

    @Override
    public void giveBack() {
        this.available = true;
    }

    @Override
    public abstract void description();
}
