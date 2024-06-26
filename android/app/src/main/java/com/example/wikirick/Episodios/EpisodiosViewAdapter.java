package com.example.wikirick.Episodios;

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

public class EpisodiosViewAdapter extends RecyclerView.Adapter<EpisodiosViewHolder>{

    private List<EpisodiosData> episodios;

    private Activity activity;

    public EpisodiosViewAdapter(List<EpisodiosData> dataSet,Activity activity){
        this.episodios=dataSet;
        this.activity=activity;

    }


    @NonNull
    @Override

    public EpisodiosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View episodioView = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodios_recycler_cell,parent,false);
        return new EpisodiosViewHolder(episodioView);
    }
    @Override
    public void onBindViewHolder(@NonNull EpisodiosViewHolder holder, int position) {
        EpisodiosData dataForThisCell = episodios.get(position);
        holder.bind(dataForThisCell);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, EpisodiosDetail.class);
                intent.putExtra("nombre",dataForThisCell.getName());
                intent.putExtra("fecha",dataForThisCell.getDate());
                intent.putExtra("episodio",dataForThisCell.getEpisode());

                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return episodios.size();
    }
}






