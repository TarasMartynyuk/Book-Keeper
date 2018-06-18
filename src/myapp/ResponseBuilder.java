package myapp;
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

    public void writeOkResponce(String response, String contentType) {
        writeOkHeader();
        writeContentHeaders(response.length(), contentType);
        _out.write(response);
        writeConnectionClosed();
        _out.flush();
    }

    private void writeOkHeader() {
        _out.write("HTTP/1.1 200 OK\r\n");
    }

    private void writeContentHeaders(int contentLength, String contentType) {
        _out.write("Content-Length: " + contentLength + "\r\n");
        _out.write("Content-Type: " + contentType + "\r\n\r\n");
    }

    private void writeConnectionClosed() {
        _out.write("Connection: Closed");
    }
}
