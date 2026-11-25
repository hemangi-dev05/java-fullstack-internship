package calculator;

import java.util.List;
import java.util.Scanner;

public class CalculatorApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Calculator calc = new Calculator();

    public static void main(String[] args) {
        System.out.println("Welcome! Calculator using functional interfaces and lambda expressions.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-5): ");
            switch (choice) {
                case 1 -> twoNumbers();
                case 2 -> oneNumber();
                case 3 -> showHistory();
                case 4 -> runSamples();
                case 5 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 5.");
            }
            if (choice >= 1 && choice <= 5) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= CALCULATOR MENU =========");
        System.out.println("  1. Calculate with two numbers");
        System.out.println("  2. Calculate with one number");
        System.out.println("  3. Show history");
        System.out.println("  4. Run sample calculations");
        System.out.println("  5. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("===================================");
    }

    private static void twoNumbers() {
        double a = readDouble("First number: ");
        System.out.print("Operator (" + String.join(" ", calc.binarySymbols()) + "): ");
        String symbol = sc.nextLine().trim();
        double b = readDouble("Second number: ");
        System.out.println();
        runTwo(a, symbol, b);
    }

    private static void oneNumber() {
        System.out.print("Function (" + String.join(" ", calc.unaryNames()) + "): ");
        String name = sc.nextLine().trim().toLowerCase();
        double x = readDouble("Number: ");
        System.out.println();
        runOne(name, x);
    }

    private static void runTwo(double a, String symbol, double b) {
        String left = Calculator.format(a) + " " + symbol + " " + Calculator.format(b);
        try {
            System.out.println(left + " = " + Calculator.format(calc.calculate(a, symbol, b)));
        } catch (ArithmeticException | IllegalArgumentException e) {
            System.out.println(left + " -> Error: " + e.getMessage());
        }
    }

    private static void runOne(String name, double x) {
        String left = name + "(" + Calculator.format(x) + ")";
        try {
            System.out.println(left + " = " + Calculator.format(calc.calculateOne(name, x)));
        } catch (ArithmeticException | IllegalArgumentException e) {
            System.out.println(left + " -> Error: " + e.getMessage());
        }
    }

    private static void showHistory() {
        System.out.println();
        List<String> items = calc.getHistory();
        if (items.isEmpty()) {
            System.out.println("No calculations yet.");
        } else {
            System.out.println("HISTORY (" + items.size() + ")");
            items.forEach(System.out::println);   // method reference
        }
    }

    private static void runSamples() {
        System.out.println();
        runTwo(10, "+", 5);
        runTwo(10, "-", 4);
        runTwo(6, "*", 7);
        runTwo(20, "/", 4);
        runTwo(7, "/", 2);
        runTwo(10, "%", 3);
        runTwo(2, "^", 10);
        runTwo(5, "/", 0);
        runTwo(5, "%", 0);
        runTwo(0.1, "+", 0.2);
        runOne("sqrt", 16);
        runOne("sqrt", -4);
        runOne("square", 9);

        // a lambda can also be written right here, no changes to Calculator needed
        Operation sumOfSquares = (x, y) -> x * x + y * y;
        System.out.println("custom lambda x*x + y*y with 3 and 4 = "
                + Calculator.format(sumOfSquares.apply(3, 4)));
    }

    private static void explain() {
        System.out.println();
        System.out.println("FUNCTIONAL INTERFACE");
        System.out.println("  Operation has ONE abstract method: double apply(double a, double b)");
        System.out.println("  @FunctionalInterface makes the compiler check that rule");
        System.out.println();
        System.out.println("LAMBDA EXPRESSION");
        System.out.println("  (a, b) -> a + b     parameters -> body, it is the code of apply()");
        System.out.println();
        System.out.println("METHOD REFERENCE");
        System.out.println("  Math::pow           short form of (a, b) -> Math.pow(a, b)");
        System.out.println();
        System.out.println("BUILT-IN INTERFACE");
        System.out.println("  UnaryOperator<Double> takes one Double and returns a Double");
        System.out.println();
        System.out.println("MAP OF LAMBDAS");
        System.out.println("  Map<String, Operation> links a symbol to its lambda, so no switch is needed");
        System.out.println("  Adding a new operator is one new put() line");
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a whole number. Try again (example: 1).");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(sc.nextLine().trim());
                if (!Double.isNaN(value) && !Double.isInfinite(value)) {
                    return value;
                }
                System.out.println("  Please enter a normal number (example: 12.5).");
            } catch (NumberFormatException e) {
                System.out.println("  That is not a number. Try again (example: 12.5).");
            }
        }
    }
}