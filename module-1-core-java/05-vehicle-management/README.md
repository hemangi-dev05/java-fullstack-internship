# 05 - Abstract Vehicle Management System

Assignment 5 of the Java Full Stack Internship at PeakProSys Solutions, Pune.

## Overview

A console application to manage a small fleet of vehicles. The user can add cars, bikes and trucks, view the full list, and calculate the rent of any vehicle for a given number of days.

The main goal of the assignment is to show how an **abstract class** defines a common structure while each child class supplies its own behaviour.

## Features

- Add a Car (seats), Bike (engine cc) or Truck (load capacity)
- Each vehicle gets an auto-generated ID
- Show all vehicles in one list
- Calculate rent by vehicle ID and number of days
- Input validation with clear error messages

## Design

`Vehicle` is an abstract class, so `new Vehicle()` is not allowed. It holds what every vehicle shares (id, brand, model and `displayDetails()`) and declares three abstract methods that every child class must implement:

| Method | Purpose |
| --- | --- |
| `getVehicleType()` | Returns "Car", "Bike" or "Truck" |
| `getSpecialDetail()` | Returns the detail unique to that type (seats, cc or load) |
| `calculateRent(int days)` | Returns the rent using that type's own formula |

```
               Vehicle (abstract)
   getVehicleType() | getSpecialDetail() | calculateRent()
                        |
            +-----------+-----------+
            |           |           |
           Car         Bike       Truck
```

All vehicles are stored in a single `ArrayList<Vehicle>`. Calling `calculateRent()` or `displayDetails()` on any item runs the version that belongs to the actual object (polymorphism).

## Rent calculation

| Vehicle | Rent per day |
| --- | --- |
| Car | 1500 |
| Bike | 500 |
| Truck | 2000 + (100 x load capacity in tons) |

## Validation

- Brand and model cannot be empty
- Seats, engine cc and load capacity must be greater than 0
- Number of days must be greater than 0
- A non-numeric value is rejected and asked again
- An unknown vehicle ID shows a "not found" message

## Project structure

```
05-vehicle-management/
├── screenshots/
└── src/com/internship/module1/vehicle/
    ├── Vehicle.java    abstract parent class
    ├── Car.java
    ├── Bike.java
    ├── Truck.java
    └── Main.java       menu and input handling
```

## How to run

From this folder:

```
javac -d out src/com/internship/module1/vehicle/*.java
java -cp out com.internship.module1.vehicle.Main
```

Or open the project in IntelliJ IDEA and run `Main`. Built and tested with JDK 17.

## Sample input and output

```
Enter choice: 1
Enter brand: Toyota
Enter model: Innova
Enter number of seats: 7
Added: ID: 1 | Car   | Toyota Innova | Seats: 7

Enter choice: 5
Enter vehicle ID: 1
Enter number of days: 3
Rent for 3 day(s): 4500.00
```
## Screenshots

### Output

**Main menu**

The program starts and shows the menu with add car, add bike, add truck, show all, calculate rent and exit.

![Main menu](<screenshots/menu.png>)

**Add car**

Choice 1 with brand, model and seats. The car is added and gets ID 1.

![Add car](<screenshots/car.png>)

**Add bike**

Choice 2 with brand, model and engine cc. The bike is added and gets the next ID.

![Add bike](<screenshots/bike.png>)

**Add truck**

Choice 3 with brand, model and load capacity. The truck is added and gets the next ID.

![Add truck](<screenshots/truck.png>)

**Show all vehicles**

Choice 4 lists every vehicle. The same displayDetails() call prints seats for a car, engine cc for a bike and load for a truck.

![Show all vehicles](<screenshots/show all.png>)

**Calculate rent**

Choice 5 with a vehicle ID and number of days. Each vehicle type uses its own calculateRent() formula.

![Calculate rent](<screenshots/rent.png>)
