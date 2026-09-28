package entities.book;

public class Ebook extends Book {

    private double fileSize;

    public Ebook(String code, String title, String author, int numberOfPages, double fileSize) {
        super(code, title, author, numberOfPages);
        setFileSize(fileSize);
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = Math.max(fileSize, 0);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void description() {
        System.out.println(
                "Ebook: " + getTitle() + "\n" +
                "Código: " + getCode() + "\n" +
                "Autor: " + getAuthor() + "\n" +
                "Páginas: " + getNumberOfPages() + "\n" +
                "Tamanho: " + getFileSize() + "MB\n" +
                "Situação: " + (isAvailable() ? "Disponível" : "Emprestado")
        );
    }
}
