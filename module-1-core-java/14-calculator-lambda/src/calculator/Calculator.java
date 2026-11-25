package calculator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

public class Calculator {

    // each operator symbol is linked to a lambda, so no big switch is needed
    private final Map<String, Operation> binaryOps = new LinkedHashMap<>();

    // UnaryOperator<Double> is a ready-made functional interface from java.util.function
    private final Map<String, UnaryOperator<Double>> unaryOps = new LinkedHashMap<>();

    private final List<String> history = new ArrayList<>();

    public Calculator() {
        binaryOps.put("+", (a, b) -> a + b);
        binaryOps.put("-", (a, b) -> a - b);
        binaryOps.put("*", (a, b) -> a * b);
        binaryOps.put("/", (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Cannot divide by zero.");
            }
            return a / b;
        });
        binaryOps.put("%", (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Cannot take remainder by zero.");
            }
            return a % b;
        });
        binaryOps.put("^", Math::pow);   // method reference, same as (a, b) -> Math.pow(a, b)

        unaryOps.put("sqrt", x -> {
            if (x < 0) {
                throw new ArithmeticException("Cannot take the square root of a negative number.");
            }
            return Math.sqrt(x);
        });
        unaryOps.put("square", x -> x * x);
    }

    public Set<String> binarySymbols() {
        return binaryOps.keySet();
    }

    public Set<String> unaryNames() {
        return unaryOps.keySet();
    }

    public double calculate(double a, String symbol, double b) {
        Operation op = binaryOps.get(symbol);
        if (op == null) {
            throw new IllegalArgumentException("Unknown operator: " + symbol);
        }
        double result = checked(op.apply(a, b));
        history.add(format(a) + " " + symbol + " " + format(b) + " = " + format(result));
        return result;
    }

    public double calculateOne(String name, double x) {
        UnaryOperator<Double> op = unaryOps.get(name);
        if (op == null) {
            throw new IllegalArgumentException("Unknown function: " + name);
        }
        double result = checked(op.apply(x));
        history.add(name + "(" + format(x) + ") = " + format(result));
        return result;
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    // rejects Infinity and NaN (for example 10 ^ 400)
    private static double checked(double result) {
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Result is too large or not a number.");
        }
        return result;
    }

    // shows 15 instead of 15.0, but keeps decimals like 3.5
    public static String format(double value) {
        if (!Double.isNaN(value) && !Double.isInfinite(value)
                && value == Math.floor(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}