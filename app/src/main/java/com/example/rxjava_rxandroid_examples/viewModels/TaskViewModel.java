package com.example.rxjava_rxandroid_examples.viewModels;

import com.example.rxjava_rxandroid_examples.Task;
import com.example.rxjava_rxandroid_examples.repositories.TaskRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


public class TaskViewModel extends ViewModel {
    private static TaskViewModel instance;
    private TaskRepository taskRepository;
    private static TaskViewModel getInstance(){
        if (instance==null){
            instance=new TaskViewModel();
        }
        return instance;
    }
    public TaskViewModel() {
        taskRepository=TaskRepository.getInstance();
    }
    public LiveData<Task>makeReactiveQuery(int id){
        return taskRepository.makeReactiveQuery(id);
    }
}
