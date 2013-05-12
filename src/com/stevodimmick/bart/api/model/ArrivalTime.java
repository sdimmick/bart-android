package com.stevodimmick.bart.api.model;

import java.io.Serializable;

/**
 * POJO to hold parsed departure estimates. The XML looks like:
 * 
 * <estimate>
 *   <minutes>14</minutes>
 *   <platform>2</platform>
 *   <direction>South</direction>
 *   <length>6</length>
 *   <color>ORANGE</color>
 *   <hexcolor>#ff9933</hexcolor>
 *   <bikeflag>1</bikeflag>
 * </estimate>
 * 
 * More info here:
 * http://api.bart.gov/api/etd.aspx?cmd=etd&orig=RICH&key=MW9S-E7SL-26DU-VV8V
 *  
 * @author sdimmick
 */
public class ArrivalTime implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String minutes;
    private String platform;
    private String direction;
    private int length;
    private String color;
    private String hexcolor;
    private int bikeflag;
    
    public String getMinutes() {
        return minutes;
    }
    
    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
    
    public String getPlatform() {
        return platform;
    }
    
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getHexcolor() {
        return hexcolor;
    }
    
    public void setHexcolor(String hexcolor) {
        this.hexcolor = hexcolor;
    }
    
    public int getBikeflag() {
        return bikeflag;
    }
    
    public void setBikeflag(int bikeflag) {
        this.bikeflag = bikeflag;
    }

}
