package com.example.countrybase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countrybase.API.Connector;
import com.example.countrybase.R;
import com.example.countrybase.activities.model.Pais;
import com.example.countrybase.base.BaseActivity;
import com.example.countrybase.base.CallInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.util.Collections;
import java.util.List;


public class MainActivity extends BaseActivity implements CallInterface<List<Pais>>,
        View.OnClickListener {

    private List<Pais> paises;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonAnyadir;
    private Pais paisInsertar;
    private int posicionInsertar;
    private CallInterface<Pais> INSERTAR_PAIS = new CallInterface<Pais>() {
        @Override
        public Pais doInBackground() throws Exception {
            return Connector.getConector().post(Pais.class,paisInsertar,"pais");
        }

        @Override
        public void doInUI(Pais data) {
            if(data!=null|| data.equals(paisInsertar)){
                paises.add(posicionInsertar,paisInsertar);
                recyclerView.getAdapter().notifyItemInserted(posicionInsertar);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        buttonAnyadir = findViewById(R.id.buttonAnyadir);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if(result.getResultCode() == RESULT_OK){
                                Pais pais = (Pais) result.getData().getExtras().getSerializable("pais");
                                paises.add(pais);
                                recyclerView.getAdapter().notifyItemInserted(paises.size()-1);
                            }
                        });

        buttonAnyadir.setOnClickListener( view -> {
            Intent intent = new Intent(this, DetallePaisActivity.class);
            activityResultLauncher.launch(intent);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        int posIni = viewHolder.getAdapterPosition();
                        int posFin = target.getAdapterPosition();
                        Pais pais = paises.remove(posIni);
                        paises.add(posFin,pais);
                        recyclerView.getAdapter().notifyItemMoved(posIni,posFin);
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        Pais pais = paises.get(viewHolder.getAdapterPosition());
                        eliminarPais(pais);
                    }
                }
        );

        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Mostramos la barra de progreso
        showProgress();
        //ejecutamos la llamada a la API
        executeCall(this);
    }


    @Override
    public List<Pais> doInBackground() throws Exception {
        return Connector.getConector().getAsList(Pais.class,"pais");
    }

    @Override
    public void doInUI(List<Pais> data) {
        paises = data;
        AdaptadorRecycle adaptador = new AdaptadorRecycle(this,paises,this);
        recyclerView.setAdapter(adaptador);

    }

    @Override
    public void onClick(View view) {
        int posicion = recyclerView.getChildAdapterPosition(view);
        Pais pais = paises.get(posicion);
        Intent intent = new Intent(this, DetallePaisActivity.class);
        intent.putExtra("pais", pais);
        startActivity(intent);
    }

    private void eliminarPais(Pais pais){
        executeCall(new CallInterface<Pais>() {
            @Override
            public Pais doInBackground() throws Exception {
                return Connector.getConector().delete(Pais.class,"pais/"+pais.getNombre());
            }

            @Override
            public void doInUI(Pais data) {
                if (data != null || data.equals(pais)){
                    int posicion = paises.indexOf(pais);
                    paises.remove(posicion);
                    recyclerView.getAdapter().notifyItemRemoved(posicion);
                    paisInsertar = pais;
                    posicionInsertar=posicion;
                    Snackbar.make(recyclerView,"Borrado pais: " + pais.getNombre(), Snackbar.LENGTH_LONG).
                            setAction("Deshacer", view -> executeCall(INSERTAR_PAIS)).
                            show();
                }
            }
        });
    }
}