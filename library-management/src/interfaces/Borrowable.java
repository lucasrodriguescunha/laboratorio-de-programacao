package interfaces;

public interface Borrowable {

    boolean isAvailable();
    void borrow();
    void giveBack();
}
