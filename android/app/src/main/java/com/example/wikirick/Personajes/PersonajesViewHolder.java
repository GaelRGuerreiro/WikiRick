package com.example.wikirick.Personajes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikirick.R;
import com.example.wikirick.Util;

public class PersonajesViewHolder extends RecyclerView.ViewHolder {

    private TextView characterNameTextView;
    private ImageView characterImageView;

    public PersonajesViewHolder(@NonNull View itemView) {
        super(itemView);

        // Asigna las vistas a las variables
        characterImageView = itemView.findViewById(R.id.characterImageview);
        characterNameTextView = itemView.findViewById(R.id.characterNameTextView);
    }

    public void bind(PersonajesData dataForThisCell) {
        // Configura los datos del personaje en las vistas
        characterNameTextView.setText(dataForThisCell.getNombrePersonaje());

        // Descarga la imagen del personaje y la asigna al ImageView
        Util.downloadBitmapToImageView(dataForThisCell.getImageUrl(), characterImageView);
    }
}