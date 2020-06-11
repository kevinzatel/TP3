package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String userName;
    private String password;
    private String nickname;
    private String latitude;
    private String longitude;
    private String district;
    private String instrument;
    private String timeOfDay;
    private String phone;
    private boolean isBand;
    private boolean active;
    private ArrayList<String> musicianSearching;

    public User() {}

    public User(String userName, String password, boolean isBand) {
        this.userName = userName;
        this.password = password;
        this.isBand = isBand;
        this.active = false;
        this.nickname = null;
        this.latitude = null;
        this.longitude = null;
        this.district = null;
        this.instrument = null;
        this.timeOfDay = null;
        this.phone = null;
        this.musicianSearching = null;
    }

    public User(String userName, String password, String nickname, String latitude, String longitude, String district, String instrument, String timeOfDay, String phone, boolean isBand, boolean active, ArrayList<String> musicianSearching) {
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.district = district;
        this.instrument = instrument;
        this.timeOfDay = timeOfDay;
        this.phone = phone;
        this.isBand = isBand;
        this.active = active;
        this.musicianSearching = musicianSearching;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getLatitude() { return latitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getInstrument() { return instrument; }

    public void setInstrument(String instrument) { this.instrument = instrument; }

    public boolean isBand() { return isBand; }

    public void setBand(boolean band) { isBand = band; }

    public String getTimeOfDay() { return timeOfDay; }

    public void setTimeOfDay(String timeOfDay) { this.timeOfDay = timeOfDay; }

    public ArrayList<String> getMusicianSearching() { return musicianSearching; }

    public void setMusicianSearching(ArrayList<String> musicianSearching) { this.musicianSearching = musicianSearching; }
}
