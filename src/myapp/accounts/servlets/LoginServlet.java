package myapp.accounts.servlets;

import http.server.Method;
import http.server.request.HttpRequest;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.servlets.MissingParameterException;

import java.io.IOException;

public class LoginServlet extends AbstractServlet {
    @Override
    public void service(Request req, Response res) throws IOException {
        if(req.getMethod() != Method.POST) {
            throw new IllegalArgumentException("AddBookServlet got a request with method: " + req.getMethod() +
                    "the method must be Post");
        }

        var cookie = ((HttpRequest) req).getCookie();
        String url;
        if(CookieAuthenticator.getInstance().isCookieAuthenticated(cookie)) {
            url = "/index.html";
        } else {
            url = "/login.html";
        }

        res.sendStaticResource(url);
    }
}
