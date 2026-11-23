package com.internship.module1.payroll;

public class FullTimeEmployee extends Employee {
    private double basicSalary;

    public FullTimeEmployee(int id, String name, double basicSalary) {
        super(id, name);
        this.basicSalary = basicSalary;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public String getType() {
        return "Full-Time";
    }

    // HRA = 20%, DA = 10%, PF deduction = 12% of basic
    public double calculateSalary() {
        double hra = basicSalary * 0.20;
        double da = basicSalary * 0.10;
        double pf = basicSalary * 0.12;
        return basicSalary + hra + da - pf;
    }
}