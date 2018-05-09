package com.widgetexample.widget.collection;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.widgetexample.entities.UsersResponse;
import com.widgetexample.net.ExampleJob;
import com.widgetexample.net.GenericResponseInteractor;
import com.widgetexample.net.JobQueueManager;

import java.util.List;

/**
 * Created by ajea on 22/06/17.
 */

public class WidgetService extends Service {

    public static void startWidgetService(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        JobQueueManager.addJobInBackground(this, new ExampleJob(new GenericResponseInteractor() {
            @Override
            public void onSuccess(List<UsersResponse> response) {
                //Update widgets
                updateWidget(response);
            }

            @Override
            public void onError(String error) {
                //Update widgets
                updateWidget(null);
            }

            private void updateWidget(List<UsersResponse> msg) {
                if (msg != null){
                    String name = msg.get(0).getName();
                    msg.get(0).setName(name);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetService.this);
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(WidgetService.this, CollectionApiWidgetProvider.class));

                    CollectionApiWidgetProvider.updateApiWidgets(WidgetService.this, appWidgetManager, msg, appWidgetIds);
                }

                stopSelf();
            }

        }), "widget");

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
