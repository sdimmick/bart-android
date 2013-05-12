package com.stevodimmick.bart.api.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.stevodimmick.bart.api.model.Station;

/**
 * Parses XML containing a list of BART stations from the following API:
 * 
 * http://api.bart.gov/api/stn.aspx?cmd=stns&key=MW9S-E7SL-26DU-VV8V
 * 
 * Here's some sample XML:
 * 
 * <station>
 *   <name>12th St. Oakland City Center</name>
 *   <abbr>12TH</abbr>
 *   <gtfs_latitude>37.803664</gtfs_latitude>
 *   <gtfs_longitude>-122.271604</gtfs_longitude>
 *   <address>1245 Broadway</address>
 *   <city>Oakland</city>
 *   <county>alameda</county>
 *   <state>CA</state>
 *   <zipcode>94612</zipcode>
 * </station>
 * 
 * @author sdimmick
 */
public class StationParser extends BaseParser<List<Station>> {
    public StationParser(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    /**
     * Parses XML containing a list of BART stations
     * @param inputStream an {@link InputStream} for the BART stations API
     * @return a {@link List} of parsed {@link Station} objects
     */
    @Override
    public List<Station> parse() throws XmlPullParserException, IOException {
        List<Station> stations = new ArrayList<Station>();
        
        mParser.require(XmlPullParser.START_TAG, XML_NAMESPACE, "root");
        
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            
            String name = mParser.getName();
            if (name.equals("station")) {
                stations.add(parseStation());
            } else if (!name.equals("stations")) {
                skip();
            }
        }
        
        return stations;
    }
    
    /**
     * Parses a {@link Station} element
     * @return a POJO holding parsed BART station data
     */
    private Station parseStation() throws XmlPullParserException, IOException {
        Station station = new Station();
        
        mParser.require(XmlPullParser.START_TAG, XML_NAMESPACE, "station");
        
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
        
            String name = mParser.getName();
            if (name.equals("name")) {
                station.setName(readString("name"));
            } else if (name.equals("abbr")) {
                station.setAbbr(readString("abbr"));
            } else if (name.equals("gtfs_latitude")) {
                station.setLatitude(readFloat("gtfs_latitude"));
            } else if (name.equals("gtfs_longitude")) {
                station.setLongitude(readFloat("gtfs_longitude"));
            } else if (name.equals("address")) {
                station.setAddress(readString("address"));
            } else if (name.equals("city")) {
                station.setCity(readString("city"));
            } else if (name.equals("state")) {
                station.setState(readString("state"));
            } else if (name.equals("zipcode")) {
                station.setZipcode(readString("zipcode"));
            } else {
                skip();
            }
        }
        
        return station;
    }
    
}
