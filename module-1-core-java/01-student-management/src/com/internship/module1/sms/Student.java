package com.internship.module1.sms;

public class Student {
    private int id;
    private String name;
    private String course;
    private int age;
    private String email;

    public Student(int id, String name, String course, int age, String email) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Course: " + course
                + " | Age: " + age + " | Email: " + email;
    }
}