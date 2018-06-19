package myapp.books;

import com.mongodb.*;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class BooksCollectionWrapper {

    public static BooksCollectionWrapper getInstance()  {
        if(_instance == null) {
            try {
                _instance = new BooksCollectionWrapper();
            } catch (UnknownHostException uhe) {
                throw new RuntimeException(uhe);
            }
        }
        return _instance;
    }

    private static final String BOOKS_DB_NAME = "Books";
    private static final String BOOKS_COLL_NAME = "books";

    private static final String USERNAME = "only_user";
    private static final String PASSWORD = "1111";

    private static BooksCollectionWrapper _instance;

    private final MongoClient _client;
    private final DBCollection _booksColl;

    private BooksCollectionWrapper() throws UnknownHostException {

        var credential = MongoCredential.createCredential(USERNAME, BOOKS_DB_NAME, PASSWORD.toCharArray());
        _client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));


        var booksDb = _client.getDB(BOOKS_DB_NAME);
        _booksColl = booksDb.getCollection(BOOKS_COLL_NAME);
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
}
