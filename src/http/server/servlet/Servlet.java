package http.server.servlet;


import http.server.request.Request;
import http.server.response.Response;
import myapp.servlets.MissingParameterException;

import java.io.IOException;

/**
 *
 * @author Andrii_Rodionov
 */
public interface Servlet {

    void destroy();

    void init();

    void service(Request req, Response res) throws IOException, MissingParameterException;
    
}
