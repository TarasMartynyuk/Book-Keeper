package http.server.request;
import java.io.*;


/**
 * assumes it is the only one reading from InputStream
 */
public class HttpRequestReader {
    private final BufferedReader _bufferedIn;

    public HttpRequestReader(InputStream in) {
        _bufferedIn = new BufferedReader(new InputStreamReader(in));
    }

    // should be parsed as a map, but i don't need it now
    /**
     * reads the http request's headers into a string
     * stops at the blank line after headers
     *
     * the stream is advanced, so that it now points
     * to the start of next line after the blank line
     */
    public String readHeaders() throws IOException {
        // not closing any stream that uses socket input stream here
        var fullRequest = new StringBuilder();

        String line;
        while (true) {
            line = _bufferedIn.readLine();

            if (line == null || line.isEmpty()) {
                break;
            }

            fullRequest.append(line);
            fullRequest.append("\r\n");
        }

        assert fullRequest.toString().contains("HTTP");
        return fullRequest.toString();
    }

    /**
     * the headers must be read from InputStream _in before reading body
     * reads all remaining characters from inputstream
     *
     * interprets bytes as UTF-8 encoded
     */
    public String readBody(int contentLength) throws IOException {
        if(contentLength < 0) {
            throw new IllegalArgumentException("contentLength must be >= 0");
        }

        var chars = new char[contentLength];
        _bufferedIn.read(chars, 0, chars.length);

        return new String(chars);
    }

    public void writeAll(InputStream in) throws IOException {
        var twoBytes = new byte[2];
        int inLine = 0;
        while (true) {

            if(in.read(twoBytes, 0, twoBytes.length) == -1) {
                System.out.println("EOF!");
                break;
            }

            System.out.print(new String(twoBytes, "UTF-8"));
        }
    }

    private boolean isCarriageReturn(byte[] bytes) {
        return bytes[0] == '\r' &&
                bytes[1] == '\n';
    }
}
