package com.example.countrybase.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countrybase.R;
import com.example.countrybase.activities.model.Pais;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorRecycle extends
        RecyclerView.Adapter<AdaptadorRecycle.ContenedorVista> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Pais> paises;
    private View.OnClickListener onClickListener;

    public AdaptadorRecycle(Context context, List<Pais> paises, View.OnClickListener onClickListener){
        this.context = context;
        this.onClickListener = onClickListener;
        this.paises = paises;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ContenedorVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.viewholder_layout, parent, false);
        view.setOnClickListener(onClickListener);
        return new ContenedorVista(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContenedorVista holder, int position) {
        Pais pais = paises.get(position);
        holder.tvNombre.setText(pais.getNombre());
        Picasso.get().load(pais.getUrl()).into(holder.ivBandera);
    }

    @Override
    public int getItemCount() {
        return paises.size();
    }

    public static class ContenedorVista extends RecyclerView.ViewHolder{
        private ImageView ivBandera;
        private TextView tvNombre;
        public ContenedorVista(@NonNull View itemView) {
            super(itemView);
            ivBandera = itemView.findViewById(R.id.ivBandera);
            tvNombre = itemView.findViewById(R.id.tvNombre);
        }
    }
}
