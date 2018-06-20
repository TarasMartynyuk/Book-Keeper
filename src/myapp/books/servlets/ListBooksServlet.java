package myapp.books.servlets;
import http.server.Method;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.AppConstants;
import myapp.accounts.CookieAuthenticator;
import myapp.servlets.NotAuthenticatedResponseWriter;
import myapp.servlets.ResponseBuilder;
import myapp.books.BooksContainer;
import myapp.servlets.WrongMethodException;

import java.io.IOException;

public class ListBooksServlet extends AbstractServlet {
    @Override
    public void service(Request req, Response res) throws IOException {
        if(req.getMethod() != Method.GET) {
            throw new WrongMethodException(
                    "ListBooksServlet", req.getMethod(), Method.GET);
        }

        if(! CookieAuthenticator.getInstance().containsAuthenticationCookie(req)) {
            new NotAuthenticatedResponseWriter().writeNotAuthenticated(res);
            return;
        }

        var htmlBuilder = new BookListPageBuilder();
        var resBuilder = new ResponseBuilder(res);

        var books = BooksContainer.getInstance().getBooks();
        resBuilder.writeOkResponse(htmlBuilder.buildBookListPage(books), AppConstants.TYPE_HTML);
    }

}
