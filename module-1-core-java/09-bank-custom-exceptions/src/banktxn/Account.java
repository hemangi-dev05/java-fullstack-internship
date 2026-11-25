package banktxn;

public class Account {

    private static final double MAX_AMOUNT = 1_000_000;

    private final String accountNumber;
    private final String holderName;
    private double balance;

    public Account(String accountNumber, String holderName, double openingBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty.");
        }
        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException("Holder name cannot be empty.");
        }
        if (Double.isNaN(openingBalance) || openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative.");
        }
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = openingBalance;
    }

    // check methods only validate, they never change the balance
    public void checkDeposit(double amount) throws InvalidAmountException {
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new InvalidAmountException("Amount must be a real number.", amount);
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.", amount);
        }
        if (amount > MAX_AMOUNT) {
            throw new InvalidAmountException(
                    String.format("Amount cannot exceed %.0f per transaction.", MAX_AMOUNT), amount);
        }
    }

    public void checkWithdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        checkDeposit(amount);
        if (amount > balance) {
            throw new InsufficientBalanceException(balance, amount);
        }
    }

    public void deposit(double amount) throws InvalidAmountException {
        checkDeposit(amount);
        balance += amount;
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        checkWithdraw(amount);
        balance -= amount;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }
}