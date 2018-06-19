package tests;

import http.server.request.HttpHeaderLinesParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HttpHeaderLinesParserTests {
    //region body mocks

    static final String key1 = "key1";
    static final String value1 = "value1";
    static final String key2 = "key2";
    static final String value2 = "value2";

    static final String VALID_BODY_SINGLE_PARAM = key1 + "=" + value1;
    static final String VALID_BODY_MULTIPLE_PARAMS = VALID_BODY_SINGLE_PARAM +
            "&" + key2 + "=" + value2;
    static final String NO_EQUALS_BODY = key1 + value1;

    static final String EMPTY_KEY_BODY = VALID_BODY_SINGLE_PARAM + "&=" + value1;
    static final String EMPTY_VALUE_BODY = VALID_BODY_SINGLE_PARAM + "&" + key1 + "=";
    //endregion

    static final String HEADER_VALUE = "value";
    static final String VALID_HEADER = "header: " + HEADER_VALUE;
    static final String INVALID_HEADER = "invalid semicolon should be here with whitespace" + HEADER_VALUE;

    static final String COOKIE_HEADER = "Cookie";
    static final String VALID_COOKIE_HEADER = COOKIE_HEADER + ": val";
    static final String NOT_COOKIE_HEADER = "notcookie: val";

    HttpHeaderLinesParser _testInstance;

    @Before
    public void setup() throws IOException {
        _testInstance = new HttpHeaderLinesParser();
    }

    //region body
    @Test
    public void ParseBody_IfSingleParameter_ReturnMapHasSingleEntry() {
        var body = _testInstance.parseBody(VALID_BODY_SINGLE_PARAM);

        Assert.assertEquals(body.get(key1), value1);
        Assert.assertEquals(body.keySet().size(), 1);
    }

    @Test
    public void ParseBody_ReturnMap_HasEntryForEachParameter() {
        var body = _testInstance.parseBody(VALID_BODY_MULTIPLE_PARAMS);

        Assert.assertEquals(body.get(key1), value1);
        Assert.assertEquals(body.get(key2), value2);
        Assert.assertEquals(body.keySet().size(), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ParseBody_ThrowsIllegalArgument_IfNoEqualsEncountered() {

        _testInstance.parseBody(NO_EQUALS_BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ParseBody_ThrowsInvalidArgument_IfKeyIsEmpty() {
        _testInstance.parseBody(EMPTY_KEY_BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ParseBody_ThrowsInvalidArgument_IfValueIsEmpty() {
        _testInstance.parseBody(EMPTY_VALUE_BODY);
    }
    //endregion

    //region cookie
    @Test
    public void IsCookie_ReturnsTrueForCookieHeader() {
        Assert.assertTrue(_testInstance.isCookiesHeader(VALID_COOKIE_HEADER));
    }

    @Test
    public void IsCookie_ReturnsFalse_ForNonCookieHeader() {
        Assert.assertFalse(_testInstance.isCookiesHeader(NOT_COOKIE_HEADER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void IsCookie_Throws_IfNoSemicolonAndWhitespace() {
        _testInstance.isCookiesHeader(INVALID_HEADER);
    }

    @Test
    public void ParseHeaderValue_ReturnsHeaderValue_IfValidHeader() {
        Assert.assertEquals(HEADER_VALUE, _testInstance.parseHeaderValue(VALID_HEADER));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ParseHeaderValue_Throws_IfNoSemicolonAndWhitespace() {
        _testInstance.parseHeaderValue(INVALID_HEADER);
    }
    //endregion
}
