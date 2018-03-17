package lv.st.sbogdano.bakingapp.util;

import android.content.Context;

import lv.st.sbogdano.bakingapp.AppExecutors;
import lv.st.sbogdano.bakingapp.data.RecipesRepository;
import lv.st.sbogdano.bakingapp.data.RecipesRepositoryImpl;
import lv.st.sbogdano.bakingapp.data.database.RecipesDatabase;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class InjectorUtils {

    public static RecipesRepository provideRecipesRepository(Context applicationContext) {
        checkNotNull(applicationContext);
        RecipesDatabase database = RecipesDatabase.getInstance(applicationContext);
        AppExecutors executors = AppExecutors.getInstance();

        return RecipesRepositoryImpl.getInstance(
                database.recipesDao(),
                database.ingredientsDao(),
                database.stepsDao(),
                executors);
    }
}
