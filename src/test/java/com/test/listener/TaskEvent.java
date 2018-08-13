package com.test.listener;

/**
 * Created by songyigui on 2018/1/11.
 */
public class TaskEvent {
    private Task task;
    private TaskContext context;
    private String error;

    public TaskEvent(Task task, TaskContext context) {
        this.task = task;
        this.context = context;
    }

    public TaskEvent(Task task, TaskContext context, String error) {
        this.task = task;
        this.context = context;
        this.error = error;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskContext getContext() {
        return context;
    }

    public void setContext(TaskContext context) {
        this.context = context;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "TaskEvent{" +
                "task=" + task +
                ", context=" + context +
                ", error='" + error + '\'' +
                '}';
    }
}
