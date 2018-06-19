package tests;

import http.server.request.HttpRequestParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class HttpRequestParserTests {

    static final String CONTENT = "note=LONGONELONGONELONGONELONGOE";
    static final int CONTENT_LENGTH = CONTENT.length();
    static final String HEADER_END = "HEADER_END";
    static final String COOKIE = "Idea-4886331b=53119ac8-b5ca-4982-942e-92dec07594ea; JSESSIONID=F534F188FEC14180E540B5E25D5D7E48";

    static final String POST_STRING =
            "POST /servlet/hello HTTP/1.1\r\n" +
            "Host: localhost:8888\r\n" +
            "Content-Length: " + CONTENT_LENGTH + "\r\n" +
            "Connection: keep-alive" + HEADER_END + "\r\n" +
            "Cookie: " + COOKIE +
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

    @Test
    public void GetCookie_ReturnsValueOfCookieHeader() throws IOException {
        _testInstance.readAndParseHeaders();
        Assert.assertEquals(_testInstance.getCookie(), COOKIE);
    }

    InputStream createPostRequestStream() {
        return new ByteArrayInputStream(POST_STRING.getBytes());
    }
}
