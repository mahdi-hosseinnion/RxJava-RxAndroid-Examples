package com.example.rxjava_rxandroid_examples;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.SearchView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //ui
    SearchView searchView;

    //vars

    private CompositeDisposable disposable = new CompositeDisposable();
    long timeSinceLastQuery=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        searchView = findViewById(R.id.search_view);
        timeSinceLastQuery=System.currentTimeMillis();
        Observable<Unit> observableQueryText=RxView.clicks(findViewById(R.id.btn))
                .throttleFirst(1,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
        observableQueryText.subscribe(new Observer<Unit>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(Unit s) {
                Log.d(TAG, "onNext: it has been "+(System.currentTimeMillis()-timeSinceLastQuery)+" milisecond since last runned");
//                Log.d(TAG, "onNext: query = "+s);
                timeSinceLastQuery=System.currentTimeMillis();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
