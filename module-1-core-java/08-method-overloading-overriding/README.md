# Assignment 8 - Method Overloading and Overriding

## What it does
A menu app that shows both kinds of polymorphism with live examples, and a side-by-side screen that shows the difference.

## Concepts
- Overloading = compile-time polymorphism
- Overriding = runtime polymorphism
- Parent and child classes (Animal, Dog, Cat)
- Parent reference holding a child object

## Files
    src/polymorphism/Calculator.java      -> 4 versions of add()
    src/polymorphism/Animal.java          -> parent
    src/polymorphism/Dog.java, Cat.java   -> children that override
    src/polymorphism/Printer.java         -> show(Animal) and show(Dog)
    src/polymorphism/PolymorphismApp.java -> the menu

## Run
    cd 08-method-overloading-overriding
    javac -d out src\polymorphism\*.java
    java -cp out polymorphism.PolymorphismApp

## Overloading vs Overriding
    Overloading: same name, different parameters, same class.
                 The compiler decides, by looking at the arguments.
    Overriding:  same name, same parameters, child replaces parent.
                 The JVM decides, by looking at the real object.

## Sample test
    Type: 1, 1, 10, 20
    Result: Java called: add(int, int)   Result: 30

    Type: 1, 4, Peak, ProSys
    Result: Java called: add(String, String)   Result: PeakProSys

    Type: 2, 1, Bruno
    Result: Bruno (Dog) says: Woof!

    Type: 3
    Result: show(a) picks show(Animal), but a.makeSound() still gives Woof!

(In the menu, type the main option first, then the sub option, then the values. Type 0 to go back or exit.)

## What happens inside
1. Menu 1 calls add() with different argument types. Java picks the matching version before the program runs.
2. Menu 2 creates Animal, Dog or Cat but stores it in an Animal variable. The call makeSound() runs the version of the real object, so a Dog says Woof.
3. Menu 3 proves the difference. With Animal a = new Dog(), the overloaded show(a) uses the variable type (Animal), but the overridden makeSound() uses the object type (Dog).