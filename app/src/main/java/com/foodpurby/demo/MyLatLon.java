package com.foodpurby.demo;

/**
 * Created by android1 on 2/2/2016.
 */
public class MyLatLon {

    private Double Lat;
    private Double Lon;
    private Integer State;
    private String Name;

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLon(Double lon) {
        Lon = lon;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public MyLatLon(Double Lat,Double Lon, Integer State, String Name) {
        this.Lat = Lat;
        this.Lon = Lon;
        this.State = State;
        this.Name = Name;
    }
}
