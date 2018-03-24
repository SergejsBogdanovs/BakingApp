package lv.st.sbogdano.bakingapp.ui.widget.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.data.database.entries.IngredientEntry;
import lv.st.sbogdano.bakingapp.ui.widget.RecipeWidgetManager;

import static android.content.ContentValues.TAG;

public class RecipeWidgetRemoteViewService extends RemoteViewsService{

    private RecipeWidgetManager mRecipeWidgetManager = new RecipeWidgetManager();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            private List<IngredientEntry> mIngredientEntries = null;

            @Override
            public void onCreate() {
                mIngredientEntries = new ArrayList<>();
                mIngredientEntries.addAll(mRecipeWidgetManager.getIngredients());
            }

            @Override
            public void onDataSetChanged() {
                mIngredientEntries.clear();
                mIngredientEntries.addAll(mRecipeWidgetManager.getIngredients());
                Log.v(TAG, "onDataSetChanged: " + mIngredientEntries.size());
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return mIngredientEntries.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.recipe_widget_item);
                IngredientEntry ingredientEntry = mIngredientEntries.get(position);
                Log.v(TAG, "getViewAt: " + ingredientEntry.getIngredient());
                remoteViews.setTextViewText(R.id.recipe_ingredient_name, ingredientEntry.getIngredient());
                remoteViews.setTextViewText(R.id.recipe_ingredient_quantity,
                        getString(R.string.ingredient_quantity_text,
                                String.valueOf(ingredientEntry.getQuantity()),
                                ingredientEntry.getMeasure()));
                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return (long) position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
