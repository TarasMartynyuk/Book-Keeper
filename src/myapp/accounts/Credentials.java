package myapp.accounts;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Credentials {
    private String _login;
    private String _password;

    public Credentials(String login, String password) {
        _login = login;
        _password = password;
    }

    public Credentials(DBObject document) {
        _login = (String) document.get("login");
        _password = (String) document.get("password");
    }

    public String getLogin() {
        return _login;
    }

    public void setLogin(String login) {
        _login = login;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public DBObject toDocument() {
        var document = new BasicDBObject();

        document.put("login", _login);
        document.put("password", _password);

        return document;
    }
}
