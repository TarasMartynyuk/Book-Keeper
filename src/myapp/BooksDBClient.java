package myapp;

import com.mongodb.*;
import java.util.Arrays;

public class BooksDBClient {
    public static BooksDBClient getInstance()  {
        if(_instance == null) {
            _instance = new BooksDBClient();
        }
        return _instance;
    }

    private static final String USERNAME = "only_user";
    private static final String PASSWORD = "1111";

    private static BooksDBClient _instance;
    private final MongoClient _client;
    private final DB _booksDb;

    private BooksDBClient() {
        var credential = MongoCredential.createCredential(USERNAME,
                Constants.BOOK_KEEPER_DB_NAME,
                PASSWORD.toCharArray());
        _client = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));

        _booksDb = _client.getDB(Constants.BOOK_KEEPER_DB_NAME);
    }

    public DBCollection getCollection(String booksCollName) {
        return _booksDb.getCollection(booksCollName);
    }
}
