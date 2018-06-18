package http.server.request;
import http.server.Method;

import java.util.HashMap;
import java.util.Map;

// TODO: parsed everything in one run
public class HttpRequestParser {

    public String parseUri(String headersString) {

        if (headersString.isEmpty()) {
            return "";
        }

        int index1 = headersString.indexOf(' ');
        if (index1 != -1) {
            int index2 = headersString.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return headersString.substring(index1 + 1, index2);
            }
        }

        return "";
    }

    public Method parseMethod(String headers) {

        int methodEndIndex = headers.indexOf(' ');

        if(methodEndIndex < 0) {
            throw new IllegalArgumentException("headers does not contain a single space");
        }

        var methodString = headers.substring(0, methodEndIndex).toUpperCase();

        switch (methodString) {
            case "GET":
                return Method.GET;
            case "POST":
                return Method.POST;
            default: return null;
        }
    }

    /**
     * @return 0 if no content length header provided, else header value
     */
    public int parseContentLengthFromHeaders(String headers) {
        int headerStartIndex = headers.indexOf("Content-Length");

        if(headerStartIndex < 0) {
            return 0;
        }
        int offset = 16; // length of haeder name string + 2 for : and space

        int headerEndIndex =  headers.indexOf("\r\n", headerStartIndex);

        assert  headerEndIndex > 0;

        var valueStr = headers.substring(headerStartIndex + offset, headerEndIndex);

        assert Integer.parseInt(valueStr) >= 0;
        return Integer.parseInt(valueStr);
    }

    public Map<String, String> parseBody(String bodyString) {
        // quick way
        var paramPairStrings = bodyString.split("&");
        var body = new HashMap<String, String >();

        for (var pair : paramPairStrings) {
            var keyValueStrings = pair.split("=");

            if(keyValueStrings.length == 1) {
                throw new IllegalArgumentException("every key in body must have a matching value, separated by =");
            }

            assert keyValueStrings.length == 2;
            if(keyValueStrings[0].isEmpty()) {
                throw new IllegalArgumentException("empty key fo value: " + keyValueStrings[1]);
            }
            if(keyValueStrings[1].isEmpty()) {
                throw new IllegalArgumentException("empty value fo key: " + keyValueStrings[0]);
            }

            body.put(keyValueStrings[0], keyValueStrings[1]);
        }

        return body;
    }
}
