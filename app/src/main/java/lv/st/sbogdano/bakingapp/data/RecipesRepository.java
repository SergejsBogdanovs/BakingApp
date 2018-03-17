package lv.st.sbogdano.bakingapp.data;


import android.arch.lifecycle.LiveData;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;

public interface RecipesRepository {

    LiveData<Resource<List<RecipeEntry>>> loadRecipes(boolean forceUpdate);


}
