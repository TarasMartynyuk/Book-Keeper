package myapp.books;

import com.mongodb.*;
import myapp.BooksDBClient;
import myapp.Constants;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class BooksCollectionWrapper {

    public static BooksCollectionWrapper getInstance()  {
        if(_instance == null) {
            _instance = new BooksCollectionWrapper();
        }
        return _instance;
    }

    private static final String BOOKS_COLL_NAME = "books";

    private static BooksCollectionWrapper _instance;
    private final DBCollection _booksColl;

    private BooksCollectionWrapper() {
        _booksColl = BooksDBClient.getInstance().
                getCollection(BOOKS_COLL_NAME);
    }

    public void insert(DBObject dbObject) {
        _booksColl.insert(dbObject);
    }

    public ArrayList<Book> getAllBooks() {
        var cursor = _booksColl.find();

        var books = new ArrayList<Book>(cursor.count());
        System.out.println(books.size());

        while (cursor.hasNext()) {
            var dbObject = cursor.next();
            books.add(Book.fromDBObject(dbObject));
        }
        return books;
    }

    public void printColl() {
        var cursor = _booksColl.find();
        System.out.println("Books Collection:");

        while (cursor.hasNext()) {
            var obj = cursor.next();
            System.out.println('\t' + obj.toString());
        }
    }

    public void dropColl() {
        _booksColl.drop();
    }
}
