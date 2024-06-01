package com.example.wikirick.Personajes;

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


public class PersonajesDetail extends AppCompatActivity  {

    private TextView nombrePersonaje;
    private TextView genero;
    private TextView estado;
    private TextView origen;
    private TextView especie;
    private ImageButton fav;
    private ImageView imagen;
    private boolean favoriteado=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajedetail);



        Intent intent = getIntent();
        String Nombre = intent.getStringExtra("nombre");
        String Origen = intent.getStringExtra("origen");
        String Estado = intent.getStringExtra("estado");
        String Especie = intent.getStringExtra("especie");
        String Genero = intent.getStringExtra("genero");
        String imageUrl = intent.getStringExtra("imagen");



        nombrePersonaje = findViewById(R.id.nombrePersonaje);
        genero = findViewById(R.id.genero);
        estado=findViewById(R.id.estado);
        origen=findViewById(R.id.origen);
        especie=findViewById(R.id.especie);
        fav=findViewById(R.id.fav);
        imagen=findViewById(R.id.imagen);





        nombrePersonaje.setText(Nombre);
        genero.setText(Genero);

                switch(Estado){
            case "Alive":
                estado.setText(Estado);
                estado.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                break;
            case "Dead":
                estado.setText(Estado);
                estado.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                break;
            case "unknown":
                estado.setText(Estado);
                estado.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                break;
        }
        origen.setText(Origen);
        especie.setText(Especie);
        Util.downloadBitmapToImageView(imageUrl,imagen);






        fav.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(favoriteado){
                    favoriteado=false;
                    fav.setColorFilter((ContextCompat.getColor(getApplicationContext(), R.color.yellow)));
                }else{
                    favoriteado=true;
                    fav.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                }
            }
        });

    }

}





















