package http.server.request;
import http.server.Method;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * assumes it is the only one reading from InputStream
 */
public class HttpRequestParser {

    //region getters

    public String getUri() {
        return _uri;
    }

    public Method getMethod() {
        return _method;
    }

    public List<String> getCookies() {
        return _cookies;
    }

    public String getHeaderString() {
        return _headerString;
    }

    public int getContentLength() {
        return _contentLength;
    }

    public Map<String, String> getBodyParams() { return _bodyParams; }

    public String getBodyString() { return _bodyString; }
    //endregion

    private String _uri;
    private Method _method;
    private int _contentLength;
    private ArrayList<String> _cookies;
    private Map<String, String> _bodyParams;

    private String _headerString;
    private String _bodyString;

    private final BufferedReader _bufferedIn;
    private final HttpHeaderLinesParser _parserHelper;

    public HttpRequestParser(InputStream in) {
        _bufferedIn = new BufferedReader(new InputStreamReader(in));
        _parserHelper = new HttpHeaderLinesParser();
    }

    // should be parsed as a map, but i don't need it now
    /**
     * reads the http request's headers into a string
     * stops at the blank line after headers
     *
     * the stream is advanced, so that it now points
     * to the start of next line after the blank line
     */
    public void readAndParseHeaders() throws IOException {
        // not closing any stream that uses socket input stream here
        var fullRequest = new StringBuilder();

        var firstLine = _bufferedIn.readLine();
        fullRequest.append(firstLine);

        if(firstLine == null || firstLine.isEmpty()) {
            throw new IllegalStateException("the input contains no lines");
        }
        parseFirstLine(firstLine);

        String line;
        while (true) {
            line = _bufferedIn.readLine();

            if (line == null || line.isEmpty()) {
                break;
            }

            if(_parserHelper.isCookiesHeader(line)) {
                _cookies = parseCookies(_parserHelper.parseHeaderValue(line));
            }

            fullRequest.append(line);
            fullRequest.append("\r\n");
        }

        assert fullRequest.toString().contains("HTTP");

        _headerString =  fullRequest.toString();
        _contentLength = _parserHelper.parseContentLengthFromHeaders(_headerString);
        if(_contentLength < 0) {
            throw new IllegalArgumentException("contentLength must be >= 0");
        }
    }

    private void parseFirstLine(String firstLine) {
        _uri = _parserHelper.parseUri(firstLine);
        _method = _parserHelper.parseMethod(firstLine);
        if(_method == null) {
            throw new IllegalArgumentException("invalid HTTP header - missing method");
        }
    }

    /**
     * the headers must be read from InputStream _in before reading body
     * reads all remaining characters from inputstream
     *
     * interprets bytes as UTF-8 encoded
     */
    public void readAndParseBody() throws IOException {

        if(_headerString == null) {
            throw new IllegalStateException("you must parse headers first");
        }
        if(_contentLength <= 0 ) {
            throw new IllegalStateException("cannot parse body for content length which is <= 0");
        }

        var chars = new char[_contentLength];
        _bufferedIn.read(chars, 0, chars.length);

        _bodyString = new String(chars);
        _bodyParams = _parserHelper.parseBody(_bodyString);
    }

    private ArrayList<String> parseCookies(String cookiesValue) {
        return new ArrayList<>(Arrays.asList(cookiesValue.split("; ")));
    }
}
