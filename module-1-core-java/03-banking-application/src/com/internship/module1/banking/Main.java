package com.internship.module1.banking;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Bank bank = new Bank();

    public static void main(String[] args) {
        int choice = -1;

        while (choice != 0) {
            System.out.println();
            System.out.println("===== Banking Application =====");
            System.out.println("1. Create account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Check balance");
            System.out.println("6. Mini statement");
            System.out.println("7. View all accounts");
            System.out.println("0. Exit");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    miniStatement();
                    break;
                case 7:
                    viewAll();
                    break;
                case 0:
                    System.out.println("Thank you for banking with us!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a whole number.");
            sc.next();
            System.out.print(prompt);
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
            System.out.print(prompt);
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static Account askAccount() {
        int number = readInt("Account number: ");
        Account a = bank.findAccount(number);
        if (a == null) {
            System.out.println("Error: Account not found.");
        }
        return a;
    }

    static void createAccount() {
        String name = readText("Account holder name: ");
        double opening = readDouble("Opening balance (minimum " + Account.MIN_BALANCE + "): ");
        String error = bank.validateNewAccount(name, opening);
        if (!error.equals("")) {
            System.out.println("Error: " + error);
        } else {
            Account a = bank.createAccount(name, opening);
            System.out.println("Account created. Your account number is " + a.getAccountNumber());
        }
    }

    static void deposit() {
        Account a = askAccount();
        if (a == null) {
            return;
        }
        double amount = readDouble("Deposit amount: ");
        String error = a.deposit(amount);
        if (!error.equals("")) {
            System.out.println("Error: " + error);
        } else {
            System.out.println("Deposit successful. New balance: " + a.getBalance());
        }
    }

    static void withdraw() {
        Account a = askAccount();
        if (a == null) {
            return;
        }
        double amount = readDouble("Withdrawal amount: ");
        String error = a.withdraw(amount);
        if (!error.equals("")) {
            System.out.println("Error: " + error);
        } else {
            System.out.println("Withdrawal successful. New balance: " + a.getBalance());
        }
    }

    static void transfer() {
        int from = readInt("From account number: ");
        int to = readInt("To account number: ");
        double amount = readDouble("Transfer amount: ");
        String error = bank.transfer(from, to, amount);
        if (!error.equals("")) {
            System.out.println("Error: " + error);
        } else {
            System.out.println("Transfer successful.");
            System.out.println("Balance of " + from + ": " + bank.findAccount(from).getBalance());
            System.out.println("Balance of " + to + ": " + bank.findAccount(to).getBalance());
        }
    }

    static void checkBalance() {
        Account a = askAccount();
        if (a != null) {
            System.out.println(a.getHolderName() + " | Balance: " + a.getBalance());
        }
    }

    static void miniStatement() {
        Account a = askAccount();
        if (a == null) {
            return;
        }
        System.out.println("--- Statement for " + a.getHolderName() + " (" + a.getAccountNumber() + ") ---");
        ArrayList<String> list = a.getTransactions();
        for (String line : list) {
            System.out.println(line);
        }
    }

    static void viewAll() {
        ArrayList<Account> list = bank.getAll();
        if (list.size() == 0) {
            System.out.println("No accounts yet.");
            return;
        }
        for (Account a : list) {
            System.out.println("Account: " + a.getAccountNumber() + " | Name: " + a.getHolderName()
                    + " | Balance: " + a.getBalance());
        }
    }
}