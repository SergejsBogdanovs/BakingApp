package lv.st.sbogdano.bakingapp.data.api;

import android.arch.lifecycle.LiveData;

import java.util.List;

import lv.st.sbogdano.bakingapp.data.model.Recipe;
import retrofit2.http.GET;

public interface RecipesService {

    @GET("baking.json")
    LiveData<ApiResponse<List<Recipe>>> getRecipes();
}
