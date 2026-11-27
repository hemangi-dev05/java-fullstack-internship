package empsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class EmployeeSearchApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        loadSampleData();
        System.out.println("Welcome! Employee search with Collections and Comparator.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-9): ");
            switch (choice) {
                case 1 -> display(employees);
                case 2 -> addEmployee();
                case 3 -> searchByName();
                case 4 -> searchByDepartment();
                case 5 -> searchBySalary();
                case 6 -> sortByName();
                case 7 -> sortBySalaryHighToLow();
                case 8 -> sortByDepartmentThenSalary();
                case 9 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 9.");
            }
            if (choice >= 1 && choice <= 9) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= EMPLOYEE SEARCH MENU =========");
        System.out.println("  1. Show all employees");
        System.out.println("  2. Add an employee");
        System.out.println("  3. Search by name");
        System.out.println("  4. Search by department");
        System.out.println("  5. Search by salary range");
        System.out.println("  6. Sort by name (A to Z)");
        System.out.println("  7. Sort by salary (high to low)");
        System.out.println("  8. Sort by department, then salary");
        System.out.println("  9. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("========================================");
    }

    private static void loadSampleData() {
        employees.add(new Employee(101, "Hemangi", "IT", 45000));
        employees.add(new Employee(102, "Rahul", "HR", 38000));
        employees.add(new Employee(103, "Priya", "IT", 52000));
        employees.add(new Employee(104, "Amit", "Finance", 47000));
        employees.add(new Employee(105, "Sneha", "HR", 41000));
        employees.add(new Employee(106, "Karan", "Finance", 60000));
        employees.add(new Employee(107, "Neha", "IT", 39000));
    }

    private static void display(List<Employee> list) {
        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.printf("%-5s %-12s %-12s %10s%n", "ID", "Name", "Department", "Salary");
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("Count: " + list.size());
    }

    private static void addEmployee() {
        int id = readInt("Employee ID: ");
        if (id <= 0) {
            System.out.println("ID must be a positive number.");
            return;
        }
        for (Employee e : employees) {
            if (e.getId() == id) {
                System.out.println("An employee with ID " + id + " already exists.");
                return;
            }
        }
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        System.out.print("Department: ");
        String department = sc.nextLine().trim();
        if (department.isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }
        double salary = readDouble("Salary: ");
        if (salary <= 0) {
            System.out.println("Salary must be more than 0.");
            return;
        }
        employees.add(new Employee(id, name, department, salary));
        System.out.println("Employee added.");
    }

    private static void searchByName() {
        System.out.print("Enter name (or part of it): ");
        String keyword = sc.nextLine().trim().toLowerCase();
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getName().toLowerCase().contains(keyword)) {
                result.add(e);
            }
        }
        display(result);
    }

    private static void searchByDepartment() {
        System.out.print("Enter department: ");
        String dept = sc.nextLine().trim();
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getDepartment().equalsIgnoreCase(dept)) {
                result.add(e);
            }
        }
        display(result);
    }

    private static void searchBySalary() {
        double min = readDouble("Minimum salary: ");
        double max = readDouble("Maximum salary: ");
        if (min > max) {
            System.out.println("Minimum cannot be more than maximum.");
            return;
        }
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getSalary() >= min && e.getSalary() <= max) {
                result.add(e);
            }
        }
        display(result);
    }

    // Comparator written as an anonymous class
    private static void sortByName() {
        List<Employee> sorted = new ArrayList<>(employees);   // copy, so the original order stays
        Collections.sort(sorted, new Comparator<Employee>() {
            @Override
            public int compare(Employee a, Employee b) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
        });
        display(sorted);
    }

    // Comparator written as a lambda
    private static void sortBySalaryHighToLow() {
        List<Employee> sorted = new ArrayList<>(employees);
        sorted.sort((a, b) -> Double.compare(b.getSalary(), a.getSalary()));
        display(sorted);
    }

    // Comparators chained together
    private static void sortByDepartmentThenSalary() {
        List<Employee> sorted = new ArrayList<>(employees);
        sorted.sort(Comparator.comparing(Employee::getDepartment)
                .thenComparing(Employee::getSalary, Comparator.reverseOrder()));
        display(sorted);
    }

    private static void explain() {
        System.out.println();
        System.out.println("COLLECTIONS");
        System.out.println("  Employees are stored in an ArrayList. Searching loops through the list");
        System.out.println("  and collects the matching employees into a new list.");
        System.out.println();
        System.out.println("COMPARATOR");
        System.out.println("  A Comparator tells Java how to order two objects.");
        System.out.println("  compare(a, b) returns a negative number, zero or a positive number.");
        System.out.println("  Three ways are used here:");
        System.out.println("    1. Anonymous class with Collections.sort (sort by name)");
        System.out.println("    2. Lambda with list.sort (salary, high to low)");
        System.out.println("    3. Comparator.comparing().thenComparing() (department, then salary)");
        System.out.println();
        System.out.println("NOTE");
        System.out.println("  Sorting is done on a copy of the list, so option 1 always shows the original order.");
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
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a number. Try again (example: 45000).");
            }
        }
    }
}