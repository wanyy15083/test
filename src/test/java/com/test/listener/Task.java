package com.test.listener;

/**
 * Created by songyigui on 2018/1/11.
 */
public class Task {
    private String id;
    private String msg;
    private State state;

    public Task(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public Task(String id, String msg, State state) {
        this.id = id;
        this.msg = msg;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public enum State {
        DOING,
        SUCCESS,
        FAIL;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", state=" + state +
                '}';
    }
}
