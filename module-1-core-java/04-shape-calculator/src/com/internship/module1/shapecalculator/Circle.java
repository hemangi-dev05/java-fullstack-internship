package com.internship.module1.shapecalculator;

public class Circle extends Shape {

    private double radius;

    public Circle(double radius) {
        super("Circle");
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0");
        }
        this.radius = radius;
    }

    // area = PI * r * r
    @Override
    public double area() {
        return Math.PI * Math.pow(radius, 2);
    }

    // perimeter (circumference) = 2 * PI * r
    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}