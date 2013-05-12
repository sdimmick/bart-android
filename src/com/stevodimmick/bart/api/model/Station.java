package com.stevodimmick.bart.api.model;

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
public class Station {
    private String name;
    private String abbr;
    private float latitude;
    private float longitude;
    private String address;
    private String city;
    private String county;
    private String state;
    private String zipcode;
    
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
