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
import android.support.v4.app.INotificationSideChannel;
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

public class MainActivity extends AppCompatActivity {
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
        PublishSubject<Integer> source = PublishSubject.create();

        // It will get 1, 2, 3, 4 and onComplete
        source.subscribe(getFirstObserver());

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        // It will get 4 and onComplete for second observer also.
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();
    }


    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: .111. Subscribe .111................");
                disposable.add(d);
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: first one 11:- " + integer + " -:11");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: .111. OnComplete .111................");
            }
        };
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
                Log.d(TAG, "onSubscribe: .222. Subscribe .222...............");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: second one 22:- " + integer + " -:22");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: .222. OnComplete .222...............");
            }
        };
    }

    @Override
    protected void onPause() {
        disposable.clear();
        Log.d(TAG, "onPause: called");
        super.onPause();

    }

}
