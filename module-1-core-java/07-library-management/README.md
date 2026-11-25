# Assignment 7 - Library Management (Constructors)

## What it does
A menu app where you add books in 4 different ways. Each way calls a different constructor of the Book class. You can then list, borrow and return books.

## Concepts
- Default constructor
- Parameterized constructor
- Constructor overloading
- Constructor chaining with this(...)
- Validation with IllegalArgumentException

## Files
    src/library/Book.java        -> the class with 4 constructors
    src/library/LibraryApp.java  -> the menu

## Run
    cd 07-library-management
    javac -d out src\library\*.java
    java -cp out library.LibraryApp

## Menu to constructor
    1 -> new Book()                          (all default values)
    2 -> new Book(id, title)                 (author = Unknown Author)
    3 -> new Book(id, title, author)         (available = true)
    4 -> new Book(id, title, author, avail)  (everything given)

## Sample test
    Type: 2, 101, Clean Code
    Result: book added, Author shows "Unknown Author"

    Type: 3, 102, Effective Java, Joshua Bloch
    Type: 6, 102
    Result: Borrowed: Effective Java

    Type: 6, 102 again
    Result: Effective Java is already borrowed.

    Type: 3, -5, Bad, Nobody
    Result: Invalid book: Book ID cannot be negative.

## What happens inside
1. Constructors 1, 2 and 3 only fill in default values and pass them to constructor 4.
2. Constructor 4 checks the data. Bad data throws an exception, so no wrong Book object is ever created.
3. The menu catches that exception and prints a friendly message.
4. Borrow and return just flip the available flag and tell you if it was not possible.