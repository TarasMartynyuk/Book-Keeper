package myapp.servlets;

import http.server.response.Response;
import myapp.AppConstants;

import java.io.IOException;

public class NotAuthenticatedResponseWriter {
    public void writeNotAuthenticated(Response res) throws IOException {
        new ResponseBuilder(res).writeError("Not Authenticated <br/> <a href=\"/login.html\">Go To Login</a>", AppConstants.TYPE_HTML);
    }
}
