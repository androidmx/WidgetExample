package com.widgetexample.net;

import com.widgetexample.entities.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ajea on 23/06/17.
 */

public class ExampleJob extends BaseWidgetJob {

    public ExampleJob (final GenericResponseInteractor genericResponseInteractor){
        super(new Executor() {
            @Override
            public void execute() {

                Call<List<UsersResponse>> response = RetrofitApi.getInstance().getUsers();
                response.enqueue(new Callback<List<UsersResponse>>() {

                    @Override
                    public void onResponse(Call<List<UsersResponse>> call, Response<List<UsersResponse>> response) {
                        genericResponseInteractor.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<UsersResponse>> call, Throwable t) {
                        genericResponseInteractor.onError(t.getMessage());
                    }

                });
            }
        }, new OnFailureError() {
            @Override
            public void onFailureErrorListener(int code, String message) {
                genericResponseInteractor.onError(message);
            }
        }, "Widget");

    }

}
