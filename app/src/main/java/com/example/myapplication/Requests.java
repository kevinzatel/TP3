package com.example.myapplication;

import java.io.Serializable;

public class Requests implements Serializable {

    private String idBand;
    private String idMusician;
    private String state;
    private String Date;

    public Requests() {
    }

    public Requests(String idBand, String idMusician, String state, String date) {
        this.idBand = idBand;
        this.idMusician = idMusician;
        this.state = state;
        this.Date = date;
    }

    public String getIdBand() {
        return idBand;
    }

    public void setIdBand(String idBand) {
        this.idBand = idBand;
    }

    public String getIdMusician() {
        return idMusician;
    }

    public void setIdMusician(String idMusician) {
        this.idMusician = idMusician;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

}
