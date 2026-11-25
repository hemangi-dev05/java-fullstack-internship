package passwordcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    // compiled once and reused, one pattern per rule
    private static final Pattern LENGTH  = Pattern.compile(".{8,20}");
    private static final Pattern UPPER   = Pattern.compile("[A-Z]");
    private static final Pattern LOWER   = Pattern.compile("[a-z]");
    private static final Pattern DIGIT   = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL = Pattern.compile("[^A-Za-z0-9\\s]");
    private static final Pattern SPACE   = Pattern.compile("\\s");

    // all rules in one pattern using lookaheads: (?=.*X) means "X appears somewhere"
    private static final Pattern ALL_RULES = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9\\s])\\S{8,20}$");

    // returns the list of broken rules, an empty list means the password is valid
    public List<String> validate(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        List<String> problems = new ArrayList<>();

        // matches() must match the WHOLE text, find() looks for a match anywhere inside
        if (!LENGTH.matcher(password).matches()) {
            problems.add("Length must be 8 to 20 characters (yours: " + password.length() + ").");
        }
        if (!found(UPPER, password)) {
            problems.add("Add at least one uppercase letter (A-Z).");
        }
        if (!found(LOWER, password)) {
            problems.add("Add at least one lowercase letter (a-z).");
        }
        if (!found(DIGIT, password)) {
            problems.add("Add at least one digit (0-9).");
        }
        if (!found(SPECIAL, password)) {
            problems.add("Add at least one special character (example: @ # $ % !).");
        }
        if (found(SPACE, password)) {
            problems.add("Spaces are not allowed.");
        }
        return problems;
    }

    public boolean isValid(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        return ALL_RULES.matcher(password).matches();
    }

    private static boolean found(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}