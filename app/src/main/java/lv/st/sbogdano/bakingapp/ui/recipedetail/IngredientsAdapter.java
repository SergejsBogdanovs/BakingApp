package lv.st.sbogdano.bakingapp.ui.recipedetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.model.Ingredient;
import lv.st.sbogdano.bakingapp.ui.recipes.RecipesAdapter;

class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    private List<IngredientEntry> mIngredients;

    public IngredientsAdapter(List<IngredientEntry> ingredients) {
        mIngredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View ingredientView = inflater.inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IngredientEntry ingredientEntry = mIngredients.get(position);
        holder.mIngredient.setText(ingredientEntry.getIngredient());
        holder.mMeasure.setText(ingredientEntry.getMeasure());
        holder.mQuantity.setText(String.valueOf(ingredientEntry.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void setRecipes(List<IngredientEntry> data) {
        mIngredients.clear();
        mIngredients.addAll(data);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ingredient)
        TextView mIngredient;
        @BindView(R.id.measure)
        TextView mMeasure;
        @BindView(R.id.quantity)
        TextView mQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
