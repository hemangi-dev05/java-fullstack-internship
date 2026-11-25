package employeesort;

import java.util.Comparator;

public class IdComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee a, Employee b) {
        return Integer.compare(a.getId(), b.getId());
    }
}