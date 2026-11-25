package employeestream;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class EmployeeApp {

    private static final Scanner sc = new Scanner(System.in);

    private static final EmployeeService service = new EmployeeService(List.of(
            new Employee(1, "Aarav Sharma", "Engineering", 90000, 28, "Pune"),
            new Employee(2, "Diya Patel", "Engineering", 120000, 35, "Bengaluru"),
            new Employee(3, "Rohan Mehta", "Engineering", 75000, 24, "Pune"),
            new Employee(4, "Sneha Iyer", "HR", 55000, 31, "Mumbai"),
            new Employee(5, "Kabir Singh", "HR", 48000, 26, "Delhi"),
            new Employee(6, "Meera Nair", "Finance", 82000, 40, "Mumbai"),
            new Employee(7, "Arjun Rao", "Finance", 67000, 29, "Bengaluru"),
            new Employee(8, "Isha Verma", "Marketing", 60000, 27, "Delhi"),
            new Employee(9, "Vikram Joshi", "Marketing", 72000, 33, "Pune"),
            new Employee(10, "Tanvi Desai", "Engineering", 105000, 30, "Mumbai")
    ));

    public static void main(String[] args) {
        System.out.println("Welcome! Employee records using the Java Stream API.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-9): ");
            switch (choice) {
                case 1 -> printTable("ALL EMPLOYEES", service.all());
                case 2 -> filterBySalary();
                case 3 -> filterByDepartment();
                case 4 -> groupByDepartment();
                case 5 -> departmentStats();
                case 6 -> printTable("TOP 3 HIGHEST PAID", service.topPaid(3));
                case 7 -> splitByAge();
                case 8 -> salarySummary();
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
        System.out.println("========= EMPLOYEE MENU =========");
        System.out.println("  1. List all employees");
        System.out.println("  2. Filter by minimum salary");
        System.out.println("  3. Filter by department");
        System.out.println("  4. Group by department");
        System.out.println("  5. Department statistics");
        System.out.println("  6. Top 3 highest paid");
        System.out.println("  7. Split by age (30 and above / under 30)");
        System.out.println("  8. Salary summary and cities");
        System.out.println("  9. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("=================================");
    }

    private static void filterBySalary() {
        double min = readDouble("Minimum salary (example: 80000): ");
        if (min < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }
        printTable("SALARY >= " + money(min) + ", highest first", service.withMinSalary(min));
    }

    private static void filterByDepartment() {
        System.out.print("Department (Engineering, Finance, HR, Marketing): ");
        String dept = sc.nextLine().trim();
        if (dept.isEmpty()) {
            System.out.println("Please type a department name.");
            return;
        }
        printTable("DEPARTMENT " + dept, service.inDepartment(dept));
    }

    private static void groupByDepartment() {
        System.out.println();
        System.out.println("EMPLOYEES BY DEPARTMENT");
        service.groupByDepartment().forEach((dept, list) -> {
            System.out.println(dept + " (" + list.size() + ")");
            list.forEach(e -> System.out.println("  - " + e.getName() + " (" + money(e.getSalary()) + ")"));
        });
    }

    private static void departmentStats() {
        Map<String, Long> counts = service.countByDepartment();
        Map<String, Double> averages = service.averageSalaryByDepartment();
        Map<String, String> best = service.highestPaidByDepartment();
        System.out.println();
        System.out.println("DEPARTMENT STATISTICS");
        counts.forEach((dept, count) -> System.out.printf(Locale.US,
                "%-12s count=%d  average=%s  highest paid=%s%n",
                dept, count, money(averages.get(dept)), best.get(dept)));
    }

    private static void splitByAge() {
        Map<Boolean, List<Employee>> parts = service.splitByAge(30);
        printTable("AGE 30 AND ABOVE", parts.get(true));
        printTable("UNDER 30", parts.get(false));
    }

    private static void salarySummary() {
        DoubleSummaryStatistics s = service.salaryStats();
        System.out.println();
        System.out.println("SALARY SUMMARY");
        System.out.println("  Employees : " + s.getCount());
        System.out.println("  Lowest    : " + money(s.getMin()));
        System.out.println("  Highest   : " + money(s.getMax()));
        System.out.println("  Average   : " + money(s.getAverage()));
        System.out.println("  Total     : " + money(s.getSum()));
        System.out.println("  Cities    : " + service.distinctCities());
    }

    private static void printTable(String title, List<Employee> list) {
        System.out.println();
        System.out.println(title + " (" + list.size() + ")");
        if (list.isEmpty()) {
            System.out.println("  No employees found.");
            return;
        }
        System.out.printf(Locale.US, "%-4s %-14s %-12s %10s %4s  %s%n",
                "ID", "Name", "Department", "Salary", "Age", "City");
        list.forEach(e -> System.out.printf(Locale.US, "%-4d %-14s %-12s %10.2f %4d  %s%n",
                e.getId(), e.getName(), e.getDepartment(), e.getSalary(), e.getAge(), e.getCity()));
    }

    private static String money(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    private static void explain() {
        System.out.println();
        System.out.println("STREAM PIPELINE");
        System.out.println("  source -> intermediate operations -> terminal operation");
        System.out.println("  filter, map, sorted, distinct, limit   build the pipeline (lazy)");
        System.out.println("  toList, collect, count, sum            run it and give the result");
        System.out.println();
        System.out.println("GROUPING");
        System.out.println("  Collectors.groupingBy(key, ...) makes a Map: key -> group");
        System.out.println("  counting, averagingDouble, maxBy are 'downstream' collectors per group");
        System.out.println("  TreeMap::new keeps the departments in alphabetical order");
        System.out.println();
        System.out.println("PARTITIONING");
        System.out.println("  Collectors.partitioningBy(test) always gives exactly two groups: true and false");
        System.out.println();
        System.out.println("SUMMARY");
        System.out.println("  summaryStatistics() gives count, min, max, average and sum in one pass");
        System.out.println("  A stream never changes the original list, and it can be used only once");
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
                System.out.println("  Please enter a normal number (example: 80000).");
            } catch (NumberFormatException e) {
                System.out.println("  That is not a number. Try again (example: 80000).");
            }
        }
    }
}