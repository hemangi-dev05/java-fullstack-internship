package com.internship.module1.payment;

public class CashOnDelivery implements Payable {

    @Override
    public String getName() {
        return "Cash on Delivery";
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Order placed. Pay %.2f in cash at delivery.%n", amount);
    }
}