package myapp.servlets;

import http.server.servlet.AbstractServletsMap;
import myapp.accounts.servlets.LoginServlet;
import myapp.accounts.servlets.SignupServlet;
import myapp.books.servlets.AddBookServlet;
import myapp.books.servlets.ListBooksServlet;

/**
 *
 * @author andrii
 */
public class ServletsMap extends AbstractServletsMap {

    public ServletsMap() {
        servlets.put("/books.add", new AddBookServlet());
        servlets.put("/books.list", new ListBooksServlet());
        servlets.put("/login", new LoginServlet());
        servlets.put("/signup", new SignupServlet());
    }
}
