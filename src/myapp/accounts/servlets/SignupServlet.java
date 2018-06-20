package myapp.accounts.servlets;

import http.server.Method;
import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.AbstractServlet;
import myapp.AppConstants;
import myapp.accounts.AccountsCollectionWrapper;
import myapp.accounts.Credentials;
import myapp.servlets.MissingParameterException;
import myapp.servlets.ResponseBuilder;
import myapp.servlets.WebRootPageReader;
import myapp.servlets.WrongMethodException;

import java.io.IOException;

public class SignupServlet extends AbstractServlet {
    @Override
    public void service(Request req, Response res) throws IOException, MissingParameterException {
        if(req.getMethod() != Method.POST) {
            throw new WrongMethodException(
                    "AddBookServlet", req.getMethod(), Method.POST);
        }

        var credentials = parseCredentials(req);

        var accountC = AccountsCollectionWrapper.getInstance();
        var resBuilder = new ResponseBuilder(res);

        if(accountC.loginTaken(credentials.getLogin())) {

            var errorPage = "The Login is already taken! <br/>" +
                    new WebRootPageReader().getPage(AppConstants.LOGIN_PAGE);
            resBuilder.writeError(errorPage, AppConstants.TYPE_HTML);
            return;
        }

        accountC.saveCredentials(credentials);
        var rootPage = new WebRootPageReader().getPage(AppConstants.INDEX);
        resBuilder.writeOkResponse(rootPage, AppConstants.TYPE_HTML);
    }

    private Credentials parseCredentials(Request req) throws MissingParameterException {
        var login = req.getParameterOrNull("login");
        if(login == null) {
            throw new MissingParameterException("login");
        }

        var password = req.getParameterOrNull("password");
        if(password == null) {
            throw new MissingParameterException("password");
        }
        return new Credentials(login, password);
    }
}
