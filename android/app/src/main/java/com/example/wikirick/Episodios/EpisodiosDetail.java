package com.example.wikirick.Episodios;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.wikirick.R;
import com.example.wikirick.Util;


public class EpisodiosDetail extends AppCompatActivity  {

    private TextView nombreEpisodio;
    private TextView fechaSalida;
    private TextView episodio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodiodetail);



        Intent intent = getIntent();
        String Nombre = intent.getStringExtra("nombre");
        String Fecha = intent.getStringExtra("fecha");
        String Episodio = intent.getStringExtra("episodio");



        nombreEpisodio = findViewById(R.id.nombreEpisodio);
        fechaSalida = findViewById(R.id.fecha);
        episodio=findViewById(R.id.episodio);


        nombreEpisodio.setText(Nombre);
        fechaSalida.setText(Fecha);
        episodio.setText(Episodio);

    }

}





















