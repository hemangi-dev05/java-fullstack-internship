# Assignment 11 - Employee Salary Sorting (Comparable and Comparator)

## What it does
A menu app that stores employees (ID, name, salary) and sorts them in different ways. Sorting by salary low to high uses Comparable. The other sorts use Comparator classes.

## Concepts
- Comparable and compareTo()
- Comparator and compare()
- Collections.sort(list) vs list.sort(comparator)
- Tie-breaking when two values are equal

## Files
    src/employeesort/Employee.java                   -> data + compareTo (salary low to high)
    src/employeesort/SalaryDescendingComparator.java -> salary high to low
    src/employeesort/NameComparator.java             -> name A to Z
    src/employeesort/IdComparator.java               -> ID order
    src/employeesort/EmployeeApp.java                -> the menu

## Run
    cd 11-employee-salary-sorting
    javac -d out src\employeesort\*.java
    java -cp out employeesort.EmployeeApp

## Comparable vs Comparator
    Comparable: inside the class, one default order, compareTo(other)
    Comparator: separate class, many orders, compare(a, b)

## Sample test
    Type: 2            (load sample data)
    Type: 4            -> Amit 38000, Ravi 45000, Karan 55000, Priya 62000, Sneha 62000
    Type: 5            -> Priya 62000, Sneha 62000, Karan 55000, Ravi 45000, Amit 38000
    Type: 6            -> Amit, Karan, Priya, Ravi, Sneha
    Type: 1, 106, Neha, -100
    Result: Not added: Salary must be a number, zero or more.

## What happens inside
1. Employees are kept in an ArrayList in the order added.
2. Each sort copies the list first, so the original order is not lost.
3. Option 4 calls Collections.sort(copy), which uses Employee.compareTo().
4. Options 5, 6, 7 call copy.sort(comparator) with a different Comparator class each time.
5. If two employees tie (same salary), the ID decides, so the result is always the same.