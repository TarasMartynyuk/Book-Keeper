package tests;

import http.server.request.HttpRequestParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class HttpRequestParserTests {

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

    HttpRequestParser _testInstance;

    @Before
    public void setup() throws IOException {
        _testInstance = new HttpRequestParser(createPostRequestStream());
    }

    @Test
    public void POST_ReadHeaders_ReturnsString_EndingWithLastHeader() throws IOException {

        _testInstance.readAndParseHeaders();
        Assert.assertTrue(_testInstance.getHeaderString().contains(HEADER_END));
    }

    @Test
    public void POST_ReadBody_ReturnsContent() throws IOException {

        _testInstance.readAndParseHeaders();
        _testInstance.readAndParseBody();
        var body = _testInstance.getBodyString();

        Assert.assertEquals(body, CONTENT);
    }

    InputStream createPostRequestStream() {
        return new ByteArrayInputStream(POST_STRING.getBytes());
    }

    public static void writeAll(InputStream in) throws IOException {
        var twoBytes = new byte[2];
        while (true) {

            if(in.read(twoBytes, 0, twoBytes.length) == -1) {
                System.out.println("EOF!");
                break;
            }

            System.out.print(new String(twoBytes, "UTF-8"));
        }
    }
}
