package com.example.mylibrary.utils.httputils;

import android.content.Context;


import com.example.mylibrary.utils.LogUtils;
import com.example.mylibrary.utils.Urls;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络请求
 * Created by 黑猫 on 2017/12/21.
 */

public class HttpServiceHelper {
    private HttpService httpService;
    private Retrofit retrofit;

    private HttpServiceHelper() {
        //Cookie持久化
        CookieManager cookieManager = new CookieManager();

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(150, TimeUnit.SECONDS)
                .cookieJar(new JavaNetCookieJar(cookieManager));

        OkHttpClient build = client.build();
        this.retrofit = new Retrofit.Builder().baseUrl(Urls.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(build)
                .build();
        this.httpService = retrofit.create(HttpService.class);
    }

    private static class HttpServiceHolder {
        private static HttpServiceHelper instance = new HttpServiceHelper();
    }

    public static HttpServiceHelper getInstance() {
        return HttpServiceHolder.instance;
    }

    public void httpGet(String url, Map<String, String> map, final HttpServiceListener listener) {
        Call<String> call = httpService.get(url, map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    LogUtils.i("http_result", "succeed--->" + response.body());
                    LogUtils.i("response", "succeed--->" + response.toString());
                    listener.onSucceed(response.body());
                } else {
                    try {
                        LogUtils.i("error", "succeed--->" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtils.i("result", "failure" + t.toString());
                listener.onFailure();
            }
        });
    }

    public void httpGet(String url, final HttpServiceListener listener) {
        Map<String, String> map = new HashMap<>();
        httpGet(url, map, listener);
    }


    public void httpPost(String url, Map<String, String> map, final HttpServiceListener listener) {
        Call<String> call = httpService.post(url, map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    LogUtils.i("http_result", "succeed--->" + response.body());
                    LogUtils.i("response", "succeed--->" + response.toString());
                    listener.onSucceed(response.body());
                } else {
                    try {
                        LogUtils.i("error", "succeed--->" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtils.i("result", "failure" + t.toString());
                listener.onFailure();
            }
        });
    }

    public void httpPut(String url, Map<String, String> map, final HttpServiceListener listener) {
        Call<String> call = httpService.put(url, map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    LogUtils.i("http_result", "succeed--->" + response.body());
                    LogUtils.i("response", "succeed--->" + response.toString());
                    listener.onSucceed(response.body());
                } else {
                    try {
                        LogUtils.i("error", "succeed--->" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtils.i("result", "failure--->" + t.toString());
                listener.onFailure();
            }
        });
    }

    public void httpDelete(String url, Map<String, String> map, final HttpServiceListener listener) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, map.get(key));
        }
        RequestBody requestBody = builder.build();
        Call<String> call = httpService.delete(url, requestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    LogUtils.i("http_result", "succeed--->" + response.body());
                    LogUtils.i("response", "succeed--->" + response.toString());
                    listener.onSucceed(response.body());
                } else {
                    try {
                        LogUtils.i("error", "succeed--->" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtils.i("http_result", "failure" + t.toString());
                listener.onFailure();
            }
        });
    }

    /**
     * 腾讯云文件上传
     *
     * @param url
     * @param partName
     * @param file
     * @param sign
     * @param listener
     * @param progressListener
     */
    public void httpPostTencentFile(String url, String partName, File file, String sign, final HttpServiceListener listener, final ProgressListener progressListener) {
        Map<String, String> headMap = new HashMap<>();
        //headMap 不可重复 会起冲突
        headMap.put("Authorization", sign);
        //带有进度的回调
        RetrofitCallback<String> retrofitCallback = new RetrofitCallback<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    LogUtils.i("http_result", "succeed--->" + response.body());
                    LogUtils.i("response", "succeed--->" + response.toString());
                    listener.onSucceed(response.body());
                } else {
                    try {
                        LogUtils.i("error", "succeed--->" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLoading(long total, long progress) {
                progressListener.onProgress(total, progress, 0);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtils.i("http_result", "failure" + t.toString());
                listener.onFailure();
            }
        };
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //将RequestBody转换成特定的FileRequestBody
        FileRequestBody body = new FileRequestBody(fileBody, retrofitCallback);
        MultipartBody.Part part =
                MultipartBody.Part.createFormData(partName, file.getName(), body);
        Call<String> call = httpService.postTencentFile(url, headMap, "upload", part);
        call.enqueue(retrofitCallback);
    }


    /**
     * 多文件上传
     *
     * @param url       绝对路径
     * @param fileMap   文件集合
     * @param context   上下文
     * @param paramsMap 参数集合
     * @param listener  成功失败回调
     */
    public void httpPostFiles(final String url, final Map<String, List<File>> fileMap, Context context, final Map<String, String> paramsMap, final HttpServiceListener listener, final ProgressListener progressListener) {
        DisposeImgAsyncTask disposeImgAsyncTask = new DisposeImgAsyncTask(context);
        disposeImgAsyncTask.execute(fileMap);
        disposeImgAsyncTask.setFinishListener(new DisposeImgAsyncTask.DataFinishListener() {
            @Override
            public void dataFinishSuccessfully(Map<String, List<File>> filesMap) {
                //上传个数
                final int[] count = {0};
                //带有进度的回调
                RetrofitCallback<String> retrofitCallback = new RetrofitCallback<String>() {
                    @Override
                    public void onSuccess(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            LogUtils.i("http_result", "succeed--->" + response.body());
                            LogUtils.i("response", "succeed--->" + response.toString());
                            listener.onSucceed(response.body());
                        } else {
                            try {
                                LogUtils.i("error", "succeed--->" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onLoading(long total, long progress) {
                        if (total == progress) {
                            count[0]++;
                        }
                        LogUtils.i("上传进度", "progress--->" + progress + "total--->" + total + "count--->" + count[0]);
                        progressListener.onProgress(total, progress, count[0]);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LogUtils.i("http_result", "failure" + t.toString());
                        listener.onFailure();
                    }
                };
                List<MultipartBody.Part> parts = new ArrayList<>();

                for (String s : filesMap.keySet()) {
                    for (int i = 0; i < filesMap.get(s).size(); i++) {
                        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), filesMap.get(s).get(i));
                        //将RequestBody转换成特定的FileRequestBody
                        FileRequestBody body = new FileRequestBody(fileBody, retrofitCallback);
                        MultipartBody.Part part =
                                MultipartBody.Part.createFormData(s, filesMap.get(s).get(i).getName(), body);
                        parts.add(part);
                    }
                }
                Call<String> call = httpService.postFiles(url, paramsMap, parts);
                call.enqueue(retrofitCallback);
            }
        });


    }
}
