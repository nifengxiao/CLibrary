package com.example.mylibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 解决Toast重复弹出
 * Created by 黑猫 on 2018/1/3.
 */

public class ToastUtils {
    private static String oldMsg;

    private static Toast toast = null;

    private static long oneTime = 0;

    private static long twoTime = 0;

    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
}
