package com.internship.module1.payment;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Payable[] methods = {
                new CreditCard("1234", 50000),
                new Upi("hemangi@upi", "4321", 20000),
                new CashOnDelivery()
        };
        ArrayList<String> history = new ArrayList<>();
        int choice = 0;

        while (choice != 4) {
            System.out.println();
            System.out.println("===== Online Payment System =====");
            System.out.println("1. Make payment");
            System.out.println("2. Refund");
            System.out.println("3. Transaction history");
            System.out.println("4. Exit");
            choice = (int) readNumber("Enter choice: ");

            try {
                if (choice == 1) {
                    Payable method = pickMethod(methods);
                    double amount = readAmount();
                    if (method instanceof Verifiable) {
                        System.out.print("Enter PIN: ");
                        if (!((Verifiable) method).verify(sc.nextLine().trim())) {
                            System.out.println("Wrong PIN. Payment cancelled.");
                            continue;
                        }
                    }
                    method.pay(amount);
                    history.add(String.format("Paid %.2f using %s", amount, method.getName()));
                } else if (choice == 2) {
                    Payable method = pickMethod(methods);
                    if (!(method instanceof Refundable)) {
                        System.out.println(method.getName() + " does not support refunds.");
                        continue;
                    }
                    double amount = readAmount();
                    ((Refundable) method).refund(amount);
                    history.add(String.format("Refunded %.2f to %s", amount, method.getName()));
                } else if (choice == 3) {
                    showHistory(history);
                } else if (choice == 4) {
                    System.out.println("Thank you.");
                } else {
                    System.out.println("Invalid choice, enter 1 to 4.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static Payable pickMethod(Payable[] methods) {
        for (int i = 0; i < methods.length; i++) {
            System.out.println((i + 1) + ". " + methods[i].getName());
        }
        int number = (int) readNumber("Select method: ");
        if (number < 1 || number > methods.length) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        return methods[number - 1];
    }

    static void showHistory(ArrayList<String> history) {
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }

    static double readAmount() {
        double amount = readNumber("Enter amount: ");
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        return amount;
    }

    static double readNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}