package com.example.rxjava_rxandroid_examples;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {
    @SerializedName("title")
    @Expose
    private String description;
    @SerializedName("completed")
    @Expose
    private boolean isComplete;
    @SerializedName("id")
    @Expose
    private int priority;

    public Task(String description, boolean isComplete, int priority) {
        this.description = description;
        this.isComplete = isComplete;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String  toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", isComplete=" + isComplete +
                ", priority=" + priority +
                '}';
    }
}
