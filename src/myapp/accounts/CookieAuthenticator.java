package myapp.accounts;

public class CookieAuthenticator {


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
