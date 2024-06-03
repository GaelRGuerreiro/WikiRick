
package com.example.wikirick.Personajes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikirick.R;

import java.util.ArrayList;
import java.util.List;

public class EpAdapter extends RecyclerView.Adapter<EpViewHolder> {
    private ArrayList<String> episodes;

    public EpAdapter(ArrayList<String> episodes) {

        this.episodes = episodes;

    }

    @NonNull
    @Override
    public EpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ep_recycler_cell, parent, false);
        return new EpViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull EpViewHolder holder, int position) {
        String episodeName = episodes.get(position);
        holder.bind(episodeName);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }
}