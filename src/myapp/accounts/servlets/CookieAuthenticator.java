package myapp.accounts.servlets;

import myapp.accounts.Credentials;
import myapp.books.BooksContainer;

public class CookieAuthenticator {

    public static CookieAuthenticator getInstance()  {
        if(_instance == null) {
            _instance = new CookieAuthenticator();
        }
        return _instance;
    }
    private static CookieAuthenticator _instance;

    /**
     * does this cookie mark an authenticated user?
     */
    public boolean isCookieAuthenticated(String cookie) {
        return false;
    }

    /**
     * if login and password are valid,
     * generates a cookie which can be used to identify the logged in user
     * else returns null
     */
    public String giveAuthenticationCookie(Credentials credentials) {
        return null;
    }
}
