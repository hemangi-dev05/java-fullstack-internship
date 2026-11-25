package employeesort;

public class Employee implements Comparable<Employee> {

    private final int id;
    private final String name;
    private final double salary;

    public Employee(int id, String name, double salary) {
        if (id <= 0) {
            throw new IllegalArgumentException("Employee ID must be greater than zero.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (Double.isNaN(salary) || Double.isInfinite(salary) || salary < 0) {
            throw new IllegalArgumentException("Salary must be a number, zero or more.");
        }
        this.id = id;
        this.name = name.trim();
        this.salary = salary;
    }

    // natural order: lowest salary first, equal salaries are ordered by ID
    @Override
    public int compareTo(Employee other) {
        int result = Double.compare(this.salary, other.salary);
        if (result != 0) {
            return result;
        }
        return Integer.compare(this.id, other.id);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("%-6d %-12s %12.2f", id, name, salary);
    }
}