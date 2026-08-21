package app;

import entities.book.Ebook;
import entities.book.PhysicalBook;
import entities.person.Member;
import services.Library;

/**
 * Dados de exemplo da demonstração: um ebook, um livro físico e um membro,
 * para que o menu já abra com o que listar e emprestar. Os funcionários
 * começam vazios e são cadastrados pelo menu.
 *
 * Fica separado do Main para que o ponto de entrada trate só de montar e abrir
 * a aplicação: o cenário é um detalhe substituível, que pode mudar ou deixar
 * de ser carregado sem que o Main mude.
 */
public class SampleData {

    // Classe utilitária: só existe pelo método estático, não faz sentido
    // instanciá-la.
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
