package com.example.wikirick.Episodios;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.wikirick.MainActivity;
import com.example.wikirick.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpisodiosFragment extends Fragment {
    private RecyclerView recyclerView;
    private Activity activity;
    private String next;
    private String prev;
    private ImageButton lupa;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private String busqueda;
    public EditText campoTexto;
    private ImageView loadingImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.episodios_fragment, container, false);

        nextButton = layout.findViewById(R.id.nextButton);
        prevButton = layout.findViewById(R.id.prevButton);
        lupa = layout.findViewById(R.id.lupa);
        campoTexto = layout.findViewById(R.id.busqueda);
        loadingImage = layout.findViewById(R.id.loadingImage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(loadingImage);
        Glide.with(this).load(R.drawable.loading).into(imageViewTarget);
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
                busqueda = campoTexto.getText().toString();
                sendSearchRequest(busqueda);
            }
        });

        campoTexto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).onBackPressedCallback.setEnabled(false);
                }            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    Activity activity = getActivity();
                    if (activity instanceof MainActivity) {
                        ((MainActivity) activity).onBackPressedCallback.setEnabled(false);
                    }
                } else {
                    Activity activity = getActivity();
                    if (activity instanceof MainActivity) {
                        ((MainActivity) activity).onBackPressedCallback.setEnabled(true);
                    }
                }
            }
        });

        recyclerView = layout.findViewById(R.id.episodios_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sendCharacterRequest("https://rickandmortyapi.com/api/episode");

        return layout;
    }

    public void sendCharacterRequest(String url) {
        loadingImage.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        loadingImage.setVisibility(View.GONE); // Ocultar el ImageView
                        List<EpisodiosData> personajeDataArray = parseJson(response);

                        if (personajeDataArray != null) {
                            EpisodiosViewAdapter adapter = new EpisodiosViewAdapter(personajeDataArray, activity);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                            ajusteVisibilidadBotones();
                        } else {
                            Toast.makeText(activity, "Error al parsear la respuesta JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingImage.setVisibility(View.GONE); // Ocultar el ImageView
                        Toast.makeText(activity, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }

    public void sendSearchRequest(String search) {
        loadingImage.setVisibility(View.VISIBLE); // Mostrar el ImageView
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                "https://rickandmortyapi.com/api/episode/?name=" + search,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingImage.setVisibility(View.GONE); // Ocultar el ImageView
                        List<EpisodiosData> episodioDataArray = parseJson(response);

                        if (episodioDataArray != null) {
                            EpisodiosViewAdapter adapter = new EpisodiosViewAdapter(episodioDataArray, activity);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                            ajusteVisibilidadBotones();
                        } else {
                            Toast.makeText(activity, "Error al parsear la respuesta JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingImage.setVisibility(View.GONE); // Ocultar el ImageView
                        Toast.makeText(activity, "No existen episodios con dicho nombre.", Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(request);
    }

    private void ajusteVisibilidadBotones() {
        if (next != null) {
            nextButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setVisibility(View.INVISIBLE);
        }

        if (prev != null) {
            prevButton.setVisibility(View.VISIBLE);
        } else {
            prevButton.setVisibility(View.INVISIBLE);
        }
    }

    private List<EpisodiosData> parseJson(JSONObject response) {
        try {
            next = response.getJSONObject("info").getString("next");
            if (next.equals("null")) {
                next = null;
            }
        } catch (JSONException e) {
            next = null;
        }
        try {
            prev = response.getJSONObject("info").getString("prev");
            if (prev.equals("null")) {
                prev = null;
            }
        } catch (JSONException e) {
            prev = null;
        }

        try {
            JSONArray results = response.getJSONArray("results");
            List<EpisodiosData> allCharacters = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject character = results.getJSONObject(i);
                String name = character.getString("name");
                String airDate = character.getString("air_date");
                String episode = character.getString("episode");
                EpisodiosData data = new EpisodiosData(next, prev, name, airDate, episode);
                allCharacters.add(data);
            }

            return allCharacters;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
