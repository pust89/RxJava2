package com.example.asoft.distinctdemo;

/**
 * Created by Pustovit Vladimir on 29.12.2019.
 * vovapust1989@gmail.com
 */

public class Man {
    private String name;
    private int age;

    public Man(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Man{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
