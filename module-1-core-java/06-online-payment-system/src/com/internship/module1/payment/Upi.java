package com.internship.module1.payment;

public class Upi implements Payable, Refundable, Verifiable {

    private String upiId;
    private String pin;
    private double balance;
    private double spent;

    public Upi(String upiId, String pin, double balance) {
        this.upiId = upiId;
        this.pin = pin;
        this.balance = balance;
    }

    @Override
    public String getName() {
        return "UPI (" + upiId + ")";
    }

    @Override
    public boolean verify(String enteredPin) {
        return pin.equals(enteredPin);
    }

    @Override
    public void pay(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException(String.format("Insufficient bank balance. Balance: %.2f", balance));
        }
        balance -= amount;
        spent += amount;
        System.out.printf("Paid %.2f by UPI. Bank balance: %.2f%n", amount, balance);
    }

    @Override
    public void refund(double amount) {
        if (amount > spent) {
            throw new IllegalArgumentException(String.format("Refund is more than the amount paid (%.2f)", spent));
        }
        balance += amount;
        spent -= amount;
        System.out.printf("Refunded %.2f to UPI. Bank balance: %.2f%n", amount, balance);
    }
}