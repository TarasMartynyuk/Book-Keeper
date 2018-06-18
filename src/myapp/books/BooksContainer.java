package myapp.books;

import com.mongodb.MongoClient;

import java.net.UnknownHostException;
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
        _books = BooksCollectionWrapper.getInstance().getAllBooks();
    }

    public synchronized void addBook(Book book) throws UnknownHostException {
        _books.add(book);
        BooksCollectionWrapper.getInstance().insert(book.toDBObject());

        BooksCollectionWrapper.getInstance().printColl();
    }

    public synchronized Iterable<Book> getBooks() {
        // it is a shallow copy, so if we modify the books,
        // so we don't synchronize for the case when the books themselves are edited
        return new ArrayList<>(_books);
    }
}
