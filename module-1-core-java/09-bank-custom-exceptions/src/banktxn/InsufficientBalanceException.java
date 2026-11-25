package banktxn;

public class InsufficientBalanceException extends Exception {

    private final double balance;
    private final double requested;

    public InsufficientBalanceException(double balance, double requested) {
        super(String.format("Insufficient balance. Available %.2f, requested %.2f.", balance, requested));
        this.balance = balance;
        this.requested = requested;
    }

    public double getShortfall() {
        return requested - balance;
    }
}