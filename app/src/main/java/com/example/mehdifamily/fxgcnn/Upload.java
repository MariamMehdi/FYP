package com.example.mehdifamily.fxgcnn;

class Upload {
    private String mImageURI;
    private String banId;
    public String name,owname,owncontact,bancapacity;
    public double latitude;
    public double longitude;

    public Upload(){

    }
    public Upload(String banId,String name,String owname,String owncontact,double latitude,double longitude,String bancapacity,String imageURI){
        this.banId = banId;
        this.name=name;
        this.owname=owname;
        this.owncontact=owncontact;
        this.bancapacity=bancapacity;
        this.latitude=latitude;
        this.longitude=longitude;
        mImageURI = imageURI;
    }

    public String getBanId() {
        return banId;
    }

    public void setBanId(String banId)
    {
        this.banId = banId;
    }

    public String getBanName() {
        return name;
    }

    public void setBanName(String name)
    {
        this.name = name;
    }

    public String getOwName() {
        return owname;
    }

    public void setOwName(String owname)
    {
        this.owname = owname;
    }

    public String getOwnCont() {
        return owncontact;
    }

    public void setOwnCont(String owncontact)
    {
        this.owncontact = owncontact;
    }

    public String getBanCap() {
        return bancapacity;
    }

    public void setBanCap(String bancapacity)
    {
        this.bancapacity= bancapacity;
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

    public String getImageURI()
    {
        return mImageURI;
    }
    public void setImageURI(String imageURI)
    {
        mImageURI = imageURI;
    }
}
