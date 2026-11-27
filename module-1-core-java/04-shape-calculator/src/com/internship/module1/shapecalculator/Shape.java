
package com.internship.module1.shapecalculator;

public abstract class Shape {

    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    public abstract double area();

    public abstract double perimeter();

    public void display() {

        System.out.println(
                name +
                        " | Area: " + String.format("%.2f", area()) +
                        " | Perimeter: " + String.format("%.2f", perimeter())
        );
    }
}
