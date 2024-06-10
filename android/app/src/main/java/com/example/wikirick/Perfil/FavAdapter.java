package com.example.wikirick.Perfil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikirick.Personajes.PersonajesData;
import com.example.wikirick.Personajes.PersonajesViewHolder;
import com.example.wikirick.R;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavViewHolder>{

    private List<FavData> personajes;

    private Activity activity;

    public FavAdapter(List<FavData> dataSet){
        this.personajes=dataSet;

    }


    @NonNull
    @Override

    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View personajeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.perfil_recycler_cell,parent,false);
        return new FavViewHolder(personajeView);
    }
    @Override
        public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        FavData dataForThisCell = personajes.get(position);
        holder.bind(dataForThisCell);

    }


    @Override
    public int getItemCount() {
        return personajes.size();
    }
}





