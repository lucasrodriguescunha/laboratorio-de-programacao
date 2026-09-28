import app.LibraryMenu;
import app.SampleData;
import services.Library;

public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        SampleData.load(library);

        new LibraryMenu(library).start();
    }
}
