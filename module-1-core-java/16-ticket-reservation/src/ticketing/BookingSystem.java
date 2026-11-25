package ticketing;

import java.util.Arrays;

public class BookingSystem {

    public enum Result { BOOKED, ALREADY_TAKEN, INVALID_SEAT, SOLD_OUT, CANCELLED, NOT_BOOKED, NOT_OWNER }

    private final String[] owners;   // index 0 is seat 1, null means the seat is free

    public BookingSystem(int seats) {
        if (seats < 1) {
            throw new IllegalArgumentException("Seats must be at least 1.");
        }
        owners = new String[seats];
    }

    public int capacity() {
        return owners.length;
    }

    // synchronized: only ONE thread at a time can run any synchronized method of this object,
    // so "check if free" and "mark as booked" can never be split by another thread
    public synchronized Result book(String customer, int seat) {
        if (seat < 1 || seat > owners.length) {
            return Result.INVALID_SEAT;
        }
        if (owners[seat - 1] != null) {
            return Result.ALREADY_TAKEN;
        }
        owners[seat - 1] = customer;
        return Result.BOOKED;
    }

    // gives the first free seat and returns its number, or -1 when sold out
    public synchronized int bookAny(String customer) {
        for (int i = 0; i < owners.length; i++) {
            if (owners[i] == null) {
                owners[i] = customer;
                return i + 1;
            }
        }
        return -1;
    }

    // only the person who booked the seat can cancel it
    public synchronized Result cancel(String customer, int seat) {
        if (seat < 1 || seat > owners.length) {
            return Result.INVALID_SEAT;
        }
        String owner = owners[seat - 1];
        if (owner == null) {
            return Result.NOT_BOOKED;
        }
        if (!owner.equalsIgnoreCase(customer)) {
            return Result.NOT_OWNER;
        }
        owners[seat - 1] = null;
        return Result.CANCELLED;
    }

    public synchronized int availableCount() {
        int free = 0;
        for (String owner : owners) {
            if (owner == null) {
                free++;
            }
        }
        return free;
    }

    public synchronized String[] snapshot() {
        return owners.clone();
    }

    public synchronized void reset() {
        Arrays.fill(owners, null);
    }
}