package studentfile;

import java.io.Serializable;

// Serializable is a marker interface: it tells Java this object can be written to a file.
public class Student implements Serializable {

    // used to check that the saved object matches the class when loading
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private String course;
    private double marks;

    public Student(int id, String name, String course, double marks) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
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

    public double getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return String.format("%-6d %-15s %-12s %6.2f", id, name, course, marks);
    }
}