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

import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.api.model.TrainsForDestination;
import com.stevodimmick.bart.api.parser.StationParser;
import com.stevodimmick.bart.api.parser.TrainsForDestinationParser;

/**
 * Fetches XML from BART's APIs and parses it into POJOs. More info here:
 * http://api.bart.gov/docs/overview/index.aspx
 *  
 * @author sdimmick
 */
public class BartApi {
    private static final String API_KEY = "MW9S-E7SL-26DU-VV8V";
    private static final String BASE_API_URL = "http://api.bart.gov/api";
    private static final String STATIONS_URL = BASE_API_URL + "/stn.aspx?cmd=stns";
    private static final String ETD_URL = BASE_API_URL + "/etd.aspx?cmd=etd&orig=%s";
    
    /**
     * Appends the API key to the specified URL and returns an 
     * {@link InputStream} to read data from it
     * @param url the API to call
     * @return an {@link InputStream} to read data from the API
     */
    private static InputStream doGet(String url) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url + "&key=" + API_KEY);
        HttpResponse response = httpClient.execute(request);
        InputStream inputStream = response.getEntity().getContent();
        
        return inputStream;
    }
    
    /**
     * Fetches and parses a list of BART stations
     * @return a list of all BART stations
     */
    public static List<Station> getStations() throws 
            ClientProtocolException, 
            IOException, 
            XmlPullParserException {
        
        InputStream inputStream = doGet(STATIONS_URL);
        StationParser parser = new StationParser(inputStream);
        return parser.parse();
    }
    
    /**
     * Fetches and parses a list of train arrival times for the specified BART station
     * @param station the station to fetch arrival times for
     * @return a {@link List} of {@link TrainsForDestination} with train arrival times
     */
    public static List<TrainsForDestination> getTrainsForDestination(String station) throws 
            ClientProtocolException, 
            IOException, 
            XmlPullParserException {
        
        InputStream inputStream = doGet(String.format(ETD_URL, station));
        TrainsForDestinationParser parser = new TrainsForDestinationParser(inputStream);
        return parser.parse();
    }
    
}
