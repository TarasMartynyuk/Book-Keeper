package myapp.books;

public class BooksController {

    public static BooksController getInstance()  {
        if(_instance == null) {
            _instance = new BooksController();
        }
        return _instance;
    }

    private static BooksController _instance;

    private BooksController() {
    }

    public synchronized void addBook(Book book) {
    }

    public synchronized Iterable<Book> getBooks() {
        return null;
    }
}
