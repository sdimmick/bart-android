package com.stevodimmick.bart.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO to hold parsed destination departure estimates. The XML looks like:
 * 
 * <etd>
 *   <destination>Fremont</destination>
 *   <abbreviation>FRMT</abbreviation>
 *   <estimate>
 *     See {@link ArrivalTime}
 *   </estimate>
 *   ...
 * </etd>
 * 
 * More info here:
 * http://api.bart.gov/api/etd.aspx?cmd=etd&orig=RICH&key=MW9S-E7SL-26DU-VV8V
 *  
 * @author sdimmick
 */
public class TrainsForDestination implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String destination;
    private String abbreviation;
    private List<ArrivalTime> arrivalTimes;
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getAbbreviation() {
        return abbreviation;
    }
    
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    
    public List<ArrivalTime> getArrivalTimes() {
        return arrivalTimes;
    }
    
    public void setArrivalTimes(List<ArrivalTime> arrivalTimes) {
        this.arrivalTimes = arrivalTimes;
    }
    
    public void addArrivalTime(ArrivalTime arrivalTime) {
        if (this.arrivalTimes == null) {
            this.arrivalTimes = new ArrayList<ArrivalTime>();
        }
        
        this.arrivalTimes.add(arrivalTime);
    }

}
