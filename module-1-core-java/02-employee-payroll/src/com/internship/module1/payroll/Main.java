package com.internship.module1.payroll;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Employee> employees = new ArrayList<Employee>();

    public static void main(String[] args) {
        int choice = -1;

        while (choice != 0) {
            System.out.println();
            System.out.println("===== Employee Payroll System =====");
            System.out.println("1. Add full-time employee");
            System.out.println("2. Add part-time employee");
            System.out.println("3. Add manager");
            System.out.println("4. View all employees and salaries");
            System.out.println("5. Total payroll");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addFullTime();
                    break;
                case 2:
                    addPartTime();
                    break;
                case 3:
                    addManager();
                    break;
                case 4:
                    viewAll();
                    break;
                case 5:
                    totalPayroll();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static boolean idExists(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return true;
            }
        }
        return false;
    }

    static void addFullTime() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (idExists(id)) {
            System.out.println("Error: ID already exists.");
            return;
        }
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Basic salary: ");
        double basic = sc.nextDouble();
        sc.nextLine();
        if (basic <= 0) {
            System.out.println("Error: Salary must be greater than 0.");
            return;
        }
        employees.add(new FullTimeEmployee(id, name, basic));
        System.out.println("Full-time employee added.");
    }

    static void addPartTime() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (idExists(id)) {
            System.out.println("Error: ID already exists.");
            return;
        }
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Hours worked: ");
        int hours = sc.nextInt();
        System.out.print("Hourly rate: ");
        double rate = sc.nextDouble();
        sc.nextLine();
        if (hours <= 0 || rate <= 0) {
            System.out.println("Error: Hours and rate must be greater than 0.");
            return;
        }
        employees.add(new PartTimeEmployee(id, name, hours, rate));
        System.out.println("Part-time employee added.");
    }

    static void addManager() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (idExists(id)) {
            System.out.println("Error: ID already exists.");
            return;
        }
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Basic salary: ");
        double basic = sc.nextDouble();
        System.out.print("Bonus: ");
        double bonus = sc.nextDouble();
        sc.nextLine();
        if (basic <= 0 || bonus < 0) {
            System.out.println("Error: Invalid salary or bonus.");
            return;
        }
        employees.add(new Manager(id, name, basic, bonus));
        System.out.println("Manager added.");
    }

    static void viewAll() {
        if (employees.size() == 0) {
            System.out.println("No employees found.");
            return;
        }
        for (Employee e : employees) {
            e.display();
        }
    }

    static void totalPayroll() {
        double total = 0;
        for (Employee e : employees) {
            total = total + e.calculateSalary();
        }
        System.out.println("Total payroll: " + total);
    }
}