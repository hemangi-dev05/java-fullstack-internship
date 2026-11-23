package com.internship.module1.banking;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private int nextAccountNumber = 1001;

    public String validateNewAccount(String name, double openingBalance) {
        if (name.length() < 2) {
            return "Name must have at least 2 characters.";
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!Character.isLetter(c) && c != ' ') {
                return "Name can contain only letters and spaces.";
            }
        }
        String error = Account.checkAmount(openingBalance);
        if (!error.equals("")) {
            return error;
        }
        if (openingBalance < Account.MIN_BALANCE) {
            return "Opening balance must be at least " + Account.MIN_BALANCE + ".";
        }
        if (openingBalance > Account.MAX_DEPOSIT) {
            return "Opening balance cannot be more than " + Account.MAX_DEPOSIT + ".";
        }
        return "";
    }

    public Account createAccount(String name, double openingBalance) {
        Account a = new Account(nextAccountNumber, name, openingBalance);
        nextAccountNumber++;
        accounts.add(a);
        return a;
    }

    public Account findAccount(int accountNumber) {
        for (Account a : accounts) {
            if (a.getAccountNumber() == accountNumber) {
                return a;
            }
        }
        return null;
    }

    public ArrayList<Account> getAll() {
        return accounts;
    }

    public String transfer(int fromNo, int toNo, double amount) {
        if (fromNo == toNo) {
            return "Cannot transfer to the same account.";
        }
        Account from = findAccount(fromNo);
        Account to = findAccount(toNo);
        if (from == null) {
            return "Source account not found.";
        }
        if (to == null) {
            return "Destination account not found.";
        }
        String error = from.withdraw(amount, "Transfer to " + toNo);
        if (!error.equals("")) {
            return error;
        }
        to.deposit(amount, "Transfer from " + fromNo);
        return "";
    }
}