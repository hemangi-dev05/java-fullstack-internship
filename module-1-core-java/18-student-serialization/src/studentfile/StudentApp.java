package studentfile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentApp {

    private static final String FILE_NAME = "students.dat";
    private static final Scanner sc = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome! Student records with file serialization.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-7): ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAll();
                case 3 -> searchById();
                case 4 -> deleteById();
                case 5 -> saveToFile();
                case 6 -> loadFromFile();
                case 7 -> explain();
                case 0 -> System.out.println("Goodbye! (Unsaved changes are not stored.)");
                default -> System.out.println("Please choose a number from 0 to 7.");
            }
            if (choice >= 1 && choice <= 7) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= STUDENT RECORDS MENU =========");
        System.out.println("  1. Add a student");
        System.out.println("  2. View all students");
        System.out.println("  3. Search student by ID");
        System.out.println("  4. Delete student by ID");
        System.out.println("  5. Save records to file");
        System.out.println("  6. Load records from file");
        System.out.println("  7. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("========================================");
    }

    private static void addStudent() {
        int id = readInt("Student ID: ");
        if (id <= 0) {
            System.out.println("ID must be a positive number.");
            return;
        }
        if (findById(id) != null) {
            System.out.println("A student with ID " + id + " already exists.");
            return;
        }
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        System.out.print("Course: ");
        String course = sc.nextLine().trim();
        if (course.isEmpty()) {
            System.out.println("Course cannot be empty.");
            return;
        }
        double marks = readDouble("Marks (0-100): ");
        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100.");
            return;
        }
        students.add(new Student(id, name, course, marks));
        System.out.println("Student added (not saved to file yet).");
    }

    private static void viewAll() {
        if (students.isEmpty()) {
            System.out.println("No records to show.");
            return;
        }
        System.out.printf("%-6s %-15s %-12s %6s%n", "ID", "Name", "Course", "Marks");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("Total students: " + students.size());
    }

    private static void searchById() {
        int id = readInt("Enter ID to search: ");
        Student s = findById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id + ".");
        } else {
            System.out.printf("%-6s %-15s %-12s %6s%n", "ID", "Name", "Course", "Marks");
            System.out.println(s);
        }
    }

    private static void deleteById() {
        int id = readInt("Enter ID to delete: ");
        Student s = findById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id + ".");
        } else {
            students.remove(s);
            System.out.println("Deleted student " + s.getName() + " (not saved to file yet).");
        }
    }

    private static void saveToFile() {
        try {
            StudentFileManager.save(students, FILE_NAME);
            System.out.println("Saved " + students.size() + " record(s) to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        if (!StudentFileManager.exists(FILE_NAME)) {
            System.out.println("No saved file found yet. Save some records first.");
            return;
        }
        try {
            students = StudentFileManager.load(FILE_NAME);
            System.out.println("Loaded " + students.size() + " record(s) from " + FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load: " + e.getMessage());
        }
    }

    private static void explain() {
        System.out.println();
        System.out.println("SERIALIZATION");
        System.out.println("  Serialization converts an object into bytes so it can be stored in a file.");
        System.out.println("  Deserialization reads those bytes and rebuilds the object.");
        System.out.println();
        System.out.println("HOW IT IS DONE HERE");
        System.out.println("  Student implements Serializable (a marker interface).");
        System.out.println("  ObjectOutputStream.writeObject() saves the whole list in one call.");
        System.out.println("  ObjectInputStream.readObject() reads it back (needs a cast).");
        System.out.println("  serialVersionUID checks that the saved data matches the class version.");
        System.out.println("  Fields marked transient are skipped during serialization.");
        System.out.println();
        System.out.println("NOTE");
        System.out.println("  Loading replaces the current list, so unsaved changes are lost.");
    }

    private static Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
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

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a number. Try again (example: 75.5).");
            }
        }
    }
}