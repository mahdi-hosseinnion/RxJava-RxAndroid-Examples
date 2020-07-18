package com.example.rxjava_rxandroid_examples.network;

import com.example.rxjava_rxandroid_examples.Task;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TaskApi {
    @GET("todos/{id}")
    Flowable<Task> getTodo(
            @Path("id") int id
    );
}
