package com.test.entity;

import java.util.Set;

public class Student {
    private String name;

    private int age;

    private String sex;

    private String describe;

    private Set books;

    public Student() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Set getBooks() {
        return books;
    }

    public void setBooks(Set books) {
        this.books = books;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
