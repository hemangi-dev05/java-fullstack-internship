# Assignment 14 - Calculator using Functional Interfaces and Lambda Expressions

## What it does
A menu calculator. Every operation is a lambda stored in a Map, so there is no big switch. Option 4 runs sample calculations, including error cases.

## Operations
    Two numbers : +  -  *  /  %  ^
    One number  : sqrt, square

## Concepts
- Custom functional interface (Operation) with @FunctionalInterface
- Lambda expressions and method references (Math::pow, System.out::println)
- Built-in functional interface UnaryOperator<Double>
- Map<String, Operation> instead of a switch statement

## Files
    src/calculator/Operation.java      -> the functional interface
    src/calculator/Calculator.java     -> lambdas, history, error checks
    src/calculator/CalculatorApp.java  -> the menu

## Run
    cd 14-calculator-lambda
    javac -d out src\calculator\*.java
    java -cp out calculator.CalculatorApp

## Sample test
    Type: 1, 10, +, 5
    Result: 10 + 5 = 15

    Type: 1, 5, /, 0
    Result: Error: Cannot divide by zero.

    Type: 2, sqrt, 16
    Result: sqrt(16) = 4

    Type: 4
    Result: 14 lines, 11 results and 3 errors

## What happens inside
1. The constructor puts one lambda per operator into a Map.
2. calculate() looks up the lambda by symbol and calls apply().
3. Divide by zero, negative square root and unknown symbols throw clear errors.
4. Infinity and NaN results are rejected.
5. Every successful calculation is added to the history list.