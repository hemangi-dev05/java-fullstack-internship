package com.internship.module1.banking;

import java.util.ArrayList;

public class Account {
    public static final double MIN_BALANCE = 500;
    public static final double MAX_WITHDRAW = 50000;
    public static final double MAX_DEPOSIT = 1000000;

    private int accountNumber;
    private String holderName;
    private double balance;
    private ArrayList<String> transactions = new ArrayList<String>();

    public Account(int accountNumber, String holderName, double openingBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = openingBalance;
        transactions.add("Account opened | Amount: " + openingBalance + " | Balance: " + balance);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactions() {
        return new ArrayList<String>(transactions);
    }

    public static String checkAmount(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            return "Invalid amount.";
        }
        if (amount <= 0) {
            return "Amount must be greater than 0.";
        }
        if (Math.abs(amount * 100 - Math.round(amount * 100)) > 0.0001) {
            return "Amount can have at most 2 decimal places.";
        }
        return "";
    }

    public String deposit(double amount) {
        return deposit(amount, "Deposit");
    }

    public String deposit(double amount, String note) {
        String error = checkAmount(amount);
        if (!error.equals("")) {
            return error;
        }
        if (amount > MAX_DEPOSIT) {
            return "Single deposit limit is " + MAX_DEPOSIT + ".";
        }
        balance = Math.round((balance + amount) * 100) / 100.0;
        transactions.add(note + " | +" + amount + " | Balance: " + balance);
        return "";
    }

    public String withdraw(double amount) {
        return withdraw(amount, "Withdrawal");
    }

    public String withdraw(double amount, String note) {
        String error = checkAmount(amount);
        if (!error.equals("")) {
            return error;
        }
        if (amount > MAX_WITHDRAW) {
            return "Single withdrawal limit is " + MAX_WITHDRAW + ".";
        }
        if (balance - amount < MIN_BALANCE) {
            return "Insufficient balance. Minimum balance of " + MIN_BALANCE
                    + " must be maintained. You can withdraw up to " + (balance - MIN_BALANCE) + ".";
        }
        balance = Math.round((balance - amount) * 100) / 100.0;
        transactions.add(note + " | -" + amount + " | Balance: " + balance);
        return "";
    }
}