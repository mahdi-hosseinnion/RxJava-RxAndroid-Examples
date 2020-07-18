package com.example.rxjava_rxandroid_examples;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //ui
    TextView txt;

    //vars

    private CompositeDisposable disposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.txt);
        List<Task> list=new ArrayList<>();
        list.addAll(DataSource.createTasksList());
        Task[] array=new Task[]{new Task("",false,1),new Task("",false,1)};
        Observable<Task> taskObservable= Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: aLong="+task.getDescription());

                Log.d(TAG, "onNext: timer");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: color setted to red");
                txt.setTextColor(Color.RED);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
