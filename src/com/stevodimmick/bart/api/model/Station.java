package com.stevodimmick.bart.api.model;

import java.io.Serializable;

import com.stevodimmick.bart.database.StationTable;
import com.stevodimmick.bart.database.StationTable.StationsQuery;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * POJO to hold parsed BART station data. Station XML looks like:
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
 * More info here: 
 * http://api.bart.gov/api/stn.aspx?cmd=stns&key=MW9S-E7SL-26DU-VV8V
 * 
 * @author sdimmick
 */
public class Station implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String abbr;
    private float latitude;
    private float longitude;
    private String address;
    private String city;
    private String county;
    private String state;
    private String zipcode;
    
    /**
     * Default constructor
     */
    public Station() {
        
    }
    
    /**
     * Creates a {@link Station} from a {@link Cursor}
     * @param cursor a {@link Cursor} pointing to the {@link StationTable}
     */
    public Station(Cursor cursor) {
        this.name = cursor.getString(StationsQuery.NAME);
        this.abbr = cursor.getString(StationsQuery.ABBREVIATION);
        this.latitude = cursor.getFloat(StationsQuery.LATITUDE);
        this.longitude = cursor.getFloat(StationsQuery.LONGITUDE);
        this.address = cursor.getString(StationsQuery.ADDRESS);
        this.county = cursor.getString(StationsQuery.COUNTY);
        this.state = cursor.getString(StationsQuery.STATE);
        this.zipcode = cursor.getString(StationsQuery.ZIPCODE);
    }
    
    /**
     * Generates {@link ContentValues} for inserting this {@link Station} in a database
     * @return {@link ContentValues} suitable for inserting into a database
     */
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        
        cv.put(StationTable.NAME, this.name);
        cv.put(StationTable.ABBREVIATION, this.abbr);
        cv.put(StationTable.LATITUDE, this.latitude);
        cv.put(StationTable.LONGITUDE, this.longitude);
        cv.put(StationTable.ADDRESS, this.address);
        cv.put(StationTable.COUNTY, this.county);
        cv.put(StationTable.STATE, this.state);
        cv.put(StationTable.ZIPCODE, this.zipcode);
        
        return cv;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAbbr() {
        return abbr;
    }
    
    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
    
    public float getLatitude() {
        return latitude;
    }
    
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    
    public float getLongitude() {
        return longitude;
    }
    
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }
    
    public void setCounty(String county) {
        this.county = county;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
