package myapp.books;

import java.util.ArrayList;
import java.util.Collections;

public class BooksContainer {

    public static BooksContainer getInstance()  {
        if(_instance == null) {
            _instance = new BooksContainer();
        }
        return _instance;
    }

    private static BooksContainer _instance;
    private final ArrayList<Book> _books;

    private BooksContainer() {
        _books = new ArrayList<>();
    }

    public synchronized void addBook(Book book) {
        _books.add(book);
    }

    public synchronized Iterable<Book> getBooks() {
        // it is a shallow copy, so if we modify the books,
        // so we don't synchronize for the case when the books themselves are edited
        var copy = new ArrayList<Book>(_books.size());

        Collections.copy(copy, _books);
        return copy;
    }
}
