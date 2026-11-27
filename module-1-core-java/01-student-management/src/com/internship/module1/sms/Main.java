package com.internship.module1.sms;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        int choice = -1;

        while (choice != 0) {
            System.out.println();
            System.out.println("===== Student Management System =====");
            System.out.println("1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search by ID");
            System.out.println("4. Search by name");
            System.out.println("5. Update student");
            System.out.println("6. Delete student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAll();
                    break;
                case 3:
                    searchById();
                    break;
                case 4:
                    searchByName();
                    break;
                case 5:
                    updateStudent();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addStudent() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Course: ");
        String course = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        String error = manager.validate(name, course, age, email);
        if (!error.equals("")) {
            System.out.println("Error: " + error);
        } else if (manager.findById(id) != null) {
            System.out.println("Error: A student with this ID already exists.");
        } else if (manager.emailExists(email, -1)) {
            System.out.println("Error: This email is already registered.");
        } else {
            manager.addStudent(new Student(id, name, course, age, email));
            System.out.println("Student added successfully.");
        }
    }

    static void viewAll() {
        ArrayList<Student> list = manager.getAll();
        if (list.size() == 0) {
            System.out.println("No students found.");
        } else {
            for (Student s : list) {
                System.out.println(s);
            }
        }
    }

    static void searchById() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        Student s = manager.findById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id);
        } else {
            System.out.println(s);
        }
    }

    static void searchByName() {
        System.out.print("Enter name to search: ");
        String keyword = sc.nextLine().trim();
        ArrayList<Student> result = manager.searchByName(keyword);

        if (result.size() == 0) {
            System.out.println("No matching students.");
        } else {
            for (Student s : result) {
                System.out.println(s);
            }
        }
    }

    static void updateStudent() {
        System.out.print("ID of student to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        Student s = manager.findById(id);
        if (s == null) {
            System.out.println("No student found with ID " + id);
            return;
        }

        System.out.print("New name: ");
        String name = sc.nextLine().trim();
        System.out.print("New course: ");
        String course = sc.nextLine().trim();
        System.out.print("New age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("New email: ");
        String email = sc.nextLine().trim();

        String error = manager.validate(name, course, age, email);
        if (!error.equals("")) {
            System.out.println("Error: " + error);
        } else if (manager.emailExists(email, id)) {
            System.out.println("Error: This email is already registered.");
        } else {
            s.setName(name);
            s.setCourse(course);
            s.setAge(age);
            s.setEmail(email);
            System.out.println("Student updated successfully.");
        }
    }

    static void deleteStudent() {
        System.out.print("ID of student to delete: ");
        int id = sc.nextInt();
        sc.nextLine();
        if (manager.deleteStudent(id)) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("No student found with ID " + id);
        }
    }
}