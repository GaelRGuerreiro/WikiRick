package com.example.wikirick.Personajes;

import android.app.Activity;
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
    }



    @Override
    public int getItemCount() {
        return personajes.size();
    }
}






