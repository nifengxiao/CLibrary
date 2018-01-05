package com.example.mylibrary.utils.httputils;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 基本网络请求
 * Created by 黑猫 on 2017/12/21.
 */

public interface HttpService {

    /**
     * get
     *
     * @param url
     * @param params
     * @return
     */
    @GET
    Call<String> get(@Url String url,
                     @QueryMap Map<String, String> params);

    /**
     * post
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url,
                      @FieldMap Map<String, String> params);

    /**
     * put
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url,
                     @FieldMap Map<String, String> params);


    /**
     * delete
     *
     * @param url
     * @param body
     * @return
     */
    @HTTP(method = "DELETE", hasBody = true)
    Call<String> delete(@Url String url,
                        @Body RequestBody body);

    /**
     * 上传图片到腾讯云
     *
     * @param url
     * @param headMap
     * @param op
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<String> postTencentFile(@Url String url,
                                 @HeaderMap Map<String, String> headMap,
                                 @Part("op") String op,
                                 @Part MultipartBody.Part file
    );

    /**
     * 植物查询系统上传图片数组
     * @param url
     * @param params
     * @return
     */
    @Multipart
    @POST
    Call<String> postFiles(@Url String url,
                           @PartMap Map<String, String> params,
                           @Part() List<MultipartBody.Part> shapeFileGroup
    );


}
