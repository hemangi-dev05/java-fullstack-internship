package com.internship.module1.payroll;

public class Manager extends FullTimeEmployee {
    private double bonus;

    public Manager(int id, String name, double basicSalary, double bonus) {
        super(id, name, basicSalary);
        this.bonus = bonus;
    }

    public String getType() {
        return "Manager";
    }

    public double calculateSalary() {
        return super.calculateSalary() + bonus;
    }
}