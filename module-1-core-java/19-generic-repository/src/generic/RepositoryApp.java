package generic;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RepositoryApp {

    private static final Scanner sc = new Scanner(System.in);

    // the same GenericRepository class is used for two different types
    private static final Repository<Product, Integer> products = new GenericRepository<>(Product::getId);
    private static final Repository<User, String> users = new GenericRepository<>(User::getEmail);

    public static void main(String[] args) {
        System.out.println("Welcome! Generic repository demo (Products and Users).");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-10): ");
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> showProducts(products.findAll());
                case 3 -> findProduct();
                case 4 -> deleteProduct();
                case 5 -> cheaperProducts();
                case 6 -> addUser();
                case 7 -> showUsers();
                case 8 -> findUser();
                case 9 -> deleteUser();
                case 10 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 10.");
            }
            if (choice >= 1 && choice <= 10) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= GENERIC REPOSITORY MENU =========");
        System.out.println("  1. Add or update a product");
        System.out.println("  2. List all products");
        System.out.println("  3. Find product by ID");
        System.out.println("  4. Delete product by ID");
        System.out.println("  5. Products cheaper than a price");
        System.out.println("  6. Add or update a user");
        System.out.println("  7. List all users");
        System.out.println("  8. Find user by email");
        System.out.println("  9. Delete user by email");
        System.out.println(" 10. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("===========================================");
    }

    private static void addProduct() {
        int id = readInt("Product ID: ");
        if (id <= 0) {
            System.out.println("ID must be a positive number.");
            return;
        }
        System.out.print("Product name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double price = readDouble("Price: ");
        if (price <= 0) {
            System.out.println("Price must be more than 0.");
            return;
        }
        boolean existed = products.findById(id).isPresent();
        products.save(new Product(id, name, price));
        System.out.println(existed ? "Product updated." : "Product added.");
    }

    private static void showProducts(List<Product> list) {
        if (list.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        System.out.printf("%-5s %-15s %10s%n", "ID", "Name", "Price");
        for (Product p : list) {
            System.out.println(p);
        }
    }

    private static void findProduct() {
        int id = readInt("Enter product ID: ");
        Optional<Product> result = products.findById(id);
        if (result.isPresent()) {
            showProducts(List.of(result.get()));
        } else {
            System.out.println("No product found with ID " + id + ".");
        }
    }

    private static void deleteProduct() {
        int id = readInt("Enter product ID to delete: ");
        System.out.println(products.deleteById(id) ? "Product deleted." : "No product found with ID " + id + ".");
    }

    private static void cheaperProducts() {
        double limit = readDouble("Show products cheaper than: ");
        showProducts(products.findWhere(p -> p.getPrice() < limit));
    }

    private static void addUser() {
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        if (!email.contains("@")) {
            System.out.println("Please enter a valid email.");
            return;
        }
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        boolean existed = users.findById(email).isPresent();
        users.save(new User(email, name));
        System.out.println(existed ? "User updated." : "User added.");
    }

    private static void showUsers() {
        List<User> list = users.findAll();
        if (list.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.printf("%-22s %-15s%n", "Email", "Name");
        for (User u : list) {
            System.out.println(u);
        }
        System.out.println("Total users: " + users.count());
    }

    private static void findUser() {
        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();
        users.findById(email).ifPresentOrElse(
                u -> System.out.println("Found: " + u),
                () -> System.out.println("No user found with email " + email + "."));
    }

    private static void deleteUser() {
        System.out.print("Enter email to delete: ");
        String email = sc.nextLine().trim();
        System.out.println(users.deleteById(email) ? "User deleted." : "No user found with email " + email + ".");
    }

    private static void explain() {
        System.out.println();
        System.out.println("GENERICS");
        System.out.println("  Repository<T, ID> uses type parameters: T is the object type, ID is the id type.");
        System.out.println("  The same GenericRepository class stores Products (Integer id)");
        System.out.println("  and Users (String id) with no casting and no duplicate code.");
        System.out.println();
        System.out.println("HOW THE ID IS FOUND");
        System.out.println("  The constructor takes a Function<T, ID>, for example Product::getId.");
        System.out.println("  This is how the repository knows the id of any object.");
        System.out.println();
        System.out.println("OTHER POINTS");
        System.out.println("  findById returns Optional, so a missing object is handled without null checks.");
        System.out.println("  findWhere takes a Predicate<T>, so any condition can be passed (a lambda).");
        System.out.println("  The compiler checks types: a User cannot be saved into the Product repository.");
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
                System.out.println("  That is not a number. Try again (example: 499.5).");
            }
        }
    }
}