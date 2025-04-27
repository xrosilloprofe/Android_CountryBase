package com.example.countrybase.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.countrybase.API.Connector;
import com.example.countrybase.R;
import com.example.countrybase.activities.model.Pais;
import com.example.countrybase.base.BaseActivity;
import com.example.countrybase.base.CallInterface;
import com.example.countrybase.base.ImageDownloader;
import com.example.countrybase.base.Parameters;


import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends BaseActivity implements CallInterface {

    private TextView txtView ;
    private TextView textViewDay;
    private TextView textViewDayOfWeek;
    private ImageView imageView;
    private Pais pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mostramos la barra de progreso y ejecutamos la llamada a la API
        showProgress();
        executeCall(this);
    }

    // Realizamos la llamada y recogemos los datos en un objeto Root
    @Override
    public void doInBackground() throws Exception {
        pais = Connector.getConector().get(Pais.class,"&lat=39.5862518&lon=-0.5411163");
    }

    // Una vez ya se ha realizado la llamada, ocultamos la barra de progreso y presentamos los datos
    @Override
    public void doInUI() {

    }

}