package myapp.servlets;

import http.server.servlet.AbstractServletsMap;

/**
 *
 * @author andrii
 */
public class ServletsMap extends AbstractServletsMap {

    public ServletsMap() {
        servlets.put("/books.add", new AddBookServlet());
    }
}
