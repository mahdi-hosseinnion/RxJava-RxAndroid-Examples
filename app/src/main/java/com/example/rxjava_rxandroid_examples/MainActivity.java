package com.example.rxjava_rxandroid_examples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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

import com.example.rxjava_rxandroid_examples.viewModels.TaskViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //ui
    TextView txt;

    //vars
    TaskViewModel taskViewModel;
    private CompositeDisposable disposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.txt);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        subscribeObservers();

    }
    private void subscribeObservers(){
        taskViewModel.makeReactiveQuery(1).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                Log.d(TAG, "onChanged: started");
                if (task!=null){
                    Log.d(TAG, "onChanged: task is not null: "+task.toString());
                    txt.setText(task.toString());
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
