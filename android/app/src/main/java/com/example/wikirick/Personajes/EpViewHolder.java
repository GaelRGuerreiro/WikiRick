package com.example.wikirick.Personajes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wikirick.R;

public class EpViewHolder extends RecyclerView.ViewHolder {
    private TextView episodeNameTextView;

    public EpViewHolder(@NonNull View itemView) {
        super(itemView);
        episodeNameTextView = itemView.findViewById(R.id.episodeName);
    }

    public void bind(String episodeName) {
        episodeNameTextView.setText(episodeName);
    }
}