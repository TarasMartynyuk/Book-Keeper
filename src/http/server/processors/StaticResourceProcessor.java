package http.server.processors;

import http.server.request.Request;
import http.server.response.Response;
import myapp.accounts.servlets.CookieAuthenticator;
import myapp.servlets.NotAuthenticatedResponseWriter;

import java.io.IOException;

public class StaticResourceProcessor implements Processor {

    private static final String LOGIN_PAGE = "login.html";
    
    @Override
    public void process(Request request, Response response) {
        try {
            var uri = request.getURI();
            if (uri.isEmpty() || uri.equals("/") || uri.equals("/" + LOGIN_PAGE)) {
                response.sendStaticResource(LOGIN_PAGE);
                return;
            }

            if(! CookieAuthenticator.getInstance().containsAuthenticationCookie(request)) {
                new NotAuthenticatedResponseWriter().writeNotAuthenticated(response);
                return;
            }

            response.sendStaticResource(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
