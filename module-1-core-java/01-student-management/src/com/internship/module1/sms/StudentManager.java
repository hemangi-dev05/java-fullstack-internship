package com.internship.module1.sms;

import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<Student>();

    // returns "" if everything is fine, otherwise the error message
    public String validate(String name, String course, int age, String email) {
        if (name.equals("")) {
            return "Name cannot be empty.";
        }
        if (course.equals("")) {
            return "Course cannot be empty.";
        }
        if (age < 15 || age > 100) {
            return "Age must be between 15 and 100.";
        }
        if (!email.contains("@") || !email.contains(".")) {
            return "Invalid email.";
        }
        return "";
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public ArrayList<Student> getAll() {
        return students;
    }

    public Student findById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public ArrayList<Student> searchByName(String keyword) {
        ArrayList<Student> result = new ArrayList<Student>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    // ignoreId lets a student keep their own email when updating (use -1 when adding)
    public boolean emailExists(String email, int ignoreId) {
        for (Student s : students) {
            if (s.getId() != ignoreId && s.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        Student s = findById(id);
        if (s == null) {
            return false;
        }
        students.remove(s);
        return true;
    }
}