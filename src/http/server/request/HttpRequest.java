package http.server.request;

import http.server.Method;

import java.io.InputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class HttpRequest implements Request {

    private final String _headers;
    private final String _body;
    private final String _uri;
    private final Map<String, String> _bodyParams;

    private final Method _method;

    public HttpRequest(InputStream input) throws IOException {
        var parser = new HttpRequestParser();
        var reader = new HttpRequestReader(input);

        _headers = reader.readHeaders();
        if(_headers.isEmpty()) {
            throw new IllegalArgumentException("stream contains empty headers - the first line is empty");
        }

        _uri = parser.parseUri(_headers);
        _method = parser.parseMethod(_headers);
        if(_method == null) {
            throw new IllegalArgumentException("invalid HTTP header - missing method");
        }

        int contentLength = parser.parseContentLengthFromHeaders(_headers);

        if(contentLength > 0) {
            _body = reader.readBody(contentLength);
            _bodyParams = parser.parseBody(_body);
        } else {
            _body = null;
            _bodyParams = null;
        }
    }

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
}
