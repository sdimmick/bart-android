package com.stevodimmick.bart.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.api.parser.StationParser;

/**
 * Fetches XML from BART's APIs and parses it into POJOs. More info here:
 * http://api.bart.gov/docs/overview/index.aspx
 *  
 * @author sdimmick
 */
public class BartApi {
    private static final String API_KEY = "MW9S-E7SL-26DU-VV8V";
    private static final String BASE_API_URL = "http://api.bart.gov/api";
    private static final String STATIONS_URL = BASE_API_URL + "/stn.aspx?cmd=stns&key=" + API_KEY;
    
    /**
     * Fetches and parses a list of BART stations
     * @param context the caller's {@link Context}
     * @return a list of all BART stations
     */
    public static List<Station> getStations(Context context) 
            throws ClientProtocolException, 
            IOException, 
            IllegalStateException, 
            XmlPullParserException {
        
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(STATIONS_URL);
        HttpResponse response = httpClient.execute(request);
        InputStream inputStream = response.getEntity().getContent();
        
        StationParser parser = new StationParser(inputStream);
        return parser.parse();
    }
    
}
