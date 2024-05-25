package com.example.wikirick.Episodios;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikirick.R;
import com.example.wikirick.Util;

public class EpisodiosViewHolder extends RecyclerView.ViewHolder {

    private TextView episodioNameTextView;

    public EpisodiosViewHolder(@NonNull View itemView) {
        super(itemView);

        // Asigna las vistas a las variables
        episodioNameTextView = itemView.findViewById(R.id.episodioNameTextView);
    }

    public void bind(EpisodiosData dataForThisCell) {
        // Configura los datos del episodio en las vistas
        episodioNameTextView.setText(dataForThisCell.getName());

    }
}