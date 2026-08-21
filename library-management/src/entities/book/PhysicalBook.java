package entities.book;

/**
 * Livro físico: o exemplar de papel, com peso em gramas.
 *
 * Herda de Book toda a regra de disponibilidade — como existe um exemplar só,
 * emprestá-lo o deixa indisponível até a devolução.
 */
public class PhysicalBook extends Book {

    private double weight;

    public PhysicalBook(String code, String title, String author, int numberOfPages, double weight) {
        super(code, title, author, numberOfPages);
        setWeight(weight);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = Math.max(weight, 0);
    }

    @Override
    public void description() {
        System.out.println(
                "Livro físico: " + getTitle() + "\n" +
                "Código: " + getCode() + "\n" +
                "Autor: " + getAuthor() + "\n" +
                "Páginas: " + getNumberOfPages() + "\n" +
                "Peso: " + getWeight() + "g\n" +
                "Situação: " + (isAvailable() ? "Disponível" : "Emprestado")
        );
    }
}
