package myapp.books;

import com.mongodb.*;

import java.net.UnknownHostException;

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

    private static BooksCollectionWrapper _instance;

    final MongoClient _client;
    final DBCollection _booksColl;

    private BooksCollectionWrapper() throws UnknownHostException {
        _client = new MongoClient();
        System.out.println("existing dbs: " + _client.getDatabaseNames());
        var booksDb = _client.getDB(BOOKS_DB_NAME);
        _booksColl = booksDb.getCollection(BOOKS_COLL_NAME);
    }

    public void insert(DBObject dbObject) {
        _booksColl.insert(dbObject);
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
