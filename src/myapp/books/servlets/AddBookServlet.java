package myapp.books.servlets;

import http.server.Method;
import http.server.request.HttpRequest;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.accounts.servlets.CookieAuthenticator;
import myapp.servlets.NotAuthenticatedResponseWriter;
import myapp.servlets.ResponseBuilder;
import myapp.books.Book;
import myapp.books.BooksContainer;
import myapp.servlets.MissingParameterException;
import myapp.servlets.WrongMethodException;

import java.io.IOException;

public class AddBookServlet extends AbstractServlet {

    public void service(Request req, Response res) throws IOException, MissingParameterException {

        if(req.getMethod() != Method.POST) {
            throw new WrongMethodException(
                    "AddBookServlet", req.getMethod(), Method.POST);
        }

        if(! hasAuthCookie(req)) {
            new NotAuthenticatedResponseWriter().writeNotAuthenticated(res);
            return;
        }

        try {
            var book = parseBookFromBody(req);
            BooksContainer.getInstance().addBook(book);

            res.sendStaticResource("/index.html");

        } catch (NumberFormatException ne) {
            new ResponseBuilder(res).writeError(ne.toString());
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

    static boolean hasAuthCookie(Request req) {
        return CookieAuthenticator.getInstance().
                containsAuthenticationCookie(
                        ((HttpRequest) req).getCookies());
    }
}
