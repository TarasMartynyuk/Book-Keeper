package myapp.accounts;

import java.util.ArrayList;

public class AccountsCollectionWrapper {
    public static AccountsCollectionWrapper getInstance()  {
        if(_instance == null) {
            _instance = new AccountsCollectionWrapper();
        }
        return _instance;
    }
    private static AccountsCollectionWrapper _instance;

    private ArrayList<Credentials> _credentials;

    private AccountsCollectionWrapper() {
        _credentials = new ArrayList<>();
        _credentials.add(new Credentials("login", "pass"));
    }

    public boolean credentialsValid(Credentials credentials) {
        return _credentials.stream().
                anyMatch(knownCred ->
                        knownCred.getLogin().equals(credentials.getLogin()) &&
                        knownCred.getPassword().equals(credentials.getPassword()));
    }
}
