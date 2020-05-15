package com.example.myapplication;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private String description;
    private String turno;
    private boolean isBand;

    public User(String userName, String password, String instrument, boolean isBand){
        setUserName(userName);
        setPassword(password);
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

    public boolean getIsBand() {
        return isBand;
    }

    public void setIsBand(boolean isBand) {
        this.isBand = isBand;
    }
}
