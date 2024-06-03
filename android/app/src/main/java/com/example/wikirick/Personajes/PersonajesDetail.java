package com.example.wikirick.Personajes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wikirick.R;
import com.example.wikirick.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonajesDetail extends AppCompatActivity  {

    private TextView nombrePersonaje;
    private TextView genero;
    private TextView estado;
    private TextView origen;
    private TextView especie;
    private ImageButton fav;
    private ImageView imagen;
    private RecyclerView episodios;
    private boolean favoriteado=true;
    private RequestQueue queue;
    static String host = "http://10.0.2.2:8000/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajedetail);

        queue = Volley.newRequestQueue(this);


        Intent intent = getIntent();
        String Id = intent.getStringExtra("id");
        String Nombre = intent.getStringExtra("nombre");
        String Origen = intent.getStringExtra("origen");
        String Estado = intent.getStringExtra("estado");
        String Especie = intent.getStringExtra("especie");
        String Genero = intent.getStringExtra("genero");
        String imageUrl = intent.getStringExtra("imagen");
        ArrayList<String> Episodios= intent.getStringArrayListExtra("episodes");




        nombrePersonaje = findViewById(R.id.nombrePersonaje);
        genero = findViewById(R.id.genero);
        estado=findViewById(R.id.estado);
        origen=findViewById(R.id.origen);
        especie=findViewById(R.id.especie);
        fav=findViewById(R.id.fav);
        imagen=findViewById(R.id.imagen);
        episodios=findViewById(R.id.epRecyclerView);





        EpAdapter adapter = new EpAdapter(Episodios);
        episodios.setAdapter(adapter);
        episodios.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



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



        knowFavorite(Id);




        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoriteado) {
                    unmarkAsFavorite(Id);


                } else {

                    markAsFavorite(Id, Nombre, imageUrl);

                }
            }
        });
    }

    private void knowFavorite(String characterId) {

        SharedPreferences preferences = getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        String sessionToken = preferences.getString("VALID_TOKEN", null);

        if (sessionToken == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                host + "characters/" + characterId + "/favorite",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean isFavorite = response.getBoolean("favorite");
                            favoriteado = isFavorite;
                            if (isFavorite) {
                                fav.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                            } else {
                                fav.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.grey));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(PersonajesDetail.this, "Error de red", Toast.LENGTH_SHORT).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            if (serverCode == 401) {
                                Toast.makeText(PersonajesDetail.this, "No autenticado", Toast.LENGTH_SHORT).show();
                            } else if (serverCode == 404) {
                                Toast.makeText(PersonajesDetail.this, "Personaje no encontrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PersonajesDetail.this, "Error: " + serverCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Session-Token", sessionToken);
                return headers;
            }
        };

        queue.add(request);
    }





    private void markAsFavorite(String characterId, String characterName, String characterImageUrl) {
        SharedPreferences preferences = getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        String sessionToken = preferences.getString("VALID_TOKEN", null);

        if (sessionToken == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("character_image_url", characterImageUrl);
            requestBody.put("character_name", characterName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                host + "characters/" + characterId + "/favorite",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        favoriteado=true;
                        fav.setColorFilter((ContextCompat.getColor(getApplicationContext(), R.color.yellow)));

                        Toast.makeText(PersonajesDetail.this, "Personaje marcado como favorito", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(PersonajesDetail.this, "Error de red", Toast.LENGTH_SHORT).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            if (serverCode == 401) {
                                Toast.makeText(PersonajesDetail.this, "No autenticado", Toast.LENGTH_SHORT).show();
                            } else if (serverCode == 404) {
                                Toast.makeText(PersonajesDetail.this, "Personaje no encontrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PersonajesDetail.this, "Error: " + serverCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Session-Token", sessionToken);
                return headers;
            }
        };

        queue.add(request);
    }

    private void unmarkAsFavorite(String characterId) {
        SharedPreferences preferences = getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
        String sessionToken = preferences.getString("VALID_TOKEN", null);

        if (sessionToken == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                host + "characters/" + characterId + "/favorite",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        favoriteado=false;
                        fav.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.grey));

                        Toast.makeText(PersonajesDetail.this, "Personaje desmarcado como favorito", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            Toast.makeText(PersonajesDetail.this, "Error de red", Toast.LENGTH_SHORT).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            if (serverCode == 401) {
                                Toast.makeText(PersonajesDetail.this, "No autenticado", Toast.LENGTH_SHORT).show();
                            } else if (serverCode == 404) {
                                Toast.makeText(PersonajesDetail.this, "Personaje no encontrado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PersonajesDetail.this, "Error: " + serverCode, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Session-Token", sessionToken);
                return headers;
            }
        };

        queue.add(request);
    }





}




















