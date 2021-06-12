package com.example.donelistapp.api;

import com.example.donelistapp.model.Value;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("masuk.php")
    Call<ResponseBody> login(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("daftar.php")
    Call<ResponseBody> register(@Field("nama") String nama,
                                @Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("tambahActivity.php")
    Call<ResponseBody> tambahActivity(@Field("id") String id,
                                      @Field("activity") String activity);

    @GET("activityList.php")
    Call<Value> activityList(@Query("nama") String nama);
}
