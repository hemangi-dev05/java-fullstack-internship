package employeesort;

import java.util.Comparator;

public class NameComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee a, Employee b) {
        int result = a.getName().compareToIgnoreCase(b.getName());
        if (result != 0) {
            return result;
        }
        return Integer.compare(a.getId(), b.getId());
    }
}