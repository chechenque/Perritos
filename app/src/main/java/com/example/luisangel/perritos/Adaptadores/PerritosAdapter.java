package com.example.luisangel.perritos.Adaptadores;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luisangel.perritos.Perrito;
import com.example.luisangel.perritos.R;

import java.util.ArrayList;

public class PerritosAdapter extends RecyclerView.Adapter<PerritosAdapter.PersonajeViewHolder>{
    ArrayList<Perrito> listaPerrito;
    Context context;
    //RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public PerritosAdapter(Context context, ArrayList<Perrito> listaPersonaje/*, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener*/){
        this.listaPerrito = listaPersonaje;
        //this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        this.context = context;
    }

    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder holder, int position){
        holder.nombre.setText(listaPerrito.get(position).getName());
        Glide.with(context).load(listaPerrito.get(position).getImagen()).into(holder.imagen);
        holder.imagen.setImageResource(R.drawable.gradient);
    }

    @Override
    public int getItemCount() {
        return listaPerrito.size();
    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        TextView nombre;
        ImageView imagen;
        CardView carta;

        public PersonajeViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.idNombre);
            imagen = (ImageView) itemView.findViewById(R.id.idImagen);
            //itemView.setOnClickListener(this);
            carta = (CardView) itemView.findViewById(R.id.cartaPersonaje);
        }

        /*public void onClick(View v){
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }*/
    }
}
