package lv.st.sbogdano.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import lv.st.sbogdano.bakingapp.R;
import lv.st.sbogdano.bakingapp.ui.widget.service.RecipeWidgetRemoteViewService;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RecipeWidgetManager recipeWidgetManager) {

        if (recipeWidgetManager.getRecipe() != null) {
            String widgetTitle = recipeWidgetManager.getRecipe().getName();
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
            views.setTextViewText(R.id.widget_title, widgetTitle);
            views.setRemoteAdapter(R.id.widget_list,
                    new Intent(context, RecipeWidgetRemoteViewService.class));

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetManager recipeWidgetManager = new RecipeWidgetManager();
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeWidgetManager);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

