package com.galaxy.bean;

import lombok.Data;

/**
 * Created by wangwenjun on 2016/10/12.
 */

@Data
public class Apple {

    private String color;
    private long weight;

    public Apple() {
    }

    public Apple(String color, long weight) {
        this.color = color;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                '}';
    }
}
