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
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.rxjava_rxandroid_examples.adapters.OnPostClickListener;
import com.example.rxjava_rxandroid_examples.adapters.PostRecyclerAdapter;
import com.example.rxjava_rxandroid_examples.models.Comment;
import com.example.rxjava_rxandroid_examples.models.Post;
import com.example.rxjava_rxandroid_examples.network.ServiceGenerator;

import java.time.Period;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements OnPostClickListener {
    private static final String TAG = "MainActivity";
    //ui
    ProgressBar prb_main;
    RecyclerView recyclerView;

    //vars
    private PublishSubject<Post> publishSubject = PublishSubject.create();//for selecting post
    PostRecyclerAdapter mRecyclerAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();
    long timeSinceLastQuery = 0;
    public static final int PERIOD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        prb_main = findViewById(R.id.prb_main);
        initRecyclerView();
        retrievePosts();

    }

    private void initSwitchMapDemo() {
        publishSubject

                //apply switchmap operator so only one Observable can be used at a time.
                //it clear the previous one
                .switchMap(new Function<Post, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(Post post) throws Exception {
                        return Observable
                                // simulate slow network speed with interval + takeWhile + filter operators
                                .interval(PERIOD, TimeUnit.MILLISECONDS)
                                .subscribeOn(Schedulers.io())
                                .takeWhile(new Predicate<Long>() {
                                    @Override
                                    public boolean test(Long aLong) throws Exception {
                                        Log.d(TAG, "test: "+Thread.currentThread().getName()+", #"+aLong);
                                        prb_main.setMax(3000- PERIOD);
                                        prb_main.setProgress(Integer.parseInt(String.valueOf((aLong*PERIOD)+PERIOD)));
                                        return aLong<=(3000/PERIOD);
                                    }
                                })
                                .filter(new Predicate<Long>() {
                                    @Override
                                    public boolean test(Long aLong) throws Exception {

                                        return aLong>=(3000/PERIOD);
                                    }
                                })
                                .flatMap(new Function<Long, ObservableSource<Post>>() {
                                    @Override
                                    public ObservableSource<Post> apply(Long aLong) throws Exception {
                                        return ServiceGenerator.getTaskApi()
                                                .getPost(post.getId());
                                    }
                                })
                                ;
                    }
                })
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Post post) {
                        Log.d(TAG, "onNext: done.");
                        navViewPostActivity(post);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ",e );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void retrievePosts() {
            ServiceGenerator.getTaskApi()
                    .getPosts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Post>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(List<Post> posts) {
                            mRecyclerAdapter.setRecipes(posts);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "onError: ",e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
    }

    private void navViewPostActivity(Post post){
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        intent.putExtra("post",post);
        startActivity(intent);
    }
    private void initRecyclerView() {
        mRecyclerAdapter = new PostRecyclerAdapter(this);
        recyclerView.setAdapter(mRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        prb_main.setProgress(0);
        initSwitchMapDemo();
    }

    @Override
    protected void onPause() {
        disposable.clear();
        Log.d(TAG, "onPause: called");
        super.onPause();

    }

    @Override
    public void onPostClick(int position) {
        Log.d(TAG, "onPostClick: clicked #"+position);
        //submit the selected post object to be queried
        publishSubject.onNext(mRecyclerAdapter.getPosts().get(position));
    }
}
