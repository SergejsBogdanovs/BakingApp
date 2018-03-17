package lv.st.sbogdano.bakingapp.ui.recipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{

    private List<RecipeEntry> mRecipes;

    public RecipesAdapter(List<RecipeEntry> recipes) {
        mRecipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View recipeView = inflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeEntry recipeEntry = mRecipes.get(position);
        holder.mRecipeName.setText(recipeEntry.getName());
        holder.mServings.setText(String.valueOf(recipeEntry.getServings()));
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setRecipes(List<RecipeEntry> data) {
        mRecipes.clear();
        mRecipes.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name)
        TextView mRecipeName;

        @BindView(R.id.servings)
        TextView mServings;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
