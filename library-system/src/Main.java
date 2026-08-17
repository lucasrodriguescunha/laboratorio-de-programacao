import entities.Ebook;
import entities.Library;
import entities.PhysicalBook;

public class Main {
    public static void main(String[] args) {

        PhysicalBook physicalBook = new PhysicalBook(
                "Clean Code",
                "Robert C. Martin",
                672,
                784
        );

        Ebook ebook = new Ebook(
                "Clean Code",
                "Robert C. Martin",
                672,
                16.9
        );


        Library library = new Library();

        library.addBook(physicalBook);
        library.addBook(ebook);

        physicalBook.description();
        ebook.description();

    }
}