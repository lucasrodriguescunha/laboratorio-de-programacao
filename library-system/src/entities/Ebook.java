package entities;

public class Ebook extends Book {

    private double fileSize;

    public Ebook(String title, String author, int numberOfPages, double fileSize) {
        super(title, author, numberOfPages);
        this.fileSize = fileSize;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public void description() {
        System.out.println(
                "Ebook: " + getTitle() +
                        " | Author: " + getAuthor() +
                        " | Number of pages: " + getNumberOfPages() +
                        " | Weight: " + getFileSize() + "MB"
        );
    }
}