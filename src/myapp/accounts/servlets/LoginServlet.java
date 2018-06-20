package myapp.accounts.servlets;

import http.server.Method;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
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

        var authenticationCookie = CookieAuthenticator.getInstance().
                giveAuthenticationCookie(
                        new Credentials(login, password));

        if(authenticationCookie != null) {
            var page = WebRootPageReader.getPage("/index.html");
            var headers = Arrays.asList("Set-Cookie: " + authenticationCookie);
            new ResponseBuilder(res).writeOkResponse(page, "text/html", headers);
        } else {
            res.sendStaticResource("/login.html");
        }
    }
}
