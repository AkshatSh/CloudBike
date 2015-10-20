package com.example.joseph.cloudbikebackend;

import java.util.Date;

/**
 * Created by robbymitchell on 10/17/15.
 */
public class BikeDataManager {
    public double lat;
    public double lon;
    public Date timeStamp;

    public BikeDataManager(double lat, double lon, Date timestamp) {
        this.lat = lat;
        this.lon = lon;
        this.timeStamp = timeStamp;
    }
}