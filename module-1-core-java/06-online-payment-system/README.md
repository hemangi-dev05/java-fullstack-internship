# 06 - Online Payment System

Assignment 6 of the Java Full Stack Internship at PeakProSys Solutions, Pune.

## Overview

A console app where the user pays by Credit Card, UPI or Cash on Delivery, asks for refunds and checks the transaction history.

The goal is to show a class implementing **multiple interfaces**. Each interface is one small ability, and a payment method implements only the abilities it really has.

## Interfaces

| Interface | Ability | Methods |
| --- | --- | --- |
| `Payable` | can take a payment | `getName()`, `pay(amount)` |
| `Refundable` | can give money back | `refund(amount)` |
| `Verifiable` | is protected by a PIN | `verify(pin)` |

| Class | Implements |
| --- | --- |
| `CreditCard` | `Payable`, `Refundable`, `Verifiable` |
| `Upi` | `Payable`, `Refundable`, `Verifiable` |
| `CashOnDelivery` | `Payable` only |

All methods are kept in one `Payable[]` array. `Main` checks `instanceof Verifiable` before asking for a PIN and `instanceof Refundable` before a refund. That is why Cash on Delivery never asks for a PIN and cannot be refunded.

## Demo data

| Method | Details |
| --- | --- |
| Credit Card | PIN `1234`, credit limit 50000 |
| UPI | `hemangi@upi`, PIN `4321`, bank balance 20000 |
| Cash on Delivery | no PIN, no balance |

## Validation

- Amount must be greater than 0
- Wrong PIN cancels the payment
- Credit limit and UPI balance cannot be exceeded
- Refund cannot be more than the amount paid
- Cash on Delivery refund is blocked
- Text instead of a number asks again

## Files

Package: `com.internship.module1.payment`

- `Payable.java`, `Refundable.java`, `Verifiable.java`
- `CreditCard.java`, `Upi.java`, `CashOnDelivery.java`
- `Main.java`

## How to run

From this folder:

```
javac -d out src/com/internship/module1/payment/*.java
java -cp out com.internship.module1.payment.Main
```

Or open the project in IntelliJ IDEA and run `Main`. Built with JDK 17.

## Sample

```
Enter choice: 1
1. Credit Card
2. UPI (hemangi@upi)
3. Cash on Delivery
Select method: 1
Enter amount: 2000
Enter PIN: 1234
Paid 2000.00 by Credit Card. Available limit: 48000.00

Enter choice: 2
Select method: 3
Cash on Delivery does not support refunds.
```
## Screenshots

### Output

**Credit card payment**

Option 1, credit card, correct PIN. The amount is paid and the available limit goes down.

![Credit card payment](<screenshots/card.png>)

**UPI payment**

Option 1, UPI, correct PIN. The amount is paid and the bank balance goes down.

![UPI payment](<screenshots/UPI.png>)

**Cash on delivery**

Option 1, cash on delivery. No PIN is asked because this class does not implement Verifiable.

![Cash on delivery](<screenshots/COD.png>)

**Refund**

Option 2, credit card. The amount is given back and the limit is restored because the class implements Refundable.

![Refund](<screenshots/refund.png>)

**Refund not supported**

Option 2, cash on delivery. The refund is blocked because this class does not implement Refundable.

![Refund not supported](<screenshots/no COD refund.png>)

**Transaction history**

Option 3 lists every payment and refund done in this run.

![Transaction history](<screenshots/history.png>)

**Credit limit exceeded**

A payment above the available credit limit is rejected with the available limit shown.

![Credit limit exceeded](<screenshots/credit limit exceed.png>)
