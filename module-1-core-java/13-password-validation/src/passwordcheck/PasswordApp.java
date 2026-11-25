package passwordcheck;

import java.util.List;
import java.util.Scanner;

public class PasswordApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final PasswordValidator validator = new PasswordValidator();

    public static void main(String[] args) {
        System.out.println("Welcome! Password validation using regular expressions.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-4): ");
            switch (choice) {
                case 1 -> checkOne();
                case 2 -> showRules();
                case 3 -> runSamples();
                case 4 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 4.");
            }
            if (choice >= 1 && choice <= 4) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========= PASSWORD MENU =========");
        System.out.println("  1. Check a password");
        System.out.println("  2. Show the password rules");
        System.out.println("  3. Run sample passwords");
        System.out.println("  4. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("=================================");
    }

    private static void checkOne() {
        System.out.print("Enter a password (example: Peak@2024): ");
        String password = sc.nextLine();
        report(password);
    }

    private static void report(String password) {
        List<String> problems = validator.validate(password);
        System.out.println();
        System.out.println("Password: " + password);
        if (problems.isEmpty()) {
            System.out.println("Result  : VALID");
        } else {
            System.out.println("Result  : INVALID (" + problems.size() + " problem(s))");
            for (String p : problems) {
                System.out.println("  - " + p);
            }
        }
        // the single combined pattern must agree with the rule-by-rule check
        System.out.println("Combined pattern says: " + (validator.isValid(password) ? "valid" : "invalid"));
    }

    private static void showRules() {
        System.out.println();
        System.out.println("A valid password needs ALL of these:");
        System.out.println("  1. 8 to 20 characters");
        System.out.println("  2. at least one uppercase letter");
        System.out.println("  3. at least one lowercase letter");
        System.out.println("  4. at least one digit");
        System.out.println("  5. at least one special character");
        System.out.println("  6. no spaces");
    }

    private static void runSamples() {
        String[] samples = {
            "Peak@2024",              // valid
            "Abcde1@x",               // valid, exactly 8 characters
            "Abcdefghijklmnop1@xy",   // valid, exactly 20 characters
            "Abcdefghijklmnop1@xyz",  // too long, 21 characters
            "Pe@2",                   // too short
            "peak@2024",              // no uppercase
            "PEAK@2024",              // no lowercase
            "PeakPro@@",              // no digit
            "Peak2024",               // no special character
            "Peak @2024"              // has a space
        };
        for (String s : samples) {
            report(s);
        }
    }

    private static void explain() {
        System.out.println();
        System.out.println("REGEX USED (see PasswordValidator.java)");
        System.out.println("  .{8,20}          any character, 8 to 20 times (must match the whole text)");
        System.out.println("  [A-Z]            one uppercase letter anywhere");
        System.out.println("  [a-z]            one lowercase letter anywhere");
        System.out.println("  [0-9]            one digit anywhere");
        System.out.println("  [^A-Za-z0-9\\s]   one character that is NOT a letter, digit or whitespace");
        System.out.println("  \\s               any whitespace (space, tab, newline)");
        System.out.println();
        System.out.println("PATTERN AND MATCHER");
        System.out.println("  Pattern.compile(...)  builds the rule once");
        System.out.println("  matcher.matches()     whole text must fit the pattern");
        System.out.println("  matcher.find()        pattern appears anywhere in the text");
        System.out.println();
        System.out.println("COMBINED PATTERN uses lookaheads (?=.*X): 'X must appear somewhere'.");
        System.out.println("Note: the password is shown on screen here, only for learning.");
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
}