package myapp.accounts;

public class Account {
    int _id;
    String _login;
    String _password;

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
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
}
