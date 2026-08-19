package entities;

public class Ebook extends Book {

    private double fileSize;

    public Ebook(String title, String author, int numberOfPages, double fileSize) {
        super(title, author, numberOfPages);
        setFileSize(fileSize);
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = Math.max(fileSize, 0);
    }

    @Override
    public void description() {
        System.out.println(
                "Ebook: " + getTitle() +
                        " | Autor: " + getAuthor() +
                        " | Páginas: " + getNumberOfPages() +
                        " | Tamanho: " + getFileSize() + "MB"
        );
    }
}
