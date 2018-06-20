package http.server.processors;

import http.server.request.Request;
import http.server.response.Response;
import myapp.AppConstants;
import myapp.accounts.CookieAuthenticator;
import myapp.servlets.NotAuthenticatedResponseWriter;

import java.io.IOException;

public class StaticResourceProcessor implements Processor {

    @Override
    public void process(Request request, Response response) {
        try {
            var uri = request.getURI();
            if (uri.isEmpty() || uri.equals("/") || uri.equals(AppConstants.LOGIN_PAGE)) {
                response.sendStaticResource(AppConstants.LOGIN_PAGE);
                return;
            }

            if(! CookieAuthenticator.getInstance().containsAuthenticationCookie(request) &&
                     (! uri.equals(AppConstants.SIGNUP_PAGE))) {
                new NotAuthenticatedResponseWriter().writeNotAuthenticated(response);
                return;
            }

            response.sendStaticResource(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
