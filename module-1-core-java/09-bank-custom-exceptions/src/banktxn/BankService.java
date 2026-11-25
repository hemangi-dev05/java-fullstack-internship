package banktxn;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BankService {

    private final Map<String, Account> accounts = new LinkedHashMap<>();
    private int attempts;
    private int failures;

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account find(String accountNumber) throws AccountNotFoundException {
        String key = clean(accountNumber);
        Account account = accounts.get(key);
        if (account == null) {
            throw new AccountNotFoundException(key);
        }
        return account;
    }

    // none of these methods catch anything: exceptions travel up to the caller
    public void deposit(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException {
        boolean done = false;
        try {
            find(accountNumber).deposit(amount);
            done = true;
        } finally {
            record(done);
        }
    }

    public void withdraw(String accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException {
        boolean done = false;
        try {
            find(accountNumber).withdraw(amount);
            done = true;
        } finally {
            record(done);
        }
    }

    // everything is checked first, so money is never taken out without going in
    public void transfer(String fromNumber, String toNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException {
        boolean done = false;
        try {
            if (clean(fromNumber).equals(clean(toNumber))) {
                throw new IllegalArgumentException("Cannot transfer to the same account.");
            }
            Account from = find(fromNumber);
            Account to = find(toNumber);

            from.checkWithdraw(amount);
            to.checkDeposit(amount);

            from.withdraw(amount);
            to.deposit(amount);
            done = true;
        } finally {
            record(done);
        }
    }

    private void record(boolean done) {
        attempts++;
        if (!done) {
            failures++;
        }
    }

    private static String clean(String text) {
        return text == null ? "" : text.trim().toUpperCase();
    }

    public List<Account> getAccounts() { return new ArrayList<>(accounts.values()); }
    public int getAttempts() { return attempts; }
    public int getFailures() { return failures; }
}