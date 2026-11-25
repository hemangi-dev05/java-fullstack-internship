# Assignment 15 - Java Stream API: Filter and Group Employee Records

## What it does
A menu app with 10 sample employees. It filters, sorts, groups and summarises them using only the Stream API, with no manual for loops.

## Features
    Filter by minimum salary   | Filter by department
    Group by department        | Count, average and highest paid per department
    Top 3 highest paid         | Split by age (30 and above / under 30)
    Salary summary             | Sorted list of distinct cities

## Concepts
- Stream pipeline: source, intermediate operations, terminal operation
- filter, map, sorted, distinct, limit, toList
- Collectors.groupingBy with downstream collectors (counting, averagingDouble, maxBy)
- Collectors.partitioningBy, Collectors.joining
- summaryStatistics() on a DoubleStream

## Files
    src/employeestream/Employee.java         -> the data class
    src/employeestream/EmployeeService.java  -> all the stream code
    src/employeestream/EmployeeApp.java      -> the menu

## Run
    cd 15-employee-streams
    javac -d out src\employeestream\*.java
    java -cp out employeestream.EmployeeApp

## Sample test
    Type: 2, 80000
    Result: 4 employees, highest salary first (Diya Patel on top)

    Type: 5
    Result: Engineering count=4 average=97500.00 highest paid=Diya Patel (and 3 more departments)

    Type: 8
    Result: 10 employees, total 774000.00, average 77400.00

## What happens inside
1. EmployeeService holds the list and every method is one stream pipeline.
2. Filters keep matching employees, sorted() orders them, toList() ends the pipeline.
3. groupingBy builds a Map from department to employees, with a downstream collector per group.
4. partitioningBy splits the list into two parts using one test.
5. The original list is never changed.