package http.server.processors;

import http.server.request.Request;
import http.server.response.Response;
import java.io.IOException;

public class StaticResourceProcessor implements Processor {

    private static final String WELCOME_PAGE = "index.html";
    
    @Override
    public void process(Request request, Response response) {
        try {
            String uri = request.getURI();
            if (uri.isEmpty() || uri.equals("/")) {
                uri = WELCOME_PAGE;
            }
            response.sendStaticResource(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
