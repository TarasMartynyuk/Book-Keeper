package myapp.books.servlets;

import http.server.Method;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.AppConstants;
import myapp.accounts.servlets.CookieAuthenticator;
import myapp.servlets.NotAuthenticatedResponseWriter;
import myapp.servlets.ResponseBuilder;
import myapp.books.Book;
import myapp.books.BooksContainer;
import myapp.servlets.WrongMethodException;

import java.io.IOException;

public class AddBookServlet extends AbstractServlet {

    public void service(Request req, Response res) throws IOException {

        if(req.getMethod() != Method.POST) {
            throw new WrongMethodException(
                    "AddBookServlet", req.getMethod(), Method.POST);
        }

        if(! CookieAuthenticator.getInstance().containsAuthenticationCookie(req)) {
            new NotAuthenticatedResponseWriter().writeNotAuthenticated(res);
            return;
        }

        try {
            var book = parseBookFromBody(req);
            BooksContainer.getInstance().addBook(book);

            res.sendStaticResource("/index.html");

        } catch (NumberFormatException ne) {
            new ResponseBuilder(res).writeError(ne.toString(), AppConstants.TYPE_PLAIN_TEXT);
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
