package myapp.accounts.servlets;

import myapp.accounts.AccountsCollectionWrapper;
import myapp.accounts.Credentials;
import myapp.books.BooksContainer;

import java.util.HashSet;
import java.util.Set;

public class CookieAuthenticator {
    public static CookieAuthenticator getInstance()  {
        if(_instance == null) {
            _instance = new CookieAuthenticator();
        }
        return _instance;
    }
    private static CookieAuthenticator _instance;

    private final HashSet<String> givenCookies;

    private CookieAuthenticator() {
        givenCookies = new HashSet<String>();
    }

    /**
     * does this cookie mark an authenticated user?
     */
    public boolean isCookieAuthenticated(String cookie) {
        return givenCookies.contains(cookie);
    }

    /**
     * if login and password are valid,
     * generates a cookie which can be used to identify the logged in user
     * else returns null
     */
    public String giveAuthenticationCookie(Credentials credentials) {
        if(!AccountsCollectionWrapper.getInstance().credentialsValid(credentials)) {
            return null;
        }

        var cookie = generateCookie(credentials);
        givenCookies.add(cookie);
        return cookie;
    }

    private String generateCookie(Credentials credentials) {
        return credentials.getLogin();
    }
}
