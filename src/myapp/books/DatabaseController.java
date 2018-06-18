package myapp.books;

import com.mongodb.*;

import java.net.UnknownHostException;

public class DatabaseController {

    public static DatabaseController getInstance() throws UnknownHostException {
        if(_instance == null) {
            _instance = new DatabaseController();
        }
        return _instance;
    }

    private static DatabaseController _instance;

    final MongoClient _mongoClient;

    private DatabaseController() throws UnknownHostException {
        _mongoClient = new MongoClient();
        System.out.println(_mongoClient.getDatabaseNames());
    }






}
