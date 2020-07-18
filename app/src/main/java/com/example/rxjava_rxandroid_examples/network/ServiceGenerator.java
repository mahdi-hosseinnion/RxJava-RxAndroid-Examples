package com.example.rxjava_rxandroid_examples.network;

import com.example.rxjava_rxandroid_examples.util.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder=new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            ;
    private static Retrofit retrofit=retrofitBuilder.build();

    private static TaskApi taskApi=retrofit.create(TaskApi.class);

    public static TaskApi getTaskApi() {
        return taskApi;
    }
}
