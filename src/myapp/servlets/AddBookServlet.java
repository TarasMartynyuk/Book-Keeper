package myapp.servlets;

import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.books.Book;
import myapp.books.DatabaseController;

import java.io.IOException;

public class AddBookServlet extends AbstractServlet {

    public void service(Request req, Response res) throws IOException, MissingParameterException {

        var b = DatabaseController.getInstance();

        Book book = parseBookFromBody(req);

    }

    private Book parseBookFromBody(Request req) {
        var name = req.getParameter("name");
        var author = req.getParameter("author");
        var language = req.getParameter("language");
        var year = req.getParameter("year");

        return new Book(name, author,
                language, Integer.parseInt(year));
    }
}
