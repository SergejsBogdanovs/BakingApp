package lv.st.sbogdano.bakingapp.ui.widget;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import lv.st.sbogdano.bakingapp.AppDelegate;
import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.data.database.entries.RecipeEntry;

public class RecipeWidgetManager {

    private static final String KEY_RECIPE = "Recipe";
    private static final String KEY_INGREDIENTS = "Ingredients";
    private static final String PREFERENCES_NAME = "RecipeWidgetManager";

    private Gson mGson;
    private SharedPreferences mSharedPreferences;
    private Application mApplication;

    public RecipeWidgetManager() {
        mGson = new Gson();
        mApplication = AppDelegate.getsInstance();
        mSharedPreferences = mApplication.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void updateRecipeWidget(RecipeEntry recipeEntry, List<IngredientEntry> ingredientEntries) {

        String recipeJson = mGson.toJson(recipeEntry);
        String ingredientJson = mGson.toJson(ingredientEntries);

        mSharedPreferences.edit().putString(KEY_RECIPE, recipeJson).apply();
        mSharedPreferences.edit().putString(KEY_INGREDIENTS, ingredientJson).apply();

        updateWidget();
    }

    private void updateWidget() {

        Intent intent = new Intent(mApplication, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mApplication);
        ComponentName componentName = new ComponentName(mApplication, RecipeWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        mApplication.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
    }

    public RecipeEntry getRecipe() {
        String recipe = mSharedPreferences.getString(KEY_RECIPE, null);
        return mGson.fromJson(recipe, RecipeEntry.class);
    }

    public List<IngredientEntry> getIngredients() {
        String ingredients = mSharedPreferences.getString(KEY_INGREDIENTS, null);
        Type collectionType = new TypeToken<List<IngredientEntry>>(){}.getType();
        return mGson.fromJson(ingredients, collectionType);
    }
}
