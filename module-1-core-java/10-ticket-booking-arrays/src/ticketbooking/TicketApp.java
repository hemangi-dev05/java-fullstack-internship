package ticketbooking;

import java.util.Scanner;

public class TicketApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Theatre theatre = new Theatre(5, 6);

    public static void main(String[] args) {
        System.out.println("Welcome to PeakPro Cinema ticket booking.");
        System.out.println("Hall: rows A-E, seats 1-6 per row. Example seat: B3");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-6): ");
            try {
                switch (choice) {
                    case 1 -> bookSeat();
                    case 2 -> cancelSeat();
                    case 3 -> showSeatMap();
                    case 4 -> showAvailable();
                    case 5 -> showBooked();
                    case 6 -> explain();
                    case 0 -> System.out.println("Goodbye!");
                    default -> System.out.println("Please choose a number from 0 to 6.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Not done: " + e.getMessage());
            }
            if (choice >= 1 && choice <= 6) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= TICKET MENU =========");
        System.out.println("  1. Book a seat");
        System.out.println("  2. Cancel a booking");
        System.out.println("  3. Show seat map");
        System.out.println("  4. List available seats");
        System.out.println("  5. List booked seats");
        System.out.println("  6. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("===============================");
    }

    private static void bookSeat() {
        String seat = readText("Which seat? (example: B3): ");
        String name = readText("Passenger name (example: Ravi): ");
        theatre.book(seat, name);
        System.out.println("Booked seat " + seat.trim().toUpperCase() + " for " + name.trim() + ".");
    }

    private static void cancelSeat() {
        String seat = readText("Which seat to cancel? (example: B3): ");
        String name = theatre.cancel(seat);
        System.out.println("Cancelled seat " + seat.trim().toUpperCase() + " (it was booked by " + name + ").");
    }

    private static void showSeatMap() {
        System.out.println();
        System.out.println("          SCREEN");
        System.out.print("     ");
        for (int c = 1; c <= theatre.getSeatsPerRow(); c++) {
            System.out.print("  " + c + " ");
        }
        System.out.println();
        for (int r = 0; r < theatre.getRows(); r++) {
            System.out.print("  " + (char) ('A' + r) + "  ");
            for (int c = 0; c < theatre.getSeatsPerRow(); c++) {
                System.out.print(theatre.getPassenger(r, c) == null ? " [ ]" : " [X]");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("  [ ] available   [X] booked");
        System.out.println("  Available: " + theatre.countAvailable() + "   Booked: " + theatre.countBooked());
    }

    private static void showAvailable() {
        System.out.println();
        System.out.println("Available seats (" + theatre.countAvailable() + "):");
        for (int r = 0; r < theatre.getRows(); r++) {
            StringBuilder line = new StringBuilder("  Row " + (char) ('A' + r) + ":");
            boolean any = false;
            for (int c = 0; c < theatre.getSeatsPerRow(); c++) {
                if (theatre.getPassenger(r, c) == null) {
                    line.append(" ").append(theatre.labelOf(r, c));
                    any = true;
                }
            }
            System.out.println(any ? line.toString() : line + " full");
        }
    }

    private static void showBooked() {
        System.out.println();
        if (theatre.countBooked() == 0) {
            System.out.println("No seats booked yet.");
            return;
        }
        System.out.println("Booked seats (" + theatre.countBooked() + "):");
        for (int r = 0; r < theatre.getRows(); r++) {
            for (int c = 0; c < theatre.getSeatsPerRow(); c++) {
                if (theatre.getPassenger(r, c) != null) {
                    System.out.println("  " + theatre.labelOf(r, c) + " - " + theatre.getPassenger(r, c));
                }
            }
        }
    }

    private static void explain() {
        System.out.println();
        System.out.println("HOW IT WORKS");
        System.out.println("  - seats are stored in a 2D array: String[5][6]");
        System.out.println("  - null = free seat, a name = booked seat");
        System.out.println("  - 'B3' is converted to array position [1][2]");
        System.out.println("  - loops walk through every row and seat to count and print");
        System.out.println("  - booking an already booked seat is rejected (no duplicates)");
        System.out.println("  - cancelling a free seat is rejected");
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
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