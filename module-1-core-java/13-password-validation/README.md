# Assignment 13 - Password Validation using Regular Expressions

## What it does
A menu app that checks a password against 6 rules and tells you exactly which rules are broken. Option 3 runs 10 sample passwords so you can see every case at once.

## Rules
    8 to 20 characters | one uppercase | one lowercase
    one digit | one special character | no spaces

## Concepts
- Pattern and Matcher (java.util.regex)
- matches() vs find()
- Character classes like [A-Z], [0-9], [^A-Za-z0-9\s]
- Lookaheads (?=.*X) in one combined pattern

## Files
    src/passwordcheck/PasswordValidator.java -> the regex rules
    src/passwordcheck/PasswordApp.java       -> the menu

## Run
    cd 13-password-validation
    javac -d out src\passwordcheck\*.java
    java -cp out passwordcheck.PasswordApp

## Sample test
    Type: 1, Peak@2024
    Result: VALID

    Type: 1, peak
    Result: INVALID with 4 problems (length, uppercase, digit, special)

    Type: 1, Peak @2024
    Result: INVALID - Spaces are not allowed.

    Type: 3
    Result: all 10 samples, 3 valid and 7 invalid

## What happens inside
1. Each rule is a compiled Pattern. The validator tests the password against each one.
2. matches() is used for length (whole text), find() for the other rules (anywhere in the text).
3. Every broken rule adds a clear message to a list. An empty list means valid.
4. The combined lookahead pattern checks all rules at once and must agree with the list.