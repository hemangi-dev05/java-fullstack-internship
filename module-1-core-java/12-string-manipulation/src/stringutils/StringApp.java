package stringutils;

import java.util.Scanner;

public class StringApp {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome! String tools built without ready-made shortcuts.");
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option (0-7): ");
            switch (choice) {
                case 1 -> reverse();
                case 2 -> palindrome();
                case 3 -> vowelsAndConsonants();
                case 4 -> words();
                case 5 -> duplicates();
                case 6 -> compareTwo();
                case 7 -> explain();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Please choose a number from 0 to 7.");
            }
            if (choice >= 1 && choice <= 7) {
                pause();
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=========== STRING MENU ===========");
        System.out.println("  1. Reverse a string          (example: Hello)");
        System.out.println("  2. Check palindrome          (example: Madam)");
        System.out.println("  3. Count vowels, consonants  (example: Hello World)");
        System.out.println("  4. Count words               (example: Java is fun)");
        System.out.println("  5. Find duplicate characters (example: programming)");
        System.out.println("  6. Compare two strings       (example: Apple, apple)");
        System.out.println("  7. Read the explanation");
        System.out.println("  0. Exit");
        System.out.println("===================================");
    }

    private static void reverse() {
        String text = readText("Enter text (example: Hello): ");
        System.out.println("Original: " + text);
        System.out.println("Reversed: " + StringUtils.reverse(text));
    }

    private static void palindrome() {
        String text = readText("Enter text (example: Madam): ");
        if (!StringUtils.hasLetterOrDigit(text)) {
            System.out.println("Please enter at least one letter or digit.");
            return;
        }
        if (StringUtils.isPalindrome(text)) {
            System.out.println("\"" + text + "\" IS a palindrome.");
        } else {
            System.out.println("\"" + text + "\" is NOT a palindrome.");
        }
        System.out.println("(upper/lower case, spaces and symbols are ignored)");
    }

    private static void vowelsAndConsonants() {
        String text = readText("Enter text (example: Hello World): ");
        int[] result = StringUtils.countVowelsAndConsonants(text);
        System.out.println("Vowels     : " + result[0]);
        System.out.println("Consonants : " + result[1]);
    }

    private static void words() {
        String text = readText("Enter a sentence (example: Java is fun): ");
        System.out.println("Words: " + StringUtils.countWords(text));
    }

    private static void duplicates() {
        String text = readText("Enter text (example: programming): ");
        String result = StringUtils.findDuplicates(text);
        if (result.isEmpty()) {
            System.out.println("No duplicate characters found.");
        } else {
            System.out.println("Duplicates: " + result);
        }
    }

    private static void compareTwo() {
        String a = readText("Enter first text (example: Apple): ");
        String b = readText("Enter second text (example: apple): ");
        System.out.println("Case-sensitive : " + describe(StringUtils.compare(a, b, false)));
        System.out.println("Ignoring case  : " + describe(StringUtils.compare(a, b, true)));
    }

    private static String describe(int result) {
        if (result == 0) {
            return "EQUAL";
        }
        return result < 0 ? "first comes BEFORE second" : "first comes AFTER second";
    }

    private static void explain() {
        System.out.println();
        System.out.println("HOW EACH ONE WORKS (no reverse(), equals(), split(), etc.)");
        System.out.println("  Reverse    : copy characters from the last to the first into a new array");
        System.out.println("  Palindrome : two pointers, left and right, move to the middle and compare");
        System.out.println("  Vowels     : check each letter against a, e, i, o, u");
        System.out.println("  Words      : count every switch from a space to a non-space");
        System.out.println("  Duplicates : count each character in an array, print those seen more than once");
        System.out.println("  Compare    : walk both texts, stop at the first different character");
        System.out.println();
        System.out.println("Only letters A-Z and a-z are treated as letters.");
    }

    private static void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine();
            if (!text.isBlank()) {
                return text;
            }
            System.out.println("  Text cannot be empty. Try again.");
        }
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