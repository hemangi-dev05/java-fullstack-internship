# Assignment 9 - Custom Exceptions for Bank Transactions

## What it does
A bank menu app with 3 sample accounts. You can deposit to an account, withdraw from an account, and transfer from one account to another. Wrong actions are rejected with custom exceptions.

## Concepts
- Custom checked exceptions
- try / catch / finally
- Exception propagation (throws)
- Validation before changing data

## Custom exceptions
    InvalidAmountException       -> zero, negative, above 1000000, NaN
    InsufficientBalanceException -> amount is more than the balance
    AccountNotFoundException     -> account number does not exist

## Files
    src/banktxn/Account.java       -> balance and validation rules
    src/banktxn/BankService.java   -> finds accounts, deposit, withdraw, transfer
    src/banktxn/BankApp.java       -> the menu
    src/banktxn/*Exception.java    -> the 3 custom exceptions

## Run
    cd 09-bank-custom-exceptions
    javac -d out src\banktxn\*.java
    java -cp out banktxn.BankApp

## Starting accounts
    ACC1001  Ravi Kumar     5000.00
    ACC1002  Priya Sharma   3000.00
    ACC1003  Amit Patil     1000.00

## Sample test
    Type: 1, ACC1001, 2000
    Result: Deposit successful, ACC1001 balance 7000.00

    Type: 3, ACC1001, ACC1003, 1500
    Result: Transfer successful, ACC1001 5500.00, ACC1003 2500.00

    Type: 3, ACC1003, ACC1002, 99999
    Result: Transfer rejected: Insufficient balance (shows how much short)

    Type: 3, ACC1001, ACC1001, 100
    Result: Cannot transfer to the same account.

    Type: 1, ACC9999, 100
    Result: No account found with number ACC9999.

    Type: 1, ACC1001, -50
    Result: Amount must be greater than zero.

## What happens inside
1. Account checks the rules and throws a custom exception if something is wrong.
2. BankService does not catch anything. The exception travels up to BankApp (this is propagation, menu 5 shows the path).
3. BankApp catches it and prints a clear message.
4. finally always runs: the app prints the balance after every action, and the service counts attempts and failures.
5. Transfer checks both sides first (enough balance, valid amount) and only then moves the money, so money never disappears if something fails.