package com.example.myapplication;

public class Requests {

    private int Id;
    private String idBand;
    private String idMusician;
    private String state;

    public Requests() {
    }

    public Requests(int id, String idBand, String idMusician, String state) {
        Id = id;
        this.idBand = idBand;
        this.idMusician = idMusician;
        this.state = state;
    }


}
