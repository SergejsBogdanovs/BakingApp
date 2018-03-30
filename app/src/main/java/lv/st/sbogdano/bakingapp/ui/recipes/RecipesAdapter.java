package lv.st.sbogdano.bakingapp.ui.recipes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder>{

    private List<RecipeEntry> mRecipes;
    private Context mContext;

    private final RecipesAdapterOnItemClickHandler mClickHandler;

    public interface RecipesAdapterOnItemClickHandler {
        void onItemClick(RecipeEntry recipeEntry);
    }

    public RecipesAdapter(Context context, List<RecipeEntry> recipes, RecipesAdapterOnItemClickHandler clickHandler) {
        mContext = context;
        mRecipes = recipes;
        mClickHandler = clickHandler;
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
        if (!TextUtils.isEmpty(recipeEntry.getImage())) {
            Glide.with(mContext)
                    .load(recipeEntry.getImage())
                    .into(holder.mRecipeImage);
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_name)
        TextView mRecipeName;

        @BindView(R.id.servings)
        TextView mServings;

        @BindView(R.id.recipe_image)
        ImageView mRecipeImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            RecipeEntry recipeEntry = mRecipes.get(getAdapterPosition());
            mClickHandler.onItemClick(recipeEntry);
        }
    }
}
