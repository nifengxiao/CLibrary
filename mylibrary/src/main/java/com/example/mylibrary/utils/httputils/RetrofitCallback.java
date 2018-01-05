package com.example.mylibrary.utils.httputils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 用于进度回调
 * Created by 黑猫 on 2017/12/29.
 */

public abstract class RetrofitCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(call, response);
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    public abstract void onSuccess(Call<T> call, Response<T> response);

    //用于进度的回调
    public abstract void onLoading(long total, long progress);
}