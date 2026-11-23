# 04 - Shape Calculator

Assignment 4 of the Java Full Stack Internship at PeakProSys Solutions, Pune.

## What is developed

A console program where the user picks a shape, enters its sizes and gets the area and perimeter.

- Four shapes: Circle, Rectangle, Square and Triangle
- Option 5 shows every shape added so far, with the total area
- Wrong input is handled: text instead of a number, zero or negative sizes, and triangle sides that cannot form a triangle

## Overriding and polymorphism

`Shape` is the parent class with `area()` and `perimeter()`. Each shape extends it and overrides both methods with its own formula (using `Math.PI`, `Math.pow` and `Math.sqrt`).

All shapes are stored in one `ArrayList<Shape>`. When `display()` is called on each item, Java runs the area and perimeter of the actual shape. That is polymorphism.

```
              Shape
   area() | perimeter() | display()
                |
    +-----------+-----------+-----------+
    |           |           |           |
 Circle     Rectangle     Square     Triangle
```

## Files

Package: `com.internship.module1.shapecalculator`

- `Shape.java` - parent class
- `Circle.java`, `Rectangle.java`, `Square.java`, `Triangle.java` - child classes
- `Main.java` - menu and input

## How to run

From this folder:

```
javac -d out src/com/internship/module1/shapecalculator/*.java
java -cp out com.internship.module1.shapecalculator.Main
```

Or open the project in IntelliJ and run `Main`.

## Example

```
Enter choice: 1
Enter radius: 5
Circle -> Area: 78.54, Perimeter: 31.42
```
## Screenshots

### Output

**Circle**

Choice 1 with radius 5. The program prints the area and perimeter of the circle.

![Circle](<screenshots/circle.png>)

**Rectangle**

Choice 2 with length 4 and width 6. Area is 24.00 and perimeter is 20.00.

![Rectangle](<screenshots/rectangle.png>)

**Square**

Choice 3 with side 5. Area is 25.00 and perimeter is 20.00.

![Square](<screenshots/square.png>)

**Triangle**

Choice 4 with sides 3, 4 and 5. Area comes from Heron's formula and is 6.00.

![Triangle](<screenshots/triangle.png>)

**Show all shapes**

Choice 5 lists every shape added so far and prints the total area. The loop calls the same method on each shape, but each one gives its own result.

![Show all shapes](<screenshots/show all.png>)

**Invalid input**

Text instead of a number is rejected and asked again, a zero or negative radius gives an error, and sides like 1, 2, 10 are rejected because they cannot form a triangle.

![Invalid input](<screenshots/errors.png>)
