package lv.st.sbogdano.bakingapp.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import lv.st.sbogdano.bakingapp.AppExecutors;
import lv.st.sbogdano.bakingapp.data.api.ApiResponse;
import lv.st.sbogdano.bakingapp.data.api.RecipesService;
import lv.st.sbogdano.bakingapp.data.api.ServiceGenerator;
import lv.st.sbogdano.bakingapp.data.database.dao.IngredientsDao;
import lv.st.sbogdano.bakingapp.data.database.dao.RecipesDao;
import lv.st.sbogdano.bakingapp.data.database.dao.StepsDao;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.StepEntry;
import lv.st.sbogdano.bakingapp.data.model.Ingredient;
import lv.st.sbogdano.bakingapp.data.model.Recipe;
import lv.st.sbogdano.bakingapp.data.model.Step;
import lv.st.sbogdano.bakingapp.util.RecipeTransformer;

public class RecipesRepositoryImpl implements RecipesRepository {

    private static String TAG = RecipesRepositoryImpl.class.getSimpleName();

    // For Singleton instantiation
    private volatile static RecipesRepositoryImpl INSTANCE = null;
    private final RecipesDao mRecipesDao;
    private final IngredientsDao mIngredientsDao;
    private final StepsDao mStepsDao;
    private final AppExecutors mExecutors;
    private final RecipesService mRecipesService = ServiceGenerator.createService(RecipesService.class);

    private RecipesRepositoryImpl(RecipesDao recipesDao,
                                  IngredientsDao ingredientsDao,
                                  StepsDao stepsDao,
                                  AppExecutors executors) {
        mRecipesDao = recipesDao;
        mIngredientsDao = ingredientsDao;
        mStepsDao = stepsDao;
        mExecutors = executors;
    }

    public static RecipesRepositoryImpl getInstance(
            RecipesDao recipesDao,
            IngredientsDao ingredientsDao,
            StepsDao stepsDao,
            AppExecutors executors) {
        if (INSTANCE == null) {
            synchronized (RecipesRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipesRepositoryImpl(recipesDao, ingredientsDao, stepsDao, executors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<List<RecipeEntry>>> loadRecipes(boolean forceUpdate) {
        return new NetworkBoundResource<List<RecipeEntry>, List<Recipe>>(mExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<Recipe> item) {
                List<RecipeEntry> recipeEntries = RecipeTransformer.transformRecipes(item);
                mRecipesDao.insertAll(recipeEntries);
                updateIngredientDb(item);
                updateStepDb(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RecipeEntry> data) {
                return data == null || data.isEmpty() || forceUpdate;
            }

            @NonNull
            @Override
            protected LiveData<List<RecipeEntry>> loadFromDb() {
                return mRecipesDao.getRecipes();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Recipe>>> createCall() {
                return mRecipesService.getRecipes();
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<IngredientEntry>>> getIngredients(Integer recipeId) {
        return new NetworkBoundResource<List<IngredientEntry>, List<Ingredient>>(mExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<Ingredient> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<IngredientEntry> data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<IngredientEntry>> loadFromDb() {
                return mIngredientsDao.getIngredientsForRecipe(recipeId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Ingredient>>> createCall() {
                return null;
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<StepEntry>>> getSteps(Integer recipeId) {
        return new NetworkBoundResource<List<StepEntry>, List<Step>>(mExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<Step> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<StepEntry> data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<StepEntry>> loadFromDb() {
                return mStepsDao.getStepsForRecipe(recipeId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Step>>> createCall() {
                return null;
            }
        }.asLiveData();
    }

    private void updateStepDb(List<Recipe> item) {
        List<StepEntry> stepEntries = RecipeTransformer.transformSteps(item);
        mStepsDao.insertAll(stepEntries);
    }

    private void updateIngredientDb(List<Recipe> item) {
        List<IngredientEntry> ingredientEntries = RecipeTransformer.transformIngredients(item);
        mIngredientsDao.insertAll(ingredientEntries);
    }


}
