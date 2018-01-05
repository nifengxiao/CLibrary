package com.example.mylibrary.utils.httputils;

/**
 * 进度监听 带有次数
 * Created by 黑猫 on 2017/12/29.
 */

public interface ProgressListener {
    void onProgress(long total, long progress, int count);
}
