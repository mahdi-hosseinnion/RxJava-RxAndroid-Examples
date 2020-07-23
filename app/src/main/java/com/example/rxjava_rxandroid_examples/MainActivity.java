package com.example.rxjava_rxandroid_examples;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


import android.os.Bundle;
import android.util.Log;

import com.example.rxjava_rxandroid_examples.adapters.PostRecyclerAdapter;
import com.example.rxjava_rxandroid_examples.models.Comment;
import com.example.rxjava_rxandroid_examples.models.Post;
import com.example.rxjava_rxandroid_examples.network.ServiceGenerator;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //ui
    SearchView searchView;
    RecyclerView recyclerView;
    //vars
    PostRecyclerAdapter mRecyclerAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();
    long timeSinceLastQuery=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        initRecyclerView();
        getPostsObservable()
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<Post, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(Post post) throws Exception {
                        return getCommentObservable(post);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Post post) {
                        mRecyclerAdapter.updatePost(post);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    private Observable<Post> getPostsObservable(){
        return ServiceGenerator.getTaskApi()
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Post>, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(List<Post> posts) throws Exception {
                        mRecyclerAdapter.setRecipes(posts);
                        return Observable.fromIterable(posts)
                                .subscribeOn(Schedulers.io());
                    }
                });
    }
    private Observable<Post>getCommentObservable(final Post post){
        return ServiceGenerator.getTaskApi()
                .getPostComment(post.getId())//inja havaset bashe
                .map(new Function<List<Comment>, Post>() {
                    @Override
                    public Post apply(List<Comment> comments) throws Exception {
                        int deley=((new Random().nextInt(6))+1)*1000;
                        Thread.sleep(deley);
                        Log.d(TAG, "apply: sleeping thread "+Thread.currentThread().getName()+" for "+(deley/1000)+" seconds");
                        post.setComments(comments);
                        return post;
                    }
                })
                .subscribeOn(Schedulers.io());
    }
    private void initRecyclerView(){
        mRecyclerAdapter =new PostRecyclerAdapter();
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
