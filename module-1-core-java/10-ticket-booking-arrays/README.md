# Assignment 10 - Ticket Booking (Arrays and Loops)

## What it does
A cinema booking menu. The hall has 5 rows (A-E) with 6 seats each. You can book a seat, cancel a booking, and see which seats are free or taken. Booking the same seat twice is blocked.

## Concepts
- 2D array (String[5][6])
- for loops to print, count and search
- Converting text like B3 into array positions
- Input validation with exceptions

## Files
    src/ticketbooking/Theatre.java   -> the seat array, book and cancel rules
    src/ticketbooking/TicketApp.java -> the menu

## Run
    cd 10-ticket-booking-arrays
    javac -d out src\ticketbooking\*.java
    java -cp out ticketbooking.TicketApp

## Sample test
    Type: 1, A1, Ravi
    Result: Booked seat A1 for Ravi.

    Type: 1, A1, Amit
    Result: Not done: Seat A1 is already booked by Ravi.

    Type: 1, Z9, Amit
    Result: Not done: Seat Z9 does not exist.

    Type: 2, A1
    Result: Cancelled seat A1 (it was booked by Ravi).

    Type: 2, A1 again
    Result: Not done: Seat A1 is not booked, nothing to cancel.

    Type: 3
    Result: Seat map with [ ] free and [X] booked seats

## What happens inside
1. The array starts with all null, which means every seat is free.
2. Booking converts B3 to [1][2], checks the cell is null, then stores the name.
3. If the cell already has a name, an exception stops the duplicate booking.
4. Cancel puts null back into the cell.
5. Seat map, available list and booked list are all loops over the array.