package employeesort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class EmployeeApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome! Sort employees using Comparable and Comparator.");
        System.out.println("Tip: use option 2 to load sample data quickly.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-8): ");
            try {
                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> loadSample();
                    case 3 -> print("Employees (in the order added)", employees);
                    case 4 -> sortByComparable();
                    case 5 -> sortWith("Salary HIGH to LOW (Comparator: SalaryDescendingComparator)",
                            new SalaryDescendingComparator());
                    case 6 -> sortWith("Name A to Z (Comparator: NameComparator)", new NameComparator());
                    case 7 -> sortWith("ID low to high (Comparator: IdComparator)", new IdComparator());
                    case 8 -> explain();
                    case 0 -> System.out.println("Goodbye!");
                    default -> System.out.println("Please choose a number from 0 to 8.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Not added: " + e.getMessage());
            }
            if (choice >= 1 && choice <= 8) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=========== EMPLOYEE MENU ===========");
        System.out.println("  1. Add an employee");
        System.out.println("  2. Load 5 sample employees");
        System.out.println("  3. Show employees (order added)");
        System.out.println("  4. Sort by salary LOW to HIGH   (Comparable)");
        System.out.println("  5. Sort by salary HIGH to LOW   (Comparator)");
        System.out.println("  6. Sort by name A to Z          (Comparator)");
        System.out.println("  7. Sort by ID                   (Comparator)");
        System.out.println("  8. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("=====================================");
    }

    private static void addEmployee() {
        int id = readInt("Employee ID (example: 106): ");
        String name = readText("Name (example: Neha): ");
        double salary = readDouble("Salary (example: 50000): ");
        Employee e = new Employee(id, name, salary);
        if (findById(id) != null) {
            System.out.println("An employee with ID " + id + " already exists.");
            return;
        }
        employees.add(e);
        System.out.println("Added: " + e.getName());
    }

    private static void loadSample() {
        Employee[] sample = {
            new Employee(101, "Ravi", 45000),
            new Employee(102, "Priya", 62000),
            new Employee(103, "Amit", 38000),
            new Employee(104, "Sneha", 62000),
            new Employee(105, "Karan", 55000)
        };
        int added = 0;
        for (Employee e : sample) {
            if (findById(e.getId()) == null) {
                employees.add(e);
                added++;
            }
        }
        System.out.println(added + " sample employee(s) added. Total now: " + employees.size());
    }

    // Comparable: sort() uses compareTo() written inside Employee
    private static void sortByComparable() {
        List<Employee> copy = new ArrayList<>(employees);
        Collections.sort(copy);
        print("Salary LOW to HIGH (Comparable: Employee.compareTo)", copy);
    }

    // Comparator: the rule comes from a separate class
    private static void sortWith(String title, Comparator<Employee> comparator) {
        List<Employee> copy = new ArrayList<>(employees);
        copy.sort(comparator);
        print(title, copy);
    }

    private static void print(String title, List<Employee> list) {
        System.out.println();
        System.out.println("--- " + title + " ---");
        if (list.isEmpty()) {
            System.out.println("No employees yet. Use option 1 or 2 first.");
            return;
        }
        System.out.printf("%-6s %-12s %12s%n", "ID", "Name", "Salary");
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    private static Employee findById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    private static void explain() {
        System.out.println();
        System.out.println("COMPARABLE (natural order, inside the class)");
        System.out.println("  - Employee implements Comparable<Employee> and writes compareTo()");
        System.out.println("  - gives ONE default order: salary low to high");
        System.out.println("  - used by Collections.sort(list)");
        System.out.println();
        System.out.println("COMPARATOR (custom order, in a separate class)");
        System.out.println("  - SalaryDescendingComparator, NameComparator, IdComparator");
        System.out.println("  - you can have many, without changing Employee");
        System.out.println("  - used by list.sort(comparator)");
        System.out.println();
        System.out.println("TIE RULE: same salary or same name is ordered by ID, so output is stable.");
        System.out.println("Sorting works on a copy, so option 3 always shows the order added.");
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a whole number. Try again (example: 101).");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a number. Try again (example: 50000).");
            }
        }
    }
}