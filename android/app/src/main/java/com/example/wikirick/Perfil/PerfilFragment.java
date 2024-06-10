package com.example.wikirick.Perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.wikirick.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilFragment extends Fragment {

    private TextView textViewUsername;
    private TextView textViewEmail;
    private RequestQueue queue;
    private RecyclerView recyclerView;
    private FavAdapter favAdapter;
    private List<FavData> characterList;
    private ImageView loadingImage;

    private Context context;
    static String host = "http://192.168.166.26:8000/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.perfil_fragment, container, false);

        textViewUsername = view.findViewById(R.id.nombreUsuario);
        textViewEmail = view.findViewById(R.id.correoUsuario);

        loadingImage = view.findViewById(R.id.loadingImage);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(loadingImage);
        Glide.with(this).load(R.drawable.loading).into(imageViewTarget);

        recyclerView = view.findViewById(R.id.episodios_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        characterList = new ArrayList<>();
        favAdapter = new FavAdapter(characterList);
        recyclerView.setAdapter(favAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));


        context = getActivity();
        queue = Volley.newRequestQueue(context);

        getProfileData();

        return view;
    }

    private void getProfileData() {
        loadingImage.setVisibility(View.VISIBLE);
        String url = host + "profile";
        SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", Context.MODE_PRIVATE);
        String sessionToken = preferences.getString("VALID_TOKEN", null);

        if (sessionToken != null) {
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            loadingImage.setVisibility(View.GONE);
                            try {
                                String username = response.getString("username");
                                String email = response.getString("email");

                                textViewUsername.setText(username);
                                textViewEmail.setText(email);

                                JSONArray favoriteCharacters = response.getJSONArray("favorite_characters");
                                for (int i = 0; i < favoriteCharacters.length(); i++) {
                                    JSONObject characterObj = favoriteCharacters.getJSONObject(i);
                                    String name = characterObj.getString("name");
                                    String imageUrl = characterObj.getString("image_url");
                                    characterList.add(new FavData(name, imageUrl));
                                }

                                // Asegúrate de que el adaptador se notifique sobre los cambios
                                favAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "Datos cargados correctamente", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error al obtener los datos del perfil", Toast.LENGTH_SHORT).show();
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
        } else {
            Toast.makeText(context, "No se encontró el token de sesión", Toast.LENGTH_SHORT).show();
        }
    }
}