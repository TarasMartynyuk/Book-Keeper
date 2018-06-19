package http.server.request;

import http.server.Method;

import java.io.InputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class HttpRequest implements Request {
    //region getters
    @Override
    public String getURI() {
        return _uri;
    }

    public Method getMethod() { return _method; }

    @Override
    public String getParameter(String name) {
        return _bodyParams.get(name);
    }

    public String getParameterOrNull(String name){
        return _bodyParams.getOrDefault(name, null);
    }


    @Override
    public Set<String> getParameterNames() {
        return _bodyParams.keySet();
    }

    @Override
    public Collection<String> getParameterValues() {
        return _bodyParams.values();
    }

    @Override
    public String getRequestAsText() {
        return _headers + "\n" + _body;
    }//endregion

    private String _headers;
    private final String _body;
    private String _uri;
    private String _cookie;
    private final Map<String, String> _bodyParams;

    private Method _method;
    private int _contentLength;

    public HttpRequest(InputStream input) throws IOException {
        var parser = new HttpRequestParser(input);

        parser.readAndParseHeaders();
        initHeaderFields(parser);

        if(_contentLength > 0) {
            parser.readAndParseBody();
            _body = parser.getBodyString();
            _bodyParams = parser.getBodyParams();
        } else {
            _body = null;
            _bodyParams = null;
        }
    }

    private void initHeaderFields(HttpRequestParser parser) {
        _uri = parser.getUri();
        _method = parser.getMethod();
        _contentLength = parser.getContentLength();
        _cookie = parser.getCookie();

        _headers = parser.getHeaderString();
    }
}
