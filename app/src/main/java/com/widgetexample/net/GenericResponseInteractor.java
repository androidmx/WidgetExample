package com.widgetexample.net;


import com.widgetexample.entities.UsersResponse;

import java.util.List;

public interface GenericResponseInteractor  {
    void onSuccess(List<UsersResponse> response);
    void onError(String error);
}
