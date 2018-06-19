package myapp.books.servlets;

import http.server.Method;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.ResponseBuilder;
import myapp.books.Book;
import myapp.books.BooksContainer;
import myapp.servlets.MissingParameterException;

import java.io.IOException;

public class AddBookServlet extends AbstractServlet {

    public void service(Request req, Response res) throws IOException, MissingParameterException {

        if(req.getMethod() != Method.POST) {
            throw new IllegalArgumentException("AddBookServlet got a request with method: " + req.getMethod() +
                    "the method must be Post");
        }

        try {
            var book = parseBookFromBody(req);
            BooksContainer.getInstance().addBook(book);

            res.sendStaticResource("/index.html");

        } catch (NumberFormatException ne) {
            var resBuilder = new ResponseBuilder(res);
            resBuilder.writeError(ne.toString());
        }
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
