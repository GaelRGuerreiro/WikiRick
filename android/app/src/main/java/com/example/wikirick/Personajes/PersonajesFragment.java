package com.example.wikirick.Personajes;

import static android.view.View.VISIBLE;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonajesFragment extends Fragment {
    private RecyclerView recyclerView;

    private Activity activity;
    private String next;
    private String prev;

    private ImageButton lupa;

    private ImageButton nextButton;
    private ImageButton prevButton;
    private String busqueda;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.personajes_fragment, container, false);


        nextButton = layout.findViewById(R.id.nextButton);


        prevButton = layout.findViewById(R.id.prevButton);

        lupa=layout.findViewById(R.id.lupa);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCharacterRequest(next);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCharacterRequest(prev);
            }
        });

        lupa.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                EditText campoTexto = layout.findViewById(R.id.busqueda);
                busqueda= campoTexto.getText().toString();
                Log.d("Valor de búsqueda", busqueda);
                sendSearchRequest(busqueda);
            }
        });

        recyclerView = layout.findViewById(R.id.personajes_recycler); // Obtener una referencia al RecyclerView desde el diseño
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sendCharacterRequest("https://rickandmortyapi.com/api/character");

        return layout;
    }







    public void sendCharacterRequest(String url){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        // Llamar al método parseJson para parsear la respuesta JSON
                        List<PersonajesData> personajeDataArray = null;
                        personajeDataArray = parseJson(response);



                        if (personajeDataArray != null) {

                            // Crear un adaptador con los datos parseados y configurar el RecyclerView
                            PersonajeViewAdpter adapter = new PersonajeViewAdpter(personajeDataArray, activity);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new GridLayoutManager(activity,2));

                            ajusteVisibilidadBotones();

                        } else {
                            // Manejar el caso en que ocurra un error al parsear la respuesta JSON
                            Toast.makeText(activity, "Error al parsear la respuesta JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar los errores de la solicitud y mostrar un Toast con el mensaje de error.
                        Toast.makeText(activity, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Agregar la solicitud a la cola de Volley para su procesamiento.
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }



    public void sendSearchRequest(String search){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://rickandmortyapi.com/api/character/?name="+search,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        // Llamar al método parseJson para parsear la respuesta JSON
                        List<PersonajesData> personajeDataArray = null;
                        personajeDataArray = parseJson(response);



                        if (personajeDataArray != null) {

                            // Crear un adaptador con los datos parseados y configurar el RecyclerView
                            PersonajeViewAdpter adapter = new PersonajeViewAdpter(personajeDataArray, activity);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new GridLayoutManager(activity,2));

                            ajusteVisibilidadBotones();

                        } else {
                            // Manejar el caso en que ocurra un error al parsear la respuesta JSON
                            Toast.makeText(activity, "Error al parsear la respuesta JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar los errores de la solicitud y mostrar un Toast con el mensaje de error.
                        Toast.makeText(activity, "No existen personajes con dicho nombre.",Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Agregar la solicitud a la cola de Volley para su procesamiento.
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }



    private void ajusteVisibilidadBotones(){

        if(next!=null){
            nextButton.setVisibility(View.VISIBLE);

        }else{
            nextButton.setVisibility(View.INVISIBLE);
        }

        if(prev!=null){
            prevButton.setVisibility(View.VISIBLE);
        }else{
            prevButton.setVisibility(View.INVISIBLE);
        }
    }


    private List<PersonajesData> parseJson(JSONObject response)  {
        try {
            next = response.getJSONObject("info").getString("next");

            //According to RFC 8259 a Json value can be null. next comes as null but getString is
            //providing us a "null" String. This is a workaround.

            if (next.equals("null")){
                next=null;
            }
        }catch(JSONException e){
            next=null;
        }
        try {
            prev = response.getJSONObject("info").getString("prev");

            //According to RFC 8259 a Json value can be null. next comes as null but getString is
            //providing us a "null" String. This is a workaround.

            if(prev.equals("null")){
                prev=null;
            }
        }catch(JSONException e){
            prev=null;
        }

        try {
            JSONArray results = response.getJSONArray("results");

            List<PersonajesData> allCharacters = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject character = results.getJSONObject(i);
                String id = character.getString("id");
                String name = character.getString("name");
                String imageUrl = character.getString("image");
                String gender = character.getString("gender");
                String status = character.getString("status");
                String originName = character.getJSONObject("origin").getString("name");
                String species = character.getString("species");

                PersonajesData data = new PersonajesData(id,name, imageUrl,next, prev,gender, status, originName, species);
                allCharacters.add(data);
            }

            return allCharacters;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
