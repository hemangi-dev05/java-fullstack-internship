package com.internship.module1.vehicle;

// Abstract class: it cannot be created with "new Vehicle()".
// It holds what every vehicle has in common, and forces each child
// class to write its own getVehicleType(), getSpecialDetail() and calculateRent().
public abstract class Vehicle {

    private int id;
    private String brand;
    private String model;

    public Vehicle(int id, String brand, String model) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        this.id = id;
        this.brand = brand.trim();
        this.model = model.trim();
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    // abstract methods: no body here, every child must provide one
    public abstract String getVehicleType();

    public abstract String getSpecialDetail();

    public abstract double calculateRent(int days);

    // normal method: shared by all vehicles
    public void displayDetails() {
        System.out.printf("ID: %d | %-5s | %s %s | %s%n",
                id, getVehicleType(), brand, model, getSpecialDetail());
    }
}