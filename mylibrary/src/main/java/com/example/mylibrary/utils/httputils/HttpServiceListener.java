package com.example.mylibrary.utils.httputils;

/**
 * 网络请求监听
 * Created by 黑猫 on 2017/12/21.
 */

public interface HttpServiceListener {

    void onSucceed(String result);

    void onFailure();

}
