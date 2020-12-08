package com.example.foodapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_Ingrideient, parent, false);
        return new IngredientViewHolder(view);
    }
    

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.textViewName.setText(ingredient.uid);
        holder.textViewGenre.setText(ingredient.engName);
        holder.textViewAge.setText(ingredient.units);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewGenre, textViewAge, textViewCountry;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewGenre = itemView.findViewById(R.id.text_view_genre);
            textViewAge = itemView.findViewById(R.id.text_view_age);
            textViewCountry = itemView.findViewById(R.id.text_view_country);
        }
    }
}