package com.widgetexample.widget.collection;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.widgetexample.MainActivity;
import com.widgetexample.R;
import com.widgetexample.Utils.GeneralUtils;
import com.widgetexample.entities.UsersResponse;

import java.util.List;


/**
 * Created by ajea on 15/06/17.
 */

public class CollectionApiWidgetProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.EXTRA_ITEM";
    public static Context context;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetService.startWidgetService(context);
    }


    public static void updateApiWidgets(Context context, AppWidgetManager appWidgetManager, List<UsersResponse> data, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(),
                    R.layout.collection_widget);

            //Click event handler for the title, launches the app when the user clicks on title
            Intent titleIntent = new Intent(context, MainActivity.class);
            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
            views.setOnClickPendingIntent(R.id.widgetTitleLabel, titlePendingIntent);

            //Click event handler for refresh view, upload data when the user clicks on refresh
            Intent bintent = new Intent(context, CollectionApiWidgetProvider.class);
            bintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            bintent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, bintent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.widgetRefresh, pendingIntent);


            Intent intent = new Intent(context, MyWidgetRemoteViewsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.putExtra("DATA", GeneralUtils.serialize(data));
            views.setRemoteAdapter(R.id.widgetListView, intent);

            ComponentName cn = new ComponentName(context, CollectionApiWidgetProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(cn), R.id.widgetListView);

            // This section makes it possible for items to have individualized behavior.
            // It does this by setting up a pending intent template. Individuals items of a collection
            // cannot set up their own pending intents. Instead, the collection as a whole sets
            // up a pending intent template, and the individual items set a fillInIntent
            // to create unique behavior on an item-by-item basis.
            Intent toastIntent = new Intent(context, CollectionApiWidgetProvider.class);
            // Set the action for the intent.
            // When the user touches a particular view, it will have the effect of
            // broadcasting TOAST_ACTION.
            toastIntent.setAction(CollectionApiWidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widgetListView, toastPendingIntent);


            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (intent.getAction().equals(TOAST_ACTION)) {
            String viewIndex = intent.getStringExtra(EXTRA_ITEM);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }

        super.onReceive(context, intent);
    }

    public static Context getContext() {
        return context;
    }
}
