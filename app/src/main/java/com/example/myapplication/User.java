package com.example.myapplication;

public class User {
    private String userName;
    private String password;
    private String instrument;
    private boolean isBand;

    public User(String userName, String password, String instrument, boolean isBand){
        setUserName(userName);
        setPassword(password);
        setInstrument(instrument);
        setIsBand(isBand);
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

    public boolean getIsBand() {
        return isBand;
    }

    public void setIsBand(boolean isBand) {
        this.isBand = isBand;
    }
}
