package com.example.wikirick.Personajes;

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

public class PersonajeViewAdpter extends RecyclerView.Adapter<PersonajesViewHolder>{

    private List<PersonajesData> personajes;

    private Activity activity;

    public PersonajeViewAdpter(List<PersonajesData> dataSet,Activity activity){
        this.personajes=dataSet;
        this.activity=activity;

    }


    @NonNull
    @Override

    public PersonajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View personajeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.personaje_recycler_cell,parent,false);
        return new PersonajesViewHolder(personajeView);
    }
    @Override
    public void onBindViewHolder(@NonNull PersonajesViewHolder holder, int position) {
        PersonajesData dataForThisCell = personajes.get(position);
        holder.bind(dataForThisCell);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, PersonajesDetail.class);
                intent.putExtra("id",dataForThisCell.getId());
                intent.putExtra("nombre",dataForThisCell.getNombrePersonaje());
                intent.putExtra("imagen",dataForThisCell.getImageUrl());
                intent.putExtra("genero",dataForThisCell.getGenero());
                intent.putExtra("estado",dataForThisCell.getEstado());
                intent.putExtra("origen",dataForThisCell.getOrigen());
                intent.putExtra("especie",dataForThisCell.getEspecie());
                intent.putStringArrayListExtra("episodes",dataForThisCell.getEpisodes());

                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return personajes.size();
    }
}





