package com.example.countrybase.activities;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.countrybase.API.Connector;
import com.example.countrybase.R;
import com.example.countrybase.activities.model.Pais;
import com.example.countrybase.base.BaseActivity;
import com.example.countrybase.base.CallInterface;
import com.squareup.picasso.Picasso;

public class DetallePaisActivity extends BaseActivity {

    private ImageView ivBanderaDetail;
    private EditText etNombre, etURL;
    private Button buttonAceptar, buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        ivBanderaDetail = findViewById(R.id.ivBanderaDetail);
        etNombre = findViewById(R.id.etNombre);
        etURL = findViewById(R.id.etURL);
        buttonAceptar = findViewById(R.id.buttonAceptar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        Pais pais=null;
        if(getIntent().getExtras()!=null){
            //viene con datos
            pais = (Pais) getIntent().getExtras().getSerializable("pais");
        }

        if(pais!=null){
            buttonAceptar.setVisibility(INVISIBLE);
            etURL.setEnabled(false);
            etNombre.setEnabled(false);
            etURL.setText(pais.getUrl());
            etNombre.setText(pais.getNombre());
            Picasso.get().load(pais.getUrl()).into(ivBanderaDetail);
        }

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        
        buttonAceptar.setOnClickListener(view -> {
            executeCall(new CallInterface<Pais>() {
                @Override
                public Pais doInBackground() throws Exception {
                    Pais pais = new Pais(etNombre.getText().toString(),
                            etURL.getText().toString());
                    return Connector.getConector().post(Pais.class,pais,"pais");
                }

                @Override
                public void doInUI(Pais data) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    intent.putExtra("pais",data);
                    finish();
                }
            });
        });

    }

}
