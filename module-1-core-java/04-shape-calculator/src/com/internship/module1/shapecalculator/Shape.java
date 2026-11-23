package com.internship.module1.shapecalculator;

// Parent class. Every shape has a name, an area and a perimeter.
// The child classes override area() and perimeter() with their own formulas.
public class Shape {

    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double area() {
        return 0;
    }

    public double perimeter() {
        return 0;
    }

    public void display() {
        System.out.printf("%s -> Area: %.2f, Perimeter: %.2f%n", name, area(), perimeter());
    }
}