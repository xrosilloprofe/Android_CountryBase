package com.example.countrybase.activities;

import android.os.Bundle;

import com.example.countrybase.API.Connector;
import com.example.countrybase.R;
import com.example.countrybase.activities.model.Pais;
import com.example.countrybase.base.BaseActivity;
import com.example.countrybase.base.CallInterface;


import java.util.List;


public class MainActivity extends BaseActivity implements CallInterface<List<Pais>> {

    private List<Pais> paises;

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
    public List<Pais> doInBackground() throws Exception {
        return Connector.getConector().getAsList(Pais.class,"pais");
    }

    // Una vez ya se ha realizado la llamada, ocultamos la barra de progreso y presentamos los datos
    @Override
    public void doInUI(List<Pais> data) {
        paises=data;
    }

}