package com.internship.module1.shapecalculator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // Polymorphism: this list holds Circle, Rectangle, Square and Triangle
    // objects, but the type is just Shape.
    static ArrayList<Shape> shapes = new ArrayList<>();

    public static void main(String[] args) {
        int choice = 0;

        while (choice != 6) {
            System.out.println();
            System.out.println("===== Shape Calculator =====");
            System.out.println("1. Circle");
            System.out.println("2. Rectangle");
            System.out.println("3. Square");
            System.out.println("4. Triangle");
            System.out.println("5. Show all shapes");
            System.out.println("6. Exit");
            choice = (int) readNumber("Enter choice: ");

            try {
                if (choice == 1) {
                    double r = readNumber("Enter radius: ");
                    addShape(new Circle(r));
                } else if (choice == 2) {
                    double l = readNumber("Enter length: ");
                    double w = readNumber("Enter width: ");
                    addShape(new Rectangle(l, w));
                } else if (choice == 3) {
                    double s = readNumber("Enter side: ");
                    addShape(new Square(s));
                } else if (choice == 4) {
                    double a = readNumber("Enter side a: ");
                    double b = readNumber("Enter side b: ");
                    double c = readNumber("Enter side c: ");
                    addShape(new Triangle(a, b, c));
                } else if (choice == 5) {
                    showAll();
                } else if (choice == 6) {
                    System.out.println("Thank you.");
                } else {
                    System.out.println("Invalid choice, enter 1 to 6.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void addShape(Shape shape) {
        shapes.add(shape);
        shape.display();
    }

    // The same call shape.display() runs a different area() and perimeter()
    // depending on the real object. That is polymorphism.
    static void showAll() {
        if (shapes.isEmpty()) {
            System.out.println("No shapes added yet.");
            return;
        }
        double totalArea = 0;
        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            System.out.print((i + 1) + ". ");
            shape.display();
            totalArea = totalArea + shape.area();
        }
        System.out.printf("Total area of all shapes: %.2f%n", totalArea);
    }

    // keeps asking until the user types a valid number
    static double readNumber(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}