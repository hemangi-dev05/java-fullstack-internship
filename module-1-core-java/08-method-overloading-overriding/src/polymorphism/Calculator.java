package polymorphism;

public class Calculator {

    // same method name, different parameters: resolved at compile time
    public int add(int a, int b) {
        return Math.addExact(a, b);
    }

    public int add(int a, int b, int c) {
        return Math.addExact(Math.addExact(a, b), c);
    }

    public double add(double a, double b) {
        return a + b;
    }

    public String add(String a, String b) {
        return a + b;
    }
}