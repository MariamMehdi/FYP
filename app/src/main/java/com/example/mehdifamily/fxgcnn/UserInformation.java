package com.example.mehdifamily.fxgcnn;

public class UserInformation {

    public String name,owname,owncontact,bancapacity;
    public double latitude;
    public double longitude;

    public UserInformation(){

    }
    public UserInformation(String name,String owname,String owncontact,double latitude,double longitude,String bancapacity){
        this.name=name;
        this.owname=owname;
        this.owncontact=owncontact;
        this.bancapacity=bancapacity;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}