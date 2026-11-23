# 04 - Shape Calculator

Assignment 4 of the Java Full Stack Internship at PeakProSys Solutions, Pune.

A console program that finds the area and perimeter of a circle, rectangle, square and triangle. It is built to show method overriding and polymorphism.

## How it works

- `Shape` is the parent class. It has `area()`, `perimeter()` and `display()`.
- `Circle`, `Rectangle`, `Square` and `Triangle` extend `Shape` and override `area()` and `perimeter()` with their own formulas.
- In `Main`, all shapes are stored in one `ArrayList<Shape>`. When `display()` is called on each one, Java runs the area and perimeter of the actual shape. That is polymorphism.

## Formulas

| Shape | Area | Perimeter |
| --- | --- | --- |
| Circle | `Math.PI * Math.pow(r, 2)` | `2 * Math.PI * r` |
| Rectangle | `length * width` | `2 * (length + width)` |
| Square | `Math.pow(side, 2)` | `4 * side` |
| Triangle | Heron's formula with `Math.sqrt` | `a + b + c` |

## Checks

- Sizes must be greater than 0
- Triangle sides must be able to form a triangle (a + b > c and so on)
- Text instead of a number asks again instead of crashing

## Code

Package: `com.internship.module1.shapecalculator`

- `Shape.java` - parent class
- `Circle.java`
- `Rectangle.java`
- `Square.java`
- `Triangle.java`
- `Main.java` - menu and input

## How to run

From this folder:

```
javac -d out src/com/internship/module1/shapecalculator/*.java
java -cp out com.internship.module1.shapecalculator.Main
```

Or open the project in IntelliJ and run `Main`.

## Screenshots

Screenshots are in the `screenshots` folder.