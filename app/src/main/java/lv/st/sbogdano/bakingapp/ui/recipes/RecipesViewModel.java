package lv.st.sbogdano.bakingapp.ui.recipes;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.RecipesRepository;
import lv.st.sbogdano.bakingapp.data.Resource;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;

/**
 * {@link ViewModel} for {@link RecipesActivity}
 */
public class RecipesViewModel extends ViewModel{

    private final RecipesRepository mRecipesRepository;
    private LiveData<Resource<List<RecipeEntry>>> mRecipes;
    private final Context mContext;


    public RecipesViewModel(Application context, RecipesRepository recipesRepository) {
        mContext = context;
        mRecipesRepository = recipesRepository;
    }

    public void start() {
        loadRecipes(false);
    }

    private void loadRecipes(boolean forceUpdate) {
        if (forceUpdate) {
            mRecipes = mRecipesRepository.loadRecipes(true);
            return;
        }

        mRecipes = mRecipesRepository.loadRecipes(false);
    }

    public LiveData<Resource<List<RecipeEntry>>> getRecipes() {
        return mRecipes;
    }

}
