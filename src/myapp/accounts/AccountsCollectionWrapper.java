package myapp.accounts;


public class AccountsCollectionWrapper {
    public static AccountsCollectionWrapper getInstance()  {
        if(_instance == null) {
            _instance = new AccountsCollectionWrapper();
        }
        return _instance;
    }
    private static AccountsCollectionWrapper _instance;

    public boolean credentialsValid(Credentials credentials) {
        return true;
    }

    private void createUser() {

    }
}
