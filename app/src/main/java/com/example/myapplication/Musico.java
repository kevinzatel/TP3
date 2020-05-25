package com.example.myapplication;

public class Musico extends User {
    private String instrument;

    public Musico(String userName, String password, String instrument, boolean isBand) {
        super(userName, password, null, instrument, null, isBand);
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }
}
