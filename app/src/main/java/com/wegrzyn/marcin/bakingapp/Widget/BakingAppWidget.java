package com.wegrzyn.marcin.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.wegrzyn.marcin.bakingapp.MainActivity;
import com.wegrzyn.marcin.bakingapp.Model.Ingredient;
import com.wegrzyn.marcin.bakingapp.Model.Recipe;
import com.wegrzyn.marcin.bakingapp.R;
import java.util.List;

/**
 * Created by Marcin Węgrzyn on 03.05.2018.
 * wireamg@gmail.com
 */
public class BakingAppWidget extends AppWidgetProvider {

    private static final String TAG = BakingAppWidget.class.getSimpleName() ;

    public static final String ACTION_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    public static final String RECIPE_EXTRA = "recipe_extra";
    public static final String BUNDLE_EXTRA = "bundle_extra";

    private static Recipe recipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_title_tv, context.getString(R.string.select_recipe));

        if(recipe!=null) {
            views.setTextViewText(R.id.appwidget_title_tv, recipe.getName());
            views.setTextViewText(R.id.appwidget_ingredient_list_tv, showIngredients(recipe.getIngredients()));
        }
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_id_layout,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.hasExtra(RECIPE_EXTRA)){
            Bundle bundle = intent.getBundleExtra(RECIPE_EXTRA);
            recipe = bundle.getParcelable(BUNDLE_EXTRA);
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.baking_app_widget);
            views.setTextViewText(R.id.appwidget_title_tv,recipe.getName());
            views.setTextViewText(R.id.appwidget_ingredient_list_tv, showIngredients(recipe.getIngredients()));

            ComponentName appWidget = new ComponentName(context, BakingAppWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(appWidget, views);
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

    private static String showIngredients(List<Ingredient> ingredients) {
        StringBuilder out = new StringBuilder();
        for (Ingredient ing :ingredients) {
            out.append(ing.getIngredient())
                    .append(" ")
                    .append(String.valueOf(ing.getQuantity()))
                    .append(" ").append(ing.getMeasure())
                    .append("\n");
        }
        return out.toString();
    }

}

