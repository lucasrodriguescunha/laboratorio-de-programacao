package entities;

public class PhysicalBook extends Book {

    private double weight;

    public PhysicalBook(String title, String author, int numberOfPages, double weight) {
        super(title, author, numberOfPages);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void description() {
        System.out.println(
                "Physical book: " + getTitle() +
                        " | Author: " + getAuthor() +
                        " | Number of pages: " + getNumberOfPages() +
                        " | Weight: " + getWeight() + "g"
        );
    }
}