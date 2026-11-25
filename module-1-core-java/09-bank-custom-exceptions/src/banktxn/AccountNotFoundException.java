package banktxn;

public class AccountNotFoundException extends Exception {

    private final String accountNumber;

    public AccountNotFoundException(String accountNumber) {
        super("No account found with number " + accountNumber + ".");
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}