package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String userName;
    private String password;
    private String descripcion;
    private String instrument;
    private String timeOfDay;
    private boolean isBand;
    private ArrayList<String> musicianSearching;

    public User() {}

    public User(String userName, String password, String descripcion, String instrument, String timeOfDay, boolean isBand, ArrayList<String> musicianSearching) {
        this.userName = userName;
        this.password = password;
        this.descripcion = descripcion;
        this.instrument = instrument;
        this.timeOfDay = timeOfDay;
        this.isBand = isBand;
        this.musicianSearching = musicianSearching;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public boolean isBand() { return isBand; }

    public void setBand(boolean band) {
        isBand = band;
    }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTimeOfDay() { return timeOfDay; }

    public void setTimeOfDay(String timeOfDay) { this.timeOfDay = timeOfDay; }

    public ArrayList<String> getMusicianSearching() {
        return musicianSearching;
    }

    public void setMusicianSearching(ArrayList<String> musicianSearching) {
        this.musicianSearching = musicianSearching;
    }
}
