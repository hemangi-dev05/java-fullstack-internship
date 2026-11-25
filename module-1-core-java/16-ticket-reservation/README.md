# Assignment 16 - Multithreaded Ticket Reservation

## What it does
A menu app with 10 seats. Many threads can book seats at the same time, and synchronized makes sure a seat is never sold twice. A demo shows what goes wrong without synchronized.

## Features
    Book a specific seat or any free seat | Cancel (only the owner can cancel)
    Rush: up to 100 customer threads book together
    Many threads fight for one seat       | Unsafe vs safe race condition demo

## Concepts
- Thread, Runnable (lambda), start() and join()
- Race condition (check-then-act)
- synchronized methods
- CountDownLatch to start threads together
- AtomicInteger and ConcurrentHashMap for counting across threads

## Files
    src/ticketing/BookingSystem.java   -> seats, all methods synchronized
    src/ticketing/TicketCounter.java   -> unsafe and safe counter for the demo
    src/ticketing/ReservationApp.java  -> the menu and the thread simulations

## Run
    cd 16-ticket-reservation
    javac -d out src\ticketing\*.java
    java -cp out ticketing.ReservationApp

## Sample test
    Type: 5, 20 (on a fresh start)
    Result: Booked 10, Sold out 10, Duplicate seats 0, Check PASSED

    Type: 6, 5, 10
    Result: BOOKED 1, ALREADY_TAKEN 9

    Type: 7
    Result: UNSAFE usually oversold, SAFE sold 5 and CORRECT

## What happens inside
1. Each customer is a thread that calls booking.bookAny() or booking.book().
2. The methods are synchronized, so one thread at a time checks and books.
3. The rush counts results with AtomicInteger and checks that no seat was given twice.
4. The demo repeats the same race with and without synchronized.