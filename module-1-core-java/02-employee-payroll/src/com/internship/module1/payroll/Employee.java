package com.internship.module1.payroll;

public class Employee {
    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return "Employee";
    }

    public double calculateSalary() {
        return 0;
    }

    public void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Type: " + getType()
                + " | Net Salary: " + calculateSalary());
    }
}