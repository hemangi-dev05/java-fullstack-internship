package com.internship.module1.vehicle;

public class Car extends Vehicle {

    private int seats;

    public Car(int id, String brand, String model, int seats) {
        super(id, brand, model);
        if (seats <= 0) {
            throw new IllegalArgumentException("Seats must be greater than 0");
        }
        this.seats = seats;
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public String getSpecialDetail() {
        return "Seats: " + seats;
    }

    // car rent = 1500 per day
    @Override
    public double calculateRent(int days) {
        return days * 1500;
    }
}