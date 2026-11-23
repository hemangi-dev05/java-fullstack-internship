package com.internship.module1.payment;

public class CreditCard implements Payable, Refundable, Verifiable {

    private String pin;
    private double limit;
    private double spent;

    public CreditCard(String pin, double limit) {
        this.pin = pin;
        this.limit = limit;
    }

    @Override
    public String getName() {
        return "Credit Card";
    }

    @Override
    public boolean verify(String enteredPin) {
        return pin.equals(enteredPin);
    }

    @Override
    public void pay(double amount) {
        if (spent + amount > limit) {
            throw new IllegalArgumentException(String.format("Credit limit exceeded. Available: %.2f", limit - spent));
        }
        spent += amount;
        System.out.printf("Paid %.2f by Credit Card. Available limit: %.2f%n", amount, limit - spent);
    }

    @Override
    public void refund(double amount) {
        if (amount > spent) {
            throw new IllegalArgumentException(String.format("Refund is more than the amount paid (%.2f)", spent));
        }
        spent -= amount;
        System.out.printf("Refunded %.2f to Credit Card. Available limit: %.2f%n", amount, limit - spent);
    }
}