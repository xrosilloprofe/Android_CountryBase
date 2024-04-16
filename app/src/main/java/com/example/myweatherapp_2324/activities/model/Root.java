package com.example.myweatherapp_2324.activities.model;

// Class obtained from https://json2csharp.com/code-converters/json-to-pojo
// and modified by adding Serializable interface

import java.io.Serializable;
import java.util.ArrayList;

public class Root implements Serializable {
    public String cod;
    public int message;
    public int cnt;
    public ArrayList<List> list;
    public City city;
    public String getCity(){
        return city.name;
    }

}

