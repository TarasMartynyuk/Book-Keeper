package myapp.servlets;

import http.server.servlet.AbstractServletsMap;
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
    }

}
