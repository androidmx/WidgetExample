package com.widgetexample.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.widgetexample.widget.collection.CollectionApiWidgetProvider;
import com.widgetexample.Utils.GeneralUtils;

/**
 * Created by ajea on 23/06/17.
 */

public class BaseWidgetJob extends Job {

    public static int LOW = 0;
    public static int MID = 500;
    public static int HIGH = 1000;

    public static final int ERROR_CODE_NETWORK = 1000;
    public static final String ERROR_NETWORK = "Asegúrate que tu dispositivo este conectado a Internet";

    public static final int ERROR_CODE_CANCEL = 2000;
    public static final String ERROR_CANCEL = "Ocurrio un error mientras se trataba de consultar al servidor. Intentelo nuevamente";

    private final Executor executor;
    private final OnFailureError onFailureError;

    public interface Executor {
        void execute();
    }

    public interface OnFailureError {
        void onFailureErrorListener(int code, String message);
    }

    protected BaseWidgetJob(Executor executor, OnFailureError onFailureError, String tagName) {
        super(new Params(BaseWidgetJob.HIGH).requireNetwork().addTags(tagName));
        this.executor = executor;
        this.onFailureError = onFailureError;
    }

    protected BaseWidgetJob(Params params, Executor executor, OnFailureError onFailureError) {
        super(params);
        this.executor = executor;
        this.onFailureError = onFailureError;
    }

    @Override
    public void onAdded() {
        if (!GeneralUtils.isConnected(CollectionApiWidgetProvider.getContext())) {
            onFailureError.onFailureErrorListener(ERROR_CODE_NETWORK, ERROR_NETWORK);
        }
    }

    @Override
    public void onRun() throws Throwable {
        executor.execute();
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        onFailureError.onFailureErrorListener(ERROR_CODE_CANCEL, ERROR_CANCEL);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
