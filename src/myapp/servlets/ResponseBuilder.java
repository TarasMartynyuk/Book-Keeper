package myapp.servlets;
import http.server.response.Response;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseBuilder {
    private final PrintWriter _out;

    public ResponseBuilder(Response res) throws IOException {
        this(res.getWriter());
    }

    public ResponseBuilder(PrintWriter writer) {
        _out = writer;
    }

    public void writeOkResponse(String response, String contentType) {
        writeOkResponse(response, contentType, null);
    }

    public void writeOkResponse(String response, String contentType, Iterable<String> headers) {
        writeOkHeader();
        writeContentHeaders(response.length(), contentType);

        if(headers != null) {
            for(var header : headers) {
                _out.write(header + "\r\n");
            }
        }
        _out.write("\r\n");

        _out.write(response);
        writeConnectionClosed();
        _out.flush();
    }

    public void writeError(String errorMessage, String contentType) {
        writeOkHeader();
        writeContentHeaders(errorMessage.length(), contentType);
        _out.write("\r\n");
        _out.write(errorMessage);
        writeConnectionClosed();
        _out.flush();
    }

    private void writeOkHeader() {
        _out.write("HTTP/1.1 200 OK\r\n");
    }

    private void writeContentHeaders(int contentLength, String contentType) {
        _out.write("Content-Length: " + contentLength + "\r\n");
        _out.write("Content-Type: " + contentType + "\r\n");
    }

    private void writeConnectionClosed() {
        _out.write("Connection: Closed");
    }
}
