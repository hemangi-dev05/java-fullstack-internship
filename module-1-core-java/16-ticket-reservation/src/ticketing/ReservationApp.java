package ticketing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationApp {

    private static final int SEATS = 10;
    private static final int MAX_NAME = 20;
    private static final int MAX_CUSTOMERS = 100;

    private static final Scanner sc = new Scanner(System.in);
    private static final BookingSystem booking = new BookingSystem(SEATS);

    public static void main(String[] args) {
        System.out.println("Welcome! Multithreaded ticket reservation (" + SEATS + " seats).");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-9): ");
            switch (choice) {
                case 1 -> showSeats();
                case 2 -> bookSpecific();
                case 3 -> bookAny();
                case 4 -> cancelSeat();
                case 5 -> rush();
                case 6 -> sameSeat();
                case 7 -> raceDemo();
                case 8 -> {
                    booking.reset();
                    System.out.println("All seats are free again.");
                }
                case 9 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 9.");
            }
            if (choice >= 1 && choice <= 9) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= TICKET MENU =========");
        System.out.println("  1. Show seat map");
        System.out.println("  2. Book a specific seat");
        System.out.println("  3. Book any free seat");
        System.out.println("  4. Cancel a booking");
        System.out.println("  5. Rush: many customers book at the same time");
        System.out.println("  6. Many customers fight for ONE seat");
        System.out.println("  7. Race condition demo (unsafe vs safe)");
        System.out.println("  8. Reset all seats");
        System.out.println("  9. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("===============================");
    }

    private static void showSeats() {
        String[] owners = booking.snapshot();
        int booked = 0;
        for (String owner : owners) {
            if (owner != null) {
                booked++;
            }
        }
        System.out.println();
        System.out.println("SEAT MAP (" + booked + " of " + owners.length + " booked)");
        for (int i = 0; i < owners.length; i++) {
            System.out.printf("  Seat %2d: %s%n", i + 1, owners[i] == null ? "free" : owners[i]);
        }
    }

    private static void bookSpecific() {
        String name = readText("Customer name: ");
        int seat = readInt("Seat number (1-" + SEATS + "): ");
        if (!validName(name)) {
            return;
        }
        switch (booking.book(name, seat)) {
            case BOOKED -> System.out.println("Booked! " + name + " has seat " + seat + ".");
            case ALREADY_TAKEN -> System.out.println("Seat " + seat + " is already taken.");
            default -> System.out.println("Invalid seat number. Choose 1 to " + SEATS + ".");
        }
    }

    private static void bookAny() {
        String name = readText("Customer name: ");
        if (!validName(name)) {
            return;
        }
        int seat = booking.bookAny(name);
        if (seat == -1) {
            System.out.println("Sorry, all seats are sold out.");
        } else {
            System.out.println(name + " got seat " + seat + ".");
        }
    }

    private static void cancelSeat() {
        String name = readText("Customer name: ");
        int seat = readInt("Seat number to cancel (1-" + SEATS + "): ");
        if (!validName(name)) {
            return;
        }
        switch (booking.cancel(name, seat)) {
            case CANCELLED -> System.out.println("Cancelled seat " + seat + " for " + name + ".");
            case NOT_BOOKED -> System.out.println("Seat " + seat + " is not booked.");
            case NOT_OWNER -> System.out.println("Seat " + seat + " is booked by someone else.");
            default -> System.out.println("Invalid seat number. Choose 1 to " + SEATS + ".");
        }
    }

    // N threads call bookAny at the same moment
    private static void rush() {
        int customers = readInt("Number of customers (1-" + MAX_CUSTOMERS + "): ");
        if (!validCount(customers)) {
            return;
        }
        int freeBefore = booking.availableCount();
        CountDownLatch go = new CountDownLatch(1);
        AtomicInteger booked = new AtomicInteger();
        AtomicInteger soldOut = new AtomicInteger();
        AtomicInteger duplicates = new AtomicInteger();
        Set<Integer> seatsGiven = ConcurrentHashMap.newKeySet();
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= customers; i++) {
            String name = "Customer-" + i;
            Thread t = new Thread(() -> {
                try {
                    go.await();   // every thread waits here, then they all start together
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                int seat = booking.bookAny(name);
                if (seat == -1) {
                    soldOut.incrementAndGet();
                } else {
                    booked.incrementAndGet();
                    if (!seatsGiven.add(seat)) {   // add() is false if the seat was given before
                        duplicates.incrementAndGet();
                    }
                }
            }, name);
            threads.add(t);
            t.start();
        }
        go.countDown();
        joinAll(threads);

        boolean ok = booked.get() == Math.min(customers, freeBefore) && duplicates.get() == 0;
        System.out.println();
        System.out.println("RUSH RESULT");
        System.out.println("  Customers         : " + customers);
        System.out.println("  Seats free before : " + freeBefore);
        System.out.println("  Booked            : " + booked.get());
        System.out.println("  Sold out          : " + soldOut.get());
        System.out.println("  Duplicate seats   : " + duplicates.get());
        System.out.println("  Seats free now    : " + booking.availableCount());
        System.out.println("  Check             : " + (ok ? "PASSED" : "FAILED"));
    }

    // N threads all try to book the same seat, only one can win
    private static void sameSeat() {
        int seat = readInt("Seat number: ");
        int customers = readInt("Number of customers (1-" + MAX_CUSTOMERS + "): ");
        if (!validCount(customers)) {
            return;
        }
        CountDownLatch go = new CountDownLatch(1);
        ConcurrentHashMap<BookingSystem.Result, Integer> counts = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= customers; i++) {
            String name = "Customer-" + i;
            Thread t = new Thread(() -> {
                try {
                    go.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                counts.merge(booking.book(name, seat), 1, Integer::sum);
            }, name);
            threads.add(t);
            t.start();
        }
        go.countDown();
        joinAll(threads);

        System.out.println();
        System.out.println("SAME SEAT RESULT (seat " + seat + ", " + customers + " customers)");
        for (BookingSystem.Result r : BookingSystem.Result.values()) {
            Integer count = counts.get(r);
            if (count != null) {
                System.out.println("  " + r + " : " + count);
            }
        }
    }

    private static void raceDemo() {
        int tickets = 5;
        int buyers = 20;
        System.out.println();
        System.out.println("RACE CONDITION DEMO: " + tickets + " tickets, " + buyers + " buyers at the same time");

        TicketCounter unsafeCounter = new TicketCounter(tickets);
        int soldUnsafe = stampede(unsafeCounter, buyers, false);
        String unsafeVerdict = soldUnsafe > tickets
                ? "OVERSOLD by " + (soldUnsafe - tickets)
                : "no overselling this time (run it again)";
        System.out.println("UNSAFE (no synchronized): sold " + soldUnsafe
                + ", remaining " + unsafeCounter.getRemaining() + "  -> " + unsafeVerdict);

        TicketCounter safeCounter = new TicketCounter(tickets);
        int soldSafe = stampede(safeCounter, buyers, true);
        String safeVerdict = (soldSafe == tickets && safeCounter.getRemaining() == 0) ? "CORRECT" : "WRONG";
        System.out.println("SAFE   (synchronized)   : sold " + soldSafe
                + ", remaining " + safeCounter.getRemaining() + "  -> " + safeVerdict);
    }

    // starts many buyer threads together and returns how many purchases succeeded
    private static int stampede(TicketCounter counter, int buyers, boolean safe) {
        CountDownLatch go = new CountDownLatch(1);
        AtomicInteger sold = new AtomicInteger();
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= buyers; i++) {
            Thread t = new Thread(() -> {
                try {
                    go.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                boolean ok = safe ? counter.buySafe() : counter.buyUnsafe();
                if (ok) {
                    sold.incrementAndGet();
                }
            });
            threads.add(t);
            t.start();
        }
        go.countDown();
        joinAll(threads);
        return sold.get();
    }

    private static void joinAll(List<Thread> threads) {
        for (Thread t : threads) {
            try {
                t.join();   // wait until this thread has finished
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private static boolean validName(String name) {
        if (name.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            return false;
        }
        if (name.length() > MAX_NAME) {
            System.out.println("Customer name is too long (max " + MAX_NAME + " characters).");
            return false;
        }
        return true;
    }

    private static boolean validCount(int n) {
        if (n < 1 || n > MAX_CUSTOMERS) {
            System.out.println("Enter a number from 1 to " + MAX_CUSTOMERS + ".");
            return false;
        }
        return true;
    }

    private static void explain() {
        System.out.println();
        System.out.println("THREADS");
        System.out.println("  new Thread(runnable).start() runs code at the same time as other threads");
        System.out.println("  thread.join() makes the main thread wait until that thread is finished");
        System.out.println("  CountDownLatch makes all threads wait, then start together");
        System.out.println();
        System.out.println("RACE CONDITION");
        System.out.println("  'if (seat is free) then book it' is two steps. Two threads can both see");
        System.out.println("  'free' before either one books, so the seat is sold twice (or tickets oversold).");
        System.out.println();
        System.out.println("synchronized");
        System.out.println("  Only one thread at a time can run synchronized methods of the same object.");
        System.out.println("  Check + update become one step, so a seat can be booked only once.");
        System.out.println();
        System.out.println("AtomicInteger / ConcurrentHashMap");
        System.out.println("  Thread-safe helpers used here to count results from many threads.");
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a whole number. Try again (example: 1).");
            }
        }
    }
}