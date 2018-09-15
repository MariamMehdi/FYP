package com.example.mehdifamily.fxgcnn;

public class BanquetInfo {

    public String date;
    public String name;
    public String person;
    public String contactno;

    public BanquetInfo(){

    }
    public BanquetInfo(String date,String cliname,String personnum,String contactnum){
        this.date=date;
        this.name=cliname;
        this.person=personnum;
        this.contactno=contactnum;
    }

}
