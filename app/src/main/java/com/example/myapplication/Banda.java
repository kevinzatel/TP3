package com.example.myapplication;

import java.util.ArrayList;

public class Banda extends User {

    private boolean busquedaActiva;
    private String[] tipoBusqueda;

    public Banda(String userName, String password, String instrument, boolean isBand, ArrayList<String> musicianSearching) {
        super(userName, password, null, instrument, null, isBand, musicianSearching);
    }

    public boolean isBusquedaActiva() {
        return busquedaActiva;
    }

    public void setBusquedaActiva(boolean busquedaActiva) {
        this.busquedaActiva = busquedaActiva;
    }

    public String[] getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String[] tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

}
