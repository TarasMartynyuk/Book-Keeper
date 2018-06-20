package http.server.request;

import http.server.Method;

import java.io.InputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

// i didn't take time to think about Req/Res interfaces and Http concrete classes, so i'll just
// put cookies functionality to concrete class and cast for now
public class HttpRequest implements Request {
    //region getters
    @Override
    public String getURI() {
        return _uri;
    }

    @Override
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

    public List<String> getCookies() {
        return _cookies;
    }

    @Override
    public String getRequestAsText() {
        return _headers + "\n" + _body;
    }//endregion

    private String _headers;
    private final String _body;
    private String _uri;
    private List<String> _cookies;
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
        _cookies = parser.getCookies();

        _headers = parser.getHeaderString();
    }
}
