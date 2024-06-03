package com.example.wikirick.Personajes;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.wikirick.Episodios.EpisodiosData;
import com.example.wikirick.Episodios.EpisodiosViewAdapter;
import com.example.wikirick.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EpViewHolder extends RecyclerView.ViewHolder {
    private TextView episodeNameTextView;

    public EpViewHolder(@NonNull View itemView) {
        super(itemView);
        episodeNameTextView = itemView.findViewById(R.id.episodeName);
    }

    public void bind(String episodeUrl) {

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                episodeUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String episodeName = response.getString("name");
                            episodeNameTextView.setText(episodeName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(itemView.getContext());
        queue.add(request);
    }

    private List<EpData> parseJson(JSONObject response)  {

        try {
            JSONArray results = response.getJSONArray("results");

            List<EpData> allCharacters = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject character = results.getJSONObject(i);

                String name = character.getString("name");
                EpData data = new EpData(name);
                allCharacters.add(data);
            }

            return allCharacters;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}