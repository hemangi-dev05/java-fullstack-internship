# Assignment 18 - File Handling for Student Records using Serialization

## What it does
A menu-driven console app to manage student records. The whole list of students is saved to a file using Java serialization and can be loaded back later.

## Features
    Add, view, search and delete students (ID must be unique)
    Save records to students.dat
    Load records from students.dat
    Input validation (empty name, marks outside 0-100, wrong numbers)

## Concepts
- Serializable interface and serialVersionUID
- ObjectOutputStream and ObjectInputStream
- try-with-resources for closing streams
- Serializing a whole ArrayList in one call
- The transient keyword (fields skipped while saving)

## Files
    src/studentfile/Student.java              -> the student class (Serializable)
    src/studentfile/StudentFileManager.java   -> save and load methods
    src/studentfile/StudentApp.java           -> the menu and input handling

## Run
    cd 18-student-serialization
    javac -d out src\studentfile\*.java
    java -cp out studentfile.StudentApp

## Sample test
    Type: 1, then 101, Asha, Java, 88.5
    Type: 1, then 102, Rohan, SQL, 76
    Type: 5 (save), then 4 and delete 101, then 6 (load)
    Result: student 101 comes back after loading from the file

## What happens inside
1. The student list lives in memory while the program runs.
2. Save writes the whole list to students.dat using writeObject().
3. Load reads the file with readObject() and replaces the list in memory.