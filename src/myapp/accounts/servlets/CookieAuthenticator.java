package myapp.accounts.servlets;

import myapp.accounts.AccountsCollectionWrapper;
import myapp.accounts.Credentials;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CookieAuthenticator {
    public static CookieAuthenticator getInstance()  {
        if(_instance == null) {
            _instance = new CookieAuthenticator();
        }
        return _instance;
    }
    private static CookieAuthenticator _instance;

    private final HashSet<String> _givenCookies;

    private CookieAuthenticator() {
        _givenCookies = new HashSet<String>();
    }

    public boolean containsAuthenticationCookie(List<String> cookies) {
        return cookies.stream().anyMatch(this::isCookieAuthenticated);
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
        _givenCookies.add(cookie);
        return cookie;
    }

    /**
     * does this cookie mark an authenticated user?
     */
    private boolean isCookieAuthenticated(String cookie) {
        return _givenCookies.contains(cookie);
    }

    private String generateCookie(Credentials credentials) {
        return credentials.getLogin();
    }
}
