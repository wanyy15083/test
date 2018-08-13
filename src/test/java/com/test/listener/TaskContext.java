package com.test.listener;

import java.util.*;

/**
 * Created by songyigui on 2018/1/11.
 */
public class TaskContext {
    private List<TaskListener> listeners = new ArrayList<>();
    private TaskEvent event = null;
    private Task task = null;

    public TaskContext(Task task) {
        this.task = task;
    }

    private void check() {
        if (task == null) {
            throw new IllegalStateException("task is null");
        }
    }

    public void addListener(TaskListener listener){
        listeners.add(listener);
    }

    public void start(){
        check();
        task.setState(Task.State.DOING);
        event = new TaskEvent(task, this);
        for (TaskListener listener : listeners) {
            listener.onStart(event);
        }
    }

    public void complete(){
        check();
        task.setState(Task.State.SUCCESS);
        event = new TaskEvent(task, this);
        for (TaskListener listener : listeners) {
            listener.onComplete(event);
        }
    }

    public void error(String error){
        check();
        task.setState(Task.State.FAIL);
        event = new TaskEvent(task, this, error);
        for (TaskListener listener : listeners) {
            listener.onError(event);
        }
    }
}
