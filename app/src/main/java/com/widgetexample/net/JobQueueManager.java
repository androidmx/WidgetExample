package com.widgetexample.net;

import android.content.Context;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.TagConstraint;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.network.NetworkUtil;
import com.widgetexample.widget.collection.CollectionApiWidgetProvider;
import com.widgetexample.Utils.GeneralUtils;


public class JobQueueManager {

    private static JobManager jobManager;
    private static Context mContext;

    private static JobQueueManager INSTANCE = null;

    // Private constructor suppresses
    private JobQueueManager() {

    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JobQueueManager();
            initJobManager();
        }
    }

    private static void initJobManager() {
        Configuration configuration =
                new Configuration.Builder(mContext)
                        .minConsumerCount(1)              //always keep at least one consumer alive
                        .maxConsumerCount(3)              //up to 3 consumers at a time
                        .loadFactor(3)                    //3 jobs per consumer
                        .consumerKeepAlive(120)           //wait 2 minute
                        .networkUtil(new NetworkUtil() {
                            @Override
                            public int getNetworkStatus(Context context) {
                                return GeneralUtils.isConnected(context) ? NetworkUtil.METERED : NetworkUtil.DISCONNECTED;
                            }
                        })
                        .build();

        jobManager = new JobManager(configuration);
    }

    public static JobManager getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE.getJobManager();
    }

    private static JobManager getJobManager() {
        return jobManager;
    }

    public static void addJobInBackground(Job mJob, String tagName) {
        cancelALLJobsInBackground(tagName);
        getInstance().addJobInBackground(mJob);
    }

    public static void addJobInBackground(Context context, Job mJob, String tagName) {
        mContext = context;
        cancelALLJobsInBackground(tagName);
        getInstance().addJobInBackground(mJob);
    }

    public static void cancelALLJobsInBackground(String tagName) {
        getInstance().cancelJobsInBackground(null, TagConstraint.ANY, tagName);
    }
}
