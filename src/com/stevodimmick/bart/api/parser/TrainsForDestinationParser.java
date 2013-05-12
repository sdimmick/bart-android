package com.stevodimmick.bart.api.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.stevodimmick.bart.api.model.ArrivalTime;
import com.stevodimmick.bart.api.model.TrainsForDestination;

/**
 * Parses XML containing a list of estimated arrival times for a BART station
 * from the following API:
 * 
 * http://api.bart.gov/api/etd.aspx?cmd=etd&orig=RICH&key=MW9S-E7SL-26DU-VV8V
 * 
 * Here's some sample XML:
 * 
 * <root>
 *   <uri>
 *     <![CDATA[ http://api.bart.gov/api/etd.aspx?cmd=etd&orig=RICH ]]>
 *   </uri>
 *   <date>05/12/2013</date>
 *   <time>02:00:48 PM PDT</time>
 *   <station>
 *     <name>Richmond</name>
 *     <abbr>RICH</abbr>
 *     <etd>
 *       <destination>Fremont</destination>
 *       <abbreviation>FRMT</abbreviation>
 *       <estimate>
 *         <minutes>14</minutes>
 *         <platform>2</platform>
 *         <direction>South</direction>
 *         <length>6</length>
 *         <color>ORANGE</color>
 *         <hexcolor>#ff9933</hexcolor>
 *         <bikeflag>1</bikeflag>
 *       </estimate>
 *       ...
 *     </etd>
 *   </station>
 *   <message/>
 * </root>
 *  
 * @author sdimmick
 */
public class TrainsForDestinationParser extends BaseParser<List<TrainsForDestination>> {

    public TrainsForDestinationParser(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    /**
     * Parses XML containing a list of estimated arrival times for a BART station
     * @param inputStream an {@link InputStream} for the BART arrival times API
     * @return a {@link List} of parsed {@link TrainsForDestination} objects
     */
    @Override
    public List<TrainsForDestination> parse() throws XmlPullParserException, IOException {
        List<TrainsForDestination> arrivalTimes = new ArrayList<TrainsForDestination>();

        mParser.require(XmlPullParser.START_TAG, XML_NAMESPACE, "root");
        
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            
            String name = mParser.getName();
            if (name.equals("etd")) {
                arrivalTimes.add(parseTrains());
            } else if (!name.equals("station")) {
                skip();
            }
        }
        
        return arrivalTimes;
    }
    
    /**
     * Parses an <etd> element into a {@link TrainsForDestination} object
     * @return a POJO holding the parsed BART estimated time of departure
     */
    private TrainsForDestination parseTrains() throws XmlPullParserException, IOException {
        TrainsForDestination trains = new TrainsForDestination();
        
        mParser.require(XmlPullParser.START_TAG, XML_NAMESPACE, "etd");
        
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            
            String name = mParser.getName();
            if (name.equals("destination")) {
                trains.setDestination(readString("destination"));
            } else if (name.equals("abbreviation")) {
                trains.setAbbreviation(readString("abbreviation"));
            } else if (name.equals("estimate")) {
                trains.addArrivalTime(parseArrivalTime());
            } else {
                skip();
            }
        }
        
        return trains;
    }
    
    /**
     * Parsed an <estimate> element into an {@link ArrivalTime} object
     * @return a POJO holding the parsed arrival time data
     */
    private ArrivalTime parseArrivalTime() throws XmlPullParserException, IOException {
        ArrivalTime arrivalTime = new ArrivalTime();
        
        mParser.require(XmlPullParser.START_TAG, XML_NAMESPACE, "estimate");
        
        while (mParser.next() != XmlPullParser.END_TAG) {
            if (mParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            
            String name = mParser.getName();
            if (name.equals("minutes")) {
                arrivalTime.setMinutes(readString("minutes"));
            } else if (name.equals("platform")) {
                arrivalTime.setPlatform(readString("platform"));
            } else if (name.equals("direction")) {
                arrivalTime.setDirection(readString("direction"));
            } else if (name.equals("length")) {
                arrivalTime.setLength(readInt("length"));
            } else if (name.equals("color")) {
                arrivalTime.setColor(readString("color"));
            } else if (name.equals("hexcolor")) {
                arrivalTime.setHexcolor(readString("hexcolor"));
            } else if (name.equals("bikeflag")) {
                arrivalTime.setBikeflag(readInt("bikeflag"));
            }
        }
        
        return arrivalTime;
    }
    

}
