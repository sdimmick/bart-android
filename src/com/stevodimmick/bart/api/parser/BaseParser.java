package com.stevodimmick.bart.api.parser;

import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.stevodimmick.util.LogUtils;

import android.util.Log;
import android.util.Xml;

/**
 * Base class for all BART API SAX XML parsers. That's a lot of acronyms.
 * @author sdimmick
 *
 * @param <T> the type of the resulting parsed data
 */
public abstract class BaseParser<T> {
    private static final String TAG = LogUtils.getTag(BaseParser.class);
    protected static final String XML_NAMESPACE = null;
    
    protected XmlPullParser mParser;

    public BaseParser(InputStream inputStream) throws IOException {
        mParser = createParser(inputStream);
    }
    
    /**
     * Creates a XML parser for sub-classes
     * @return a namespace-less {@link XmlPullParser} for subclasses to use
     */
    private XmlPullParser createParser(InputStream inputStream) throws IOException {
        XmlPullParser parser = Xml.newPullParser();
        
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
        } catch (XmlPullParserException e) {
            Log.e(TAG, "Error initializing XML parser", e);
        }
        
        return parser;
    }
    
    /**
     * Parses the value of the specified element
     * @param elementName the name of the element to parse
     * @return the value of the specified element
     */
    protected String readString(String elementName) throws XmlPullParserException, IOException {
        mParser.require(XmlPullParser.START_TAG, XML_NAMESPACE, elementName);
        String value = readText();
        mParser.require(XmlPullParser.END_TAG, XML_NAMESPACE, elementName);
        
        return value;
    }
    
    /**
     * Parsed the value of the specified element
     * @param elementName the name of the element to parse
     * @return a float containing the value of the specified element
     */
    protected float readFloat(String elementName) throws XmlPullParserException, IOException {
        float value = 0f;
        String text = readString(elementName);
        
        try {
            value = Float.parseFloat(text);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error parsing float from string " + text, e);
        }
        
        return value;
    }
    
    /**
     * Parses the value of the current element
     * @return the value of the current element. Jeez JavaDoc sucks...
     */
    private String readText() throws IOException, XmlPullParserException {
        String text = "";
        
        if (mParser.next() == XmlPullParser.TEXT) {
            text = mParser.getText();
            mParser.nextTag();
        }
        
        return text;
    }
    
    /**
     * Skip the current tag. We don't care about it.
     */
    protected void skip() throws XmlPullParserException, IOException {
        if (mParser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        
        int depth = 1;
        while (depth != 0) {
            switch (mParser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
    
    public abstract T parse() throws XmlPullParserException, IOException;

}
