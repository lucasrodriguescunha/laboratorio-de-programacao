package app;

import entities.book.Ebook;
import entities.book.PhysicalBook;
import entities.person.Member;
import services.Library;

public class SampleData {

    private SampleData() {
    }

    public static void load(Library library) {

        library.getBookService().add(new Ebook(
                "978-65-00-00077-1",
                "Engenharia de Software Moderna",
                "Marco Tulio Valente",
                395,
                9.9D
        ));

        library.getBookService().add(new PhysicalBook(
                "8543024978",
                "Engenharia de Software Moderna",
                "Marco Tulio Valente",
                395,
                1680D
        ));

        library.getMemberService().register(new Member(
                "1",
                "Lucas Rodrigues Cunha",
                "lucasrodriguescunha@email.com"
        ));
    }
}
