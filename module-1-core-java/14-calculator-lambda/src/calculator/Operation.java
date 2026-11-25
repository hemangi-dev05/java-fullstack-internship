package calculator;

// A functional interface has exactly ONE abstract method, so a lambda can implement it.
@FunctionalInterface
public interface Operation {
    double apply(double a, double b);
}