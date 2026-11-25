package library;

public class Book {

    private final int bookId;
    private final String title;
    private final String author;
    private boolean available;

    public Book() {
        this(0, "Unknown Title", "Unknown Author", true);
    }

    public Book(int bookId, String title) {
        this(bookId, title, "Unknown Author", true);
    }

    public Book(int bookId, String title, String author) {
        this(bookId, title, author, true);
    }

    // all other constructors end up here, so validation is only written once
    public Book(int bookId, String title, String author, boolean available) {
        if (bookId < 0) {
            throw new IllegalArgumentException("Book ID cannot be negative.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }

    public boolean borrowBook() {
        if (!available) {
            return false;
        }
        available = false;
        return true;
    }

    public boolean returnBook() {
        if (available) {
            return false;
        }
        available = true;
        return true;
    }

    public void displayDetails() {
        System.out.println("Book ID   : " + bookId);
        System.out.println("Title     : " + title);
        System.out.println("Author    : " + author);
        System.out.println("Available : " + (available ? "Yes" : "No"));
        System.out.println("-----------------------------");
    }
}