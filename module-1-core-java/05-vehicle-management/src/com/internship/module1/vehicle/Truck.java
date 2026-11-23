package com.internship.module1.vehicle;

public class Truck extends Vehicle {

    private double loadCapacity;

    public Truck(int id, String brand, String model, double loadCapacity) {
        super(id, brand, model);
        if (loadCapacity <= 0) {
            throw new IllegalArgumentException("Load capacity must be greater than 0");
        }
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String getVehicleType() {
        return "Truck";
    }

    @Override
    public String getSpecialDetail() {
        return "Load: " + loadCapacity + " tons";
    }

    // truck rent per day = 2000 + (100 for every ton of load capacity)
    @Override
    public double calculateRent(int days) {
        double perDay = 2000 + (loadCapacity * 100);
        return days * perDay;
    }
}