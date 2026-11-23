package com.internship.module1.shapecalculator;

public class Square extends Shape {

    private double side;

    public Square(double side) {
        super("Square");
        if (side <= 0) {
            throw new IllegalArgumentException("Side must be greater than 0");
        }
        this.side = side;
    }

    // area = side * side
    @Override
    public double area() {
        return Math.pow(side, 2);
    }

    // perimeter = 4 * side
    @Override
    public double perimeter() {
        return 4 * side;
    }
}