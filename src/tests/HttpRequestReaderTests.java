package tests;

import http.server.request.HttpRequestReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class HttpRequestReaderTests {

    static final String CONTENT = "note=LONGONELONGONELONGONELONGOE";
    static final int CONTENT_LENGTH = CONTENT.length();
    static final String HEADER_END = "HEADER_END";

    static final String POST_STRING =
            "POST /servlet/hello HTTP/1.1\r\n" +
            "Host: localhost:8888\r\n" +
            "Content-Length: " + CONTENT_LENGTH + "\r\n" +
            "Connection: keep-alive" + HEADER_END + "\r\n" +
            "\r\n" +
            CONTENT;

    HttpRequestReader _testInstance;

    @Before
    public void setup() throws IOException {
        _testInstance = new HttpRequestReader(createPostRequestStream());
    }

    @Test
    public void POST_ReadHeaders_ReturnsString_EndingWithLastHeader() throws IOException {

        var headers = _testInstance.readHeaders();
        Assert.assertTrue(headers.contains(HEADER_END));
    }

    @Test
    public void POST_ReadBody_ReturnsContent() throws IOException {

        _testInstance.readHeaders();
        var body = _testInstance.readBody(CONTENT_LENGTH);

        Assert.assertEquals(body, CONTENT);
    }


    InputStream createPostRequestStream() {
        return new ByteArrayInputStream(POST_STRING.getBytes());
    }

    public static void writeAll(InputStream in) throws IOException {
        var twoBytes = new byte[2];
        int inLine = 0;
        while (true) {

            if(in.read(twoBytes, 0, twoBytes.length) == -1) {
                System.out.println("EOF!");
                break;
            }

            System.out.print(new String(twoBytes, "UTF-8"));
//            inLine += 2;
//            if(inLine >= 80) {
//                System.out.print("\n");
//                inLine = 0;
//            }
        }
    }
}
