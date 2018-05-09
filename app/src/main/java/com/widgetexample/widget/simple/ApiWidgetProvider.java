package com.widgetexample.widget.simple;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.widgetexample.R;
import com.widgetexample.widget.collection.WidgetService;


/**
 * Created by ajea on 15/06/17.
 */

public class ApiWidgetProvider extends AppWidgetProvider {

    public static Context context;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startWidgetService(context);
        loadData(context, appWidgetManager, appWidgetIds);
    }

    public static void loadData(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateAppWidget(context, appWidgetManager, appWidgetIds);
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.api_widget);

            remoteViews.setViewVisibility(R.id.progress, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.content, View.INVISIBLE);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }
    }

    public static void updateApiWidgets(Context context, AppWidgetManager appWidgetManager,
                                        String data, int[] appWidgetIds) {
        updateAppWidget(context, appWidgetManager, data, appWidgetIds);
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String data, int[] appWidgetIds) {

        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.api_widget);
            remoteViews.setTextViewText(R.id.text_content, data);
            remoteViews.setViewVisibility(R.id.progress, View.INVISIBLE);
            remoteViews.setViewVisibility(R.id.content, View.VISIBLE);

            Intent intent = new Intent(context, ApiWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setOnClickPendingIntent(R.id.bt_update, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        this.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
