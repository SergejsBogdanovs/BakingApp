package lv.st.sbogdano.bakingapp.ui.recipedetail;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.RecipesRepository;
import lv.st.sbogdano.bakingapp.data.Resource;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;
import lv.st.sbogdano.bakingapp.data.model.Ingredient;

public class RecipeDetailsViewModel extends ViewModel {

    private final RecipesRepository mRecipesRepository;
    private final Context mContext;

    public RecipeDetailsViewModel(Application context, RecipesRepository recipesRepository) {
        mContext = context;
        mRecipesRepository = recipesRepository;
    }

    public LiveData<Resource<List<IngredientEntry>>> getIngredients(Integer recipeId) {
        return mRecipesRepository.getIngredients(recipeId);
    }

    public LiveData<Resource<List<StepEntry>>> getSteps(Integer recipeId) {
        return mRecipesRepository.getSteps(recipeId);
    }
}
