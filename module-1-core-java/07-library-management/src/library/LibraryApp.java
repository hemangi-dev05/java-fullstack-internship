package library;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1 -> addToList(new Book());
                    case 2 -> addIdTitleBook();
                    case 3 -> addThreeArgBook();
                    case 4 -> addFullBook();
                    case 5 -> showAll();
                    case 6 -> borrow();
                    case 7 -> giveBack();
                    case 0 -> System.out.println("Goodbye.");
                    default -> System.out.println("Invalid choice, enter 0 to 7.");
                }
            } catch (IllegalArgumentException e) {
                // thrown by the Book constructor when data is not valid
                System.out.println("Invalid book: " + e.getMessage());
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("===== Library Management =====");
        System.out.println("1. Add book  - default constructor");
        System.out.println("2. Add book  - ID and title");
        System.out.println("3. Add book  - ID, title and author");
        System.out.println("4. Add book  - all details");
        System.out.println("5. Show all books");
        System.out.println("6. Borrow a book");
        System.out.println("7. Return a book");
        System.out.println("0. Exit");
    }

    private static void addIdTitleBook() {
        int id = readInt("Book ID: ");
        String title = readText("Title: ");
        addToList(new Book(id, title));
    }

    private static void addThreeArgBook() {
        int id = readInt("Book ID: ");
        String title = readText("Title: ");
        String author = readText("Author: ");
        addToList(new Book(id, title, author));
    }

    private static void addFullBook() {
        int id = readInt("Book ID: ");
        String title = readText("Title: ");
        String author = readText("Author: ");
        boolean available = readYesNo("Available? (y/n): ");
        addToList(new Book(id, title, author, available));
    }

    private static void addToList(Book b) {
        if (findById(b.getBookId()) != null) {
            System.out.println("A book with ID " + b.getBookId() + " already exists.");
            return;
        }
        books.add(b);
        System.out.println("Book added:");
        b.displayDetails();
    }

    private static void showAll() {
        System.out.println("--- All Books (" + books.size() + ") ---");
        if (books.isEmpty()) {
            System.out.println("No books yet. Add one using options 1 to 4.");
            return;
        }
        for (Book b : books) {
            b.displayDetails();
        }
    }

    private static void borrow() {
        Book b = findById(readInt("Book ID to borrow: "));
        if (b == null) {
            System.out.println("Book not found.");
        } else if (b.borrowBook()) {
            System.out.println("Borrowed: " + b.getTitle());
        } else {
            System.out.println(b.getTitle() + " is already borrowed.");
        }
    }

    private static void giveBack() {
        Book b = findById(readInt("Book ID to return: "));
        if (b == null) {
            System.out.println("Book not found.");
        } else if (b.returnBook()) {
            System.out.println("Returned: " + b.getTitle());
        } else {
            System.out.println(b.getTitle() + " was not borrowed.");
        }
    }

    private static Book findById(int id) {
        for (Book b : books) {
            if (b.getBookId() == id) {
                return b;
            }
        }
        return null;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            String s = readText(prompt).trim().toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            System.out.println("Type y or n.");
        }
    }
}