package com.widgetexample.widget.collection;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.reflect.TypeToken;
import com.widgetexample.R;
import com.widgetexample.Utils.GeneralUtils;
import com.widgetexample.entities.UsersResponse;

import java.util.List;

/**
 * This is used to fill widget item list
 * **/
public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;
    private List<UsersResponse> users;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        users = GeneralUtils.deserialize(intent.getStringExtra("DATA"), new TypeToken<List<UsersResponse>>(){}.getType());
    }

    @Override
    public void onCreate() {

   }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return users != null ? users.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, users.get(i).getName());

        // Next, set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in CollectionApiWidgetProvider.
        Bundle extras = new Bundle();
        extras.putString(CollectionApiWidgetProvider.EXTRA_ITEM, users.get(i).getName());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
