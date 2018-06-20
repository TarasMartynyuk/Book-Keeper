package myapp.servlets;

import http.server.response.Response;
import java.io.IOException;

public class NotAuthenticatedResponseWriter {
    public void writeNotAuthenticated(Response res) throws IOException {
        new ResponseBuilder(res).writeError("Not Authenticated\n<a href=\"/login.html\">");
    }
}
