package com.example.rxjava_rxandroid_examples.network;

import com.example.rxjava_rxandroid_examples.Task;
import com.example.rxjava_rxandroid_examples.models.Comment;
import com.example.rxjava_rxandroid_examples.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TaskApi {
    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Observable<List<Comment>> getPostComment(
            @Path("id")int id
    );
    @GET("posts/{id}")
    Observable<Post> getPost(
            @Path("id") int id
    );

}
