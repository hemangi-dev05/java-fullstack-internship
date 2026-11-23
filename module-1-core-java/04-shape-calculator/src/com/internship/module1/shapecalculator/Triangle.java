package com.internship.module1.shapecalculator;

public class Triangle extends Shape {

    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        super("Triangle");
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Sides must be greater than 0");
        }
        // two sides together must be longer than the third side
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("These sides cannot form a triangle");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Heron's formula: s = (a + b + c) / 2, area = sqrt(s * (s-a) * (s-b) * (s-c))
    @Override
    public double area() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    // perimeter = a + b + c
    @Override
    public double perimeter() {
        return a + b + c;
    }
}