package com.example.mylibrary.utils.httputils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;


import com.example.mylibrary.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

/**
 * 异步 用于图片处理(多图)
 * Created by 黑猫 on 2017/10/17.
 * AsyncTask 参数说明 “启动任务执行的输入参数”、“后台任务执行的进度”、“后台计算结果的类型”
 */

public class DisposeImgAsyncTask extends AsyncTask<Map<String, List<File>>, Integer, Map<String, List<File>>> {
    private DataFinishListener dataFinishListener;
    private Context context;

    public DisposeImgAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Map<String, List<File>> doInBackground(Map<String, List<File>>[] maps) {
        Map<String, List<File>> mapResult = new HashMap<>();
        for (String s : maps[0].keySet()) {
            List<File> files = new ArrayList<>();
            Compressor compressor = new Compressor(context);
            for (int i = 0; i < maps[0].get(s).size(); i++) {
                File comFile = null;
                try {
                    comFile = compressor.setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.PNG)
                            .compressToFile(maps[0].get(s).get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LogUtils.i("文件大小", comFile.length() + "");
                files.add(comFile);
            }
            mapResult.put(s, files);
        }
        return mapResult;
    }

    //onPreExecute方法用于在执行后台任务前做一些UI操作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
    @Override
    protected void onPostExecute(Map<String, List<File>> maps) {
        super.onPostExecute(maps);
        if (maps != null) {
            dataFinishListener.dataFinishSuccessfully(maps);
        }
    }

    //onCancelled方法用于在取消执行中的任务时更改UI
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    //结果回调
    public void setFinishListener(DataFinishListener dataFinishListener) {
        this.dataFinishListener = dataFinishListener;
    }

    //onProgressUpdate方法用于更新进度信息
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public interface DataFinishListener {
        void dataFinishSuccessfully(Map<String, List<File>> maps);
    }

}
