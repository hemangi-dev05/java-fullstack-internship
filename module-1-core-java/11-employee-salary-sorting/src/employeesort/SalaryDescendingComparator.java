package employeesort;

import java.util.Comparator;

public class SalaryDescendingComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee a, Employee b) {
        int result = Double.compare(b.getSalary(), a.getSalary());
        if (result != 0) {
            return result;
        }
        return Integer.compare(a.getId(), b.getId());
    }
}