package com.example.foodapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter_forRecipes extends RecyclerView.Adapter<RecyclerViewAdapter_forRecipes.ViewHolder> {

    //much thanks to -
    // https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
    // https://developer.android.com/guide/topics/ui/layout/recyclerview
    // https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.html

    private List<Recipe> localDataSet;
    private ItemClickListener mClickListener;


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView recipeName;
        TextView recipeAuthor;
        TextView recipeDate;
        TextView recipeLikes;

        public ViewHolder(View view) {
            super(view);
            recipeName = (TextView) view.findViewById(R.id.textView_recipeName);
            recipeAuthor = (TextView) view.findViewById(R.id.textView_RecipeAuthor);
            recipeDate = (TextView) view.findViewById(R.id.textView_RecipeDate);
            recipeLikes = (TextView) view.findViewById(R.id.textView_RecipeLikes);
            view.setOnClickListener(this);
        }

        //getter. is this needed? maybe for clicking.
        public TextView get_textView_recipeName() {
            return recipeName;
        }

        public TextView get_textView_recipeAuthor(){
            return recipeAuthor;
        }

        public TextView get_textView_recipeDate(){
            return recipeDate;
        }

        public TextView get_textView_recipeLikes(){
            return recipeLikes;
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }


    }
    // Initialize the dataset of the Adapter.
    public RecyclerViewAdapter_forRecipes(Context context, List<Recipe> dataSet){
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        try {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recipe_results_layout, viewGroup, false);
            return new ViewHolder(view);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        String a = localDataSet[position];
//        a = "a = " + a;
//        Log.d(null, a);

        viewHolder.get_textView_recipeName().setText(localDataSet.get(position).getRecipeName());
        viewHolder.get_textView_recipeAuthor().setText(localDataSet.get(position).getHost());
        viewHolder.get_textView_recipeDate().setText(localDataSet.get(position).getCreate_time());
        try{
            viewHolder.get_textView_recipeLikes().setText(localDataSet.get(position).getRating());

        }
        catch (Exception e){
            viewHolder.get_textView_recipeLikes().setText("0");
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}
