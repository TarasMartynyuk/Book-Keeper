package myapp.accounts.servlets;

import http.server.Method;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.AppConstants;
import myapp.accounts.Credentials;
import myapp.servlets.MissingParameterException;
import myapp.servlets.ResponseBuilder;
import myapp.servlets.WebRootPageReader;

import java.io.IOException;
import java.util.Arrays;

public class LoginServlet extends AbstractServlet {
    @Override
    public void service(Request req, Response res) throws IOException, MissingParameterException {
        if(req.getMethod() != Method.POST) {
            throw new IllegalArgumentException("AddBookServlet got a request with method: " + req.getMethod() +
                    "the method must be Post");
        }

        var login = req.getParameterOrNull("login");
        if(login == null) {
            throw new MissingParameterException("login");
        }
        var password = req.getParameterOrNull("password");
        if(password == null) {
            throw new MissingParameterException("password");
        }

        var authCookie = CookieAuthenticator.getInstance().
                giveAuthenticationCookie(
                        new Credentials(login, password));

        if(authCookie != null) {
            LetThroughToIndex(res, authCookie);
        } else {
            LeaveOnLogin(res);
        }
    }

    private static void LetThroughToIndex(Response res, String authCookie) throws IOException {
        var page = new WebRootPageReader().getPage("/index.html");
        var headers = Arrays.asList("Set-Cookie: " + authCookie);
        new ResponseBuilder(res).writeOkResponse(page, "text/html", headers);
    }

    private static void LeaveOnLogin(Response res) throws IOException {
        var page = new WebRootPageReader().getPage("/login.html");
        new ResponseBuilder(res).writeOkResponse(
                "Wrong login or password" +
                page,
                AppConstants.TYPE_HTML);
    }
}
