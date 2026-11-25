package banktxn;

import java.util.Scanner;

public class BankApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final BankService service = new BankService();

    public static void main(String[] args) {
        service.addAccount(new Account("ACC1001", "Ravi Kumar", 5000));
        service.addAccount(new Account("ACC1002", "Priya Sharma", 3000));
        service.addAccount(new Account("ACC1003", "Amit Patil", 1000));

        System.out.println("Welcome to PeakPro Bank (custom exception demo).");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-6): ");
            switch (choice) {
                case 1 -> deposit();
                case 2 -> withdraw();
                case 3 -> transfer();
                case 4 -> showAccounts(true);
                case 5 -> propagationDemo();
                case 6 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 6.");
            }
            if (choice >= 1 && choice <= 6) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=========== BANK MENU ===========");
        System.out.println("  1. Deposit money   (into an account)");
        System.out.println("  2. Withdraw money  (from an account)");
        System.out.println("  3. Transfer money  (from one account to another)");
        System.out.println("  4. Show all accounts and transaction count");
        System.out.println("  5. Exception propagation demo");
        System.out.println("  6. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("=================================");
    }

    private static void deposit() {
        showAccounts(false);
        String to = readText("Deposit TO which account? (example: ACC1001): ");
        double amount = readDouble("Enter amount to deposit (example: 2000): ");
        try {
            service.deposit(to, amount);
            System.out.println("Deposit successful: +" + money(amount) + " into " + to.trim().toUpperCase());
        } catch (AccountNotFoundException e) {
            System.out.println("Deposit rejected: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("Deposit rejected: " + e.getMessage());
        } finally {
            printBalance(to);
        }
    }

    private static void withdraw() {
        showAccounts(false);
        String from = readText("Withdraw FROM which account? (example: ACC1001): ");
        double amount = readDouble("Enter amount to withdraw (example: 500): ");
        try {
            service.withdraw(from, amount);
            System.out.println("Withdrawal successful: -" + money(amount) + " from " + from.trim().toUpperCase());
        } catch (InsufficientBalanceException e) {
            System.out.println("Withdrawal rejected: " + e.getMessage());
            System.out.println("You are short by " + money(e.getShortfall()) + ".");
        } catch (AccountNotFoundException | InvalidAmountException e) {
            System.out.println("Withdrawal rejected: " + e.getMessage());
        } finally {
            printBalance(from);
        }
    }

    private static void transfer() {
        showAccounts(false);
        String from = readText("Transfer FROM which account? (example: ACC1001): ");
        String to = readText("Transfer TO which account?   (example: ACC1002): ");
        double amount = readDouble("Enter amount to transfer (example: 1500): ");
        try {
            service.transfer(from, to, amount);
            System.out.println("Transfer successful: " + money(amount) + " moved from "
                    + from.trim().toUpperCase() + " to " + to.trim().toUpperCase());
        } catch (InsufficientBalanceException e) {
            System.out.println("Transfer rejected: " + e.getMessage());
            System.out.println("You are short by " + money(e.getShortfall()) + ".");
        } catch (AccountNotFoundException | InvalidAmountException | IllegalArgumentException e) {
            System.out.println("Transfer rejected: " + e.getMessage());
        } finally {
            printBalance(from);
            printBalance(to);
        }
    }

    private static void showAccounts(boolean withStats) {
        System.out.println();
        System.out.println("Accounts:");
        for (Account a : service.getAccounts()) {
            System.out.printf("  %-8s %-14s %12s%n", a.getAccountNumber(), a.getHolderName(), money(a.getBalance()));
        }
        if (withStats) {
            System.out.println("Attempts: " + service.getAttempts() + "   Failed: " + service.getFailures());
        }
        System.out.println();
    }

    // prints the balance if the account exists, otherwise stays quiet
    private static void printBalance(String accountNumber) {
        try {
            Account a = service.find(accountNumber);
            System.out.println("Balance of " + a.getAccountNumber() + ": " + money(a.getBalance()));
        } catch (AccountNotFoundException e) {
            // nothing to show for an unknown account
        }
    }

    // thrown in Account, passes through BankService, and is finally caught here
    private static void propagationDemo() {
        Account first = service.getAccounts().get(0);
        double amount = first.getBalance() + 1;
        System.out.println("Trying to withdraw " + money(amount) + " from "
                + first.getAccountNumber() + " (more than its balance)...");
        try {
            service.withdraw(first.getAccountNumber(), amount);
        } catch (InsufficientBalanceException | InvalidAmountException | AccountNotFoundException e) {
            System.out.println("Caught in BankApp: " + e.getMessage());
            System.out.println("Path of the exception, from where it was thrown upwards:");
            for (StackTraceElement el : e.getStackTrace()) {
                if (el.getClassName().startsWith("banktxn")) {
                    System.out.println("  " + el.getClassName() + "." + el.getMethodName());
                }
            }
        }
    }

    private static void explain() {
        System.out.println();
        System.out.println("CUSTOM EXCEPTIONS (all extend Exception, so they are checked)");
        System.out.println("  - InvalidAmountException: zero, negative, too large, NaN or Infinity");
        System.out.println("  - InsufficientBalanceException: amount is more than the balance");
        System.out.println("  - AccountNotFoundException: the account number does not exist");
        System.out.println();
        System.out.println("TRANSFER");
        System.out.println("  - same account is rejected, both accounts must exist");
        System.out.println("  - withdrawal and deposit are both checked BEFORE money moves");
        System.out.println();
        System.out.println("TRY / CATCH / FINALLY");
        System.out.println("  - finally runs always: here it prints balances and counts attempts");
        System.out.println();
        System.out.println("PROPAGATION");
        System.out.println("  - Account throws, BankService does not catch it, BankApp catches it");
    }

    private static String money(double value) {
        return String.format("%.2f", value);
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a whole number. Try again (example: 1).");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a number. Try again (example: 2500.50).");
            }
        }
    }
}