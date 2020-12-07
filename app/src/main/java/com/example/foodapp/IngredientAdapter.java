package com.example.foodapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context mCtx;
    private List<Ingredient> ingredientList;

    public IngredientAdapter(Context mCtx, List<Ingredient> ingredientsList) {
        this.mCtx = mCtx;
        this.ingredientList = ingredientsList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_artists, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        Ingredient artist = ingredientList.get(position);
        holder.textViewName.setText(artist.name);
        holder.textViewGenre.setText("Genre: " + artist.genre);
        holder.textViewAge.setText("Age: " + artist.age);
        holder.textViewCountry.setText("Country: " + artist.country);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewGenre, textViewAge, textViewCountry;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewGenre = itemView.findViewById(R.id.text_view_genre);
            textViewAge = itemView.findViewById(R.id.text_view_age);
            textViewCountry = itemView.findViewById(R.id.text_view_country);
        }
    }
}