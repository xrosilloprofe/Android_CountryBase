package com.example.myweatherbase.base;

public class Parameters {

    // URL BASE
    public final static String URL_BASE = "https://api.openweathermap.org/data/2.5/";

    // URL OPTIONS
    public final static String API = "<put_your_API_Key_here>";
    public final static String LANG = "es";
    public final static String UNITS = "metric";
    public final static String URL_OPTIONS = "forecast?appid=" + API + "&lang=" + LANG + "&units=" + UNITS;

    // ICONS
    public final static String ICON_URL_PRE = "http://openweathermap.org/img/wn/";
    public static final String ICON_URL_POST = "@2x.png";

}
