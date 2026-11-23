package com.internship.module1.payroll;

public class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(int id, String name, int hoursWorked, double hourlyRate) {
        super(id, name);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public String getType() {
        return "Part-Time";
    }

    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}