package employeestream;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class EmployeeService {

    private final List<Employee> employees;

    public EmployeeService(List<Employee> employees) {
        this.employees = List.copyOf(employees);
    }

    public List<Employee> all() {
        return employees;
    }

    // filter, then sort by salary (highest first)
    public List<Employee> withMinSalary(double min) {
        return employees.stream()
                .filter(e -> e.getSalary() >= min)
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .toList();
    }

    public List<Employee> inDepartment(String department) {
        return employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .toList();
    }

    // department -> list of employees (TreeMap::new keeps departments in alphabetical order)
    public Map<String, List<Employee>> groupByDepartment() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new, Collectors.toList()));
    }

    public Map<String, Long> countByDepartment() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new, Collectors.counting()));
    }

    public Map<String, Double> averageSalaryByDepartment() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new,
                        Collectors.averagingDouble(Employee::getSalary)));
    }

    // maxBy gives an Optional, collectingAndThen turns it into the name
    // (get() is safe because a group always has at least one employee)
    public Map<String, String> highestPaidByDepartment() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),
                                best -> best.get().getName())));
    }

    public List<Employee> topPaid(int n) {
        return employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(n)
                .toList();
    }

    // partitioningBy always gives exactly two groups: true and false
    public Map<Boolean, List<Employee>> splitByAge(int age) {
        return employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getAge() >= age));
    }

    public DoubleSummaryStatistics salaryStats() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();
    }

    public String distinctCities() {
        return employees.stream()
                .map(Employee::getCity)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }
}