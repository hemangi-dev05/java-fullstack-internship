package com.internship.module1.vehicle;

public class Bike extends Vehicle {

    private int engineCc;

    public Bike(int id, String brand, String model, int engineCc) {
        super(id, brand, model);
        if (engineCc <= 0) {
            throw new IllegalArgumentException("Engine cc must be greater than 0");
        }
        this.engineCc = engineCc;
    }

    @Override
    public String getVehicleType() {
        return "Bike";
    }

    @Override
    public String getSpecialDetail() {
        return "Engine: " + engineCc + " cc";
    }

    // bike rent = 500 per day
    @Override
    public double calculateRent(int days) {
        return days * 500;
    }
}