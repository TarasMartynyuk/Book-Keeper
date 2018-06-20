package myapp.accounts;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import myapp.BooksDBClient;

import java.util.ArrayList;

public class AccountsCollectionWrapper {
    public static AccountsCollectionWrapper getInstance()  {
        if(_instance == null) {
            _instance = new AccountsCollectionWrapper();
        }
        return _instance;
    }
    private static AccountsCollectionWrapper _instance;

    private static final String USERS_COLL_NAME = "users";

    private ArrayList<Credentials> _credentials;
    private final DBCollection _usersCollection;

    private AccountsCollectionWrapper() {

        _usersCollection = BooksDBClient.getInstance().
                getCollection(USERS_COLL_NAME);

        printColl();
    }

    public boolean credentialsValid(Credentials credentials) {
        return _usersCollection.find(credentials.toDocument()).count() != 0;

    }

    public boolean loginTaken(String login) {
        var query = new BasicDBObjectBuilder().add("login", login).get();
        return _usersCollection.find(query).count() != 0;
    }

    public void printColl() {
        var cursor = _usersCollection.find();
        System.out.println("Users Collection:");

        cursor.forEach(doc -> System.out.println('\t' + doc.toString()));
    }

    public void dropColl() {
        _usersCollection.drop();
    }

    public void saveCredentials(Credentials credentials) {
        _usersCollection.insert(credentials.toDocument());
    }
}
