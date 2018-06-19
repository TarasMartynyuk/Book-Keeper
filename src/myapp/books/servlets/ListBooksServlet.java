package myapp.books.servlets;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.ResponseBuilder;
import myapp.books.BooksContainer;
import myapp.servlets.MissingParameterException;

import java.io.IOException;

public class ListBooksServlet extends AbstractServlet {
    @Override
    public void service(Request req, Response res) throws IOException, MissingParameterException {

        var htmlBuilder = new BookListPageBuilder();
        var resBuilder = new ResponseBuilder(res);

        var books = BooksContainer.getInstance().getBooks();
        resBuilder.writeOkResponce(htmlBuilder.buildBookListPage(books), "text/html");
    }
}
