package com.example.myapplication;

import java.io.Serializable;

public class User implements Serializable {

    private String userName;
    private String password;
    private String instrument;
    private boolean isBand;

    public User() {
    }

    public User(String userName, String password, String instrument, boolean isBand) {
        this.userName = userName;
        this.password = password;
        this.instrument = instrument;
        this.isBand = isBand;
    }

    public String getUserName() {
        return userName;
    }

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

    public boolean isBand() {
        return isBand;
    }

    public void setBand(boolean band) {
        isBand = band;
    }

}
