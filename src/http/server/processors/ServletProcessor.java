package http.server.processors;

import http.server.request.Request;
import http.server.response.Response;
import http.server.servlet.Servlet;
import http.server.servlet.AbstractServletsMap;

public class ServletProcessor implements Processor {

    private final AbstractServletsMap servletsMap;

    public ServletProcessor(AbstractServletsMap servletsMap) {
        this.servletsMap = servletsMap;
    }   
    
    @Override
    public void process(Request request, Response response) {

        var servletName = getServletName(request);
        var servlet = servletsMap.getServlet(servletName);
        if (servlet == null) {
            return;
        }
        
        try {
            servlet.service(request, response);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private String getServletName(Request request) {
        var uri = request.getURI();
        var start = uri.lastIndexOf("/");
        var end = uri.lastIndexOf("?");
        end = (end < 0) ? uri.length() : end;
        var servletName = uri.substring(start, end);
        return servletName;
    }
}
