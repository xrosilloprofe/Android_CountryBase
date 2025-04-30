package com.example.countrybase.activities.model;

import java.io.Serializable;



public class Pais implements Serializable {

    private String nombre;
    private String url;

    public Pais(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }
}

