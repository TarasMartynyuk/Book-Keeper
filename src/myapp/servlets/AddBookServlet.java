package myapp.servlets;

import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.books.DatabaseController;

import java.io.IOException;

public class AddBookServlet extends AbstractServlet {

    public void service(Request req, Response res) throws IOException, MissingParameterException {

        var b = DatabaseController.getInstance();


    }
}
