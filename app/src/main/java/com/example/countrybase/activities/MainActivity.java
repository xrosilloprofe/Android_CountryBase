package com.example.countrybase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countrybase.API.Connector;
import com.example.countrybase.R;
import com.example.countrybase.activities.model.Pais;
import com.example.countrybase.base.BaseActivity;
import com.example.countrybase.base.CallInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Collections;
import java.util.List;


public class MainActivity extends BaseActivity implements CallInterface<List<Pais>>,
        View.OnClickListener {

    private List<Pais> paises;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAnyadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        buttonAnyadir = findViewById(R.id.buttonAnyadir);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode()==RESULT_OK){
                        Pais pais = (Pais)
                                result.getData().getExtras().getSerializable("pais");
                        paises.add(pais);
                        recyclerView.getAdapter().notifyItemInserted(paises.size()-1);
                    }
                }
        );

        buttonAnyadir.setOnClickListener(view -> {
            Intent intent = new Intent(this, DetallePaisActivity.class);
            activityResultLauncher.launch(intent);
        });

        // Mostramos la barra de progreso
        showProgress();
        //ejecutamos la llamada a la API
        executeCall(this);
    }

    @Override
    public List<Pais> doInBackground() throws Exception {
        return Connector.getConector().getAsList(Pais.class,"pais");
    }

    // Una vez ya se ha realizado la llamada, ocultamos la barra de progreso y presentamos los datos
    @Override
    public void doInUI(List<Pais> data) {
        paises=data;
        AdaptadorRecycle adaptador =
                new AdaptadorRecycle(this,paises,this);
        recyclerView.setAdapter(adaptador);
    }

    @Override
    public void onClick(View view) {
        //enseñará un pais
        int posicion = recyclerView.getChildAdapterPosition(view);
        Pais pais = paises.get(posicion);

//        Toast.makeText(this, "Pais " + pais.getNombre(), Toast.LENGTH_SHORT)
//                .show();

        Intent intent = new Intent(this,DetallePaisActivity.class);
        intent.putExtra("pais",pais);
        startActivity(intent);

    }


}