package com.example.rxjava_rxandroid_examples.repositories;

import com.example.rxjava_rxandroid_examples.Task;
import com.example.rxjava_rxandroid_examples.network.ServiceGenerator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TaskRepository {
    private static TaskRepository instance;

    public static TaskRepository getInstance() {
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    }

    private TaskRepository() {
    }

    public LiveData<Task> makeReactiveQuery(final int id) {
        return LiveDataReactiveStreams.fromPublisher(ServiceGenerator
                .getTaskApi()
                .getTodo(id)
                .subscribeOn(Schedulers.io()));
    }
}
