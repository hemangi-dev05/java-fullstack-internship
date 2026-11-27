package com.internship.module1.shapecalculator;


public class Rectangle extends Shape {

    private double length;
    private double width;

    public Rectangle(double length, double width) {
        super("Rectangle");
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Length and width must be greater than 0");
        }
        this.length = length;
        this.width = width;
    }

    // area = length * width
    @Override
    public double area() {
        return length * width;
    }

    // perimeter = 2 * (length + width)
    @Override
    public double perimeter() {
        return 2 * (length + width);
    }
}