package tests;

import http.server.request.HttpRequestParserHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HttpRequestParserHelperTests {
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

    HttpRequestParserHelper _testInstance;

    @Before
    public void setup() throws IOException {
        _testInstance = new HttpRequestParserHelper();
    }

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
}
