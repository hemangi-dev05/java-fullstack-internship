package polymorphism;

import java.util.Scanner;

public class PolymorphismApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final Calculator calc = new Calculator();

    public static void main(String[] args) {
        System.out.println("Welcome! This program demonstrates method overloading and overriding.");
        int choice;
        do {
            printMainMenu();
            choice = readInt("Choose an option (0-4): ");
            switch (choice) {
                case 1 -> overloadingMenu();
                case 2 -> overridingMenu();
                case 3 -> { sideBySide(); pause(); }
                case 4 -> { explain(); pause(); }
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 4.");
            }
        } while (choice != 0);
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("=========== MAIN MENU ===========");
        System.out.println("  1. Method OVERLOADING demo");
        System.out.println("  2. Method OVERRIDING demo");
        System.out.println("  3. Overloading vs overriding side by side");
        System.out.println("  4. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("=================================");
    }

    // ---------- overloading ----------

    private static void overloadingMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("--- OVERLOADING: one method name 'add', four versions ---");
            System.out.println("  1. Add two whole numbers     (example: 10 and 20)");
            System.out.println("  2. Add three whole numbers   (example: 1, 2 and 3)");
            System.out.println("  3. Add two decimal numbers   (example: 2.5 and 3.5)");
            System.out.println("  4. Join two texts            (example: Peak and ProSys)");
            System.out.println("  0. Back to main menu");
            choice = readInt("Your choice (0-4): ");
            try {
                switch (choice) {
                    case 1 -> addTwoInts();
                    case 2 -> addThreeInts();
                    case 3 -> addTwoDoubles();
                    case 4 -> addTwoTexts();
                    case 0 -> { }
                    default -> System.out.println("Please choose a number from 0 to 4.");
                }
            } catch (ArithmeticException e) {
                System.out.println("Result is too large for a whole number (int). Try smaller numbers.");
            }
            if (choice >= 1 && choice <= 4) {
                pause();
            }
        } while (choice != 0);
    }

    private static void addTwoInts() {
        int a = readInt("Enter first whole number (example: 10): ");
        int b = readInt("Enter second whole number (example: 20): ");
        System.out.println("Java called: add(int, int)");
        System.out.println("Result: " + calc.add(a, b));
    }

    private static void addThreeInts() {
        int a = readInt("Enter first whole number (example: 1): ");
        int b = readInt("Enter second whole number (example: 2): ");
        int c = readInt("Enter third whole number (example: 3): ");
        System.out.println("Java called: add(int, int, int)");
        System.out.println("Result: " + calc.add(a, b, c));
    }

    private static void addTwoDoubles() {
        double a = readDouble("Enter first decimal number (example: 2.5): ");
        double b = readDouble("Enter second decimal number (example: 3.5): ");
        System.out.println("Java called: add(double, double)");
        System.out.println("Result: " + calc.add(a, b));
    }

    private static void addTwoTexts() {
        String a = readText("Enter first text (example: Peak): ");
        String b = readText("Enter second text (example: ProSys): ");
        System.out.println("Java called: add(String, String)");
        System.out.println("Result: " + calc.add(a, b));
    }

    // ---------- overriding ----------

    private static void overridingMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("--- OVERRIDING: Dog and Cat replace Animal's methods ---");
            System.out.println("  1. Create a Dog");
            System.out.println("  2. Create a Cat");
            System.out.println("  3. Create a generic Animal (uses the parent version)");
            System.out.println("  4. Show all three together");
            System.out.println("  0. Back to main menu");
            choice = readInt("Your choice (0-4): ");
            try {
                switch (choice) {
                    case 1 -> createAnimal("Dog");
                    case 2 -> createAnimal("Cat");
                    case 3 -> createAnimal("Animal");
                    case 4 -> sampleZoo();
                    case 0 -> { }
                    default -> System.out.println("Please choose a number from 0 to 4.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Could not create it: " + e.getMessage() + " Please type a name.");
            }
            if (choice >= 1 && choice <= 4) {
                pause();
            }
        } while (choice != 0);
    }

    private static void createAnimal(String type) {
        String name = readText("Enter a name (example: Bruno): ");

        // reference type is always Animal, the object type decides the output
        Animal a = switch (type) {
            case "Dog" -> new Dog(name);
            case "Cat" -> new Cat(name);
            default -> new Animal(name);
        };
        System.out.println("Reference type: Animal, actual object: " + a.getClass().getSimpleName());
        a.describe();
        System.out.println(a.move());
    }

    private static void sampleZoo() {
        Animal[] zoo = { new Animal("Creature"), new Dog("Bruno"), new Cat("Kitty") };
        for (Animal a : zoo) {
            a.describe();
            System.out.println("  " + a.move());
        }
    }

    // ---------- comparison and explanation ----------

    private static void sideBySide() {
        Printer printer = new Printer();
        Dog d = new Dog("Bruno");
        Animal a = new Dog("Bruno");

        System.out.println();
        System.out.println("Dog d = new Dog(...);      Animal a = new Dog(...);");
        System.out.println();
        System.out.println("printer.show(d) -> " + printer.show(d));
        System.out.println("printer.show(a) -> " + printer.show(a));
        System.out.println("  Overloading: chosen by the reference type, at compile time.");
        System.out.println();
        System.out.println("d.makeSound()   -> " + d.makeSound());
        System.out.println("a.makeSound()   -> " + a.makeSound());
        System.out.println("  Overriding: chosen by the real object, at run time.");
    }

    private static void explain() {
        System.out.println();
        System.out.println("OVERLOADING (compile-time polymorphism)");
        System.out.println("  - same class, same method name, different parameters");
        System.out.println("  - the compiler picks the method from the arguments");
        System.out.println("  - example: Calculator.add(int,int) and add(double,double)");
        System.out.println();
        System.out.println("OVERRIDING (runtime polymorphism)");
        System.out.println("  - child class replaces a parent method, same signature");
        System.out.println("  - the JVM picks the method from the actual object");
        System.out.println("  - example: Dog.makeSound() replaces Animal.makeSound()");
    }

    // ---------- input helpers ----------

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
                System.out.println("  That is not a whole number. Try again (example: 10).");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  That is not a number. Try again (example: 2.5).");
            }
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
}