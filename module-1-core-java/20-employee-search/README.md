# Assignment 20 - Employee Search Application using Collections and Comparator

## What it does
A menu-driven console app that stores employees in an ArrayList. You can search by name, department or salary range and sort the list in different ways using Comparator.

## Features
    Show all employees and add new ones (ID must be unique)
    Search by name (partial match), department, salary range
    Sort by name, by salary (high to low), by department then salary
    Input validation (empty fields, wrong numbers, minimum above maximum)

## Concepts
- ArrayList and the Collections class
- Comparator as an anonymous class, a lambda and Comparator.comparing()
- thenComparing() for sorting on two fields
- Sorting a copy so the original list is not changed
- Case-insensitive searching

## Files
    src/empsearch/Employee.java            -> the employee class
    src/empsearch/EmployeeSearchApp.java   -> the menu, searching and sorting

## Run
    cd 20-employee-search
    javac -d out src\empsearch\*.java
    java -cp out empsearch.EmployeeSearchApp

## Sample test
    Type: 3, then ne
    Result: Neha is shown

    Type: 5, then 40000 and 50000
    Result: employees earning between 40000 and 50000

    Type: 8
    Result: sorted by department, highest salary first inside each department

## What happens inside
1. Sample employees are loaded into an ArrayList when the program starts.
2. Searches loop through the list and collect matching employees.
3. Sorting makes a copy of the list and sorts it with a Comparator.