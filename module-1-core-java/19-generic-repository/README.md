# Assignment 19 - Generic Repository Class using Java Generics

## What it does
A reusable repository class that can store any type of object. The same class is used for Products (integer id) and Users (email as id) in a menu-driven console app.

## Features
    Add or update, list, find, delete for Products and Users
    Filter products cheaper than a given price (findWhere with a lambda)
    Input validation (empty name, price below 1, invalid email)

## Concepts
- Generic interface and generic class (Repository<T, ID>)
- Type parameters with two types
- Function<T, ID> to get the id from any object
- Predicate<T> for flexible searching
- Optional to avoid null checks
- Compile-time type safety

## Files
    src/generic/Repository.java          -> the generic interface
    src/generic/GenericRepository.java   -> the generic implementation (LinkedHashMap)
    src/generic/Product.java             -> sample entity with Integer id
    src/generic/User.java                -> sample entity with String id
    src/generic/RepositoryApp.java       -> the menu and input handling

## Run
    cd 19-generic-repository
    javac -d out src\generic\*.java
    java -cp out generic.RepositoryApp

## Sample test
    Type: 1, then 1, Laptop, 55000
    Type: 1, then 2, Mouse, 499
    Type: 5, then 1500
    Result: only Mouse is shown

## What happens inside
1. The repository keeps objects in a LinkedHashMap with the id as key.
2. The id is taken from each object using the Function given in the constructor.
3. Saving with an existing id replaces the old object.
4. findById returns an Optional, findWhere returns all objects matching the Predicate.