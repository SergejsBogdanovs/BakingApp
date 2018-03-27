package lv.st.sbogdano.bakingapp.data;


import android.arch.lifecycle.LiveData;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;

public interface RecipesRepository {

    LiveData<Resource<List<RecipeEntry>>> loadRecipes(boolean forceUpdate);

    LiveData<Resource<List<IngredientEntry>>> getIngredients(Integer recipeId);

    LiveData<Resource<List<StepEntry>>> getSteps(Integer recipeId);
}
