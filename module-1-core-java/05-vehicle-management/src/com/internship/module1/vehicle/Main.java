package com.internship.module1.vehicle;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    // Polymorphism: Car, Bike and Truck objects all stored as Vehicle
    static ArrayList<Vehicle> vehicles = new ArrayList<>();

    public static void main(String[] args) {
        int choice = 0;

        while (choice != 6) {
            System.out.println();
            System.out.println("===== Vehicle Management System =====");
            System.out.println("1. Add Car");
            System.out.println("2. Add Bike");
            System.out.println("3. Add Truck");
            System.out.println("4. Show all vehicles");
            System.out.println("5. Calculate rent");
            System.out.println("6. Exit");
            choice = readInt("Enter choice: ");

            try {
                if (choice == 1) {
                    String brand = readText("Enter brand: ");
                    String model = readText("Enter model: ");
                    int seats = readInt("Enter number of seats: ");
                    addVehicle(new Car(vehicles.size() + 1, brand, model, seats));
                } else if (choice == 2) {
                    String brand = readText("Enter brand: ");
                    String model = readText("Enter model: ");
                    int cc = readInt("Enter engine cc: ");
                    addVehicle(new Bike(vehicles.size() + 1, brand, model, cc));
                } else if (choice == 3) {
                    String brand = readText("Enter brand: ");
                    String model = readText("Enter model: ");
                    double load = readDouble("Enter load capacity (tons): ");
                    addVehicle(new Truck(vehicles.size() + 1, brand, model, load));
                } else if (choice == 4) {
                    showAll();
                } else if (choice == 5) {
                    rentVehicle();
                } else if (choice == 6) {
                    System.out.println("Thank you.");
                } else {
                    System.out.println("Invalid choice, enter 1 to 6.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        System.out.print("Added: ");
        vehicle.displayDetails();
    }

    // same displayDetails() call, different output for each type of vehicle
    static void showAll() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles added yet.");
            return;
        }
        for (Vehicle vehicle : vehicles) {
            vehicle.displayDetails();
        }
    }

    static void rentVehicle() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles added yet.");
            return;
        }
        int id = readInt("Enter vehicle ID: ");
        Vehicle vehicle = findById(id);
        if (vehicle == null) {
            System.out.println("No vehicle found with ID " + id);
            return;
        }
        int days = readInt("Enter number of days: ");
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than 0");
        }
        System.out.printf("Rent for %d day(s): %.2f%n", days, vehicle.calculateRent(days));
    }

    static Vehicle findById(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;
    }

    static String readText(String message) {
        System.out.print(message);
        return sc.nextLine();
    }

    static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    static double readDouble(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}