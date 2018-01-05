package com.example.mylibrary.utils.pictureUtils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.utils.ThreadManager;

/**
 * 图片加载
 * Created by 黑猫 on 2018/1/2.
 */

public class ImageToLoadHelper {
    /**
     * 加载图片
     *
     * @param context
     * @param pictureUrl
     * @param imageView
     */
    public static void loadImage(Context context, String pictureUrl, final ImageView imageView) {
        Glide.with(context).load(pictureUrl)
                .into(imageView);
    }

    /**
     * 清理磁盘缓存
     */
    public static void clearDiskCache(final Context context) {
        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
            }
        });
    }

    /**
     * 清理内存缓存
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }

}
