package stringutils;

public class StringUtils {

    // ----- small character helpers (written by hand, no Character.isLetter etc.) -----

    public static char toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 32);
        }
        return c;
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isLetterOrDigit(char c) {
        return isLetter(c) || (c >= '0' && c <= '9');
    }

    public static boolean isVowel(char c) {
        char l = toLower(c);
        return l == 'a' || l == 'e' || l == 'i' || l == 'o' || l == 'u';
    }

    private static boolean isSpace(char c) {
        return c == ' ' || c == '\t';
    }

    // ----- the six tasks -----

    // copy characters from the end to the start into a new array
    public static String reverse(String text) {
        char[] chars = text.toCharArray();
        char[] result = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            result[i] = chars[chars.length - 1 - i];
        }
        return new String(result);
    }

    // two pointers move towards the middle, skipping spaces and symbols
    public static boolean isPalindrome(String text) {
        char[] chars = text.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            if (!isLetterOrDigit(chars[left])) {
                left++;
                continue;
            }
            if (!isLetterOrDigit(chars[right])) {
                right--;
                continue;
            }
            if (toLower(chars[left]) != toLower(chars[right])) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean hasLetterOrDigit(String text) {
        for (char c : text.toCharArray()) {
            if (isLetterOrDigit(c)) {
                return true;
            }
        }
        return false;
    }

    // returns {vowels, consonants}; digits, spaces and symbols are counted as neither
    public static int[] countVowelsAndConsonants(String text) {
        int vowels = 0;
        int consonants = 0;
        for (char c : text.toCharArray()) {
            if (isLetter(c)) {
                if (isVowel(c)) {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        return new int[] { vowels, consonants };
    }

    // a word starts when we move from a space to a non-space
    public static int countWords(String text) {
        int words = 0;
        boolean inWord = false;
        for (char c : text.toCharArray()) {
            if (isSpace(c)) {
                inWord = false;
            } else if (!inWord) {
                inWord = true;
                words++;
            }
        }
        return words;
    }

    // case-insensitive, spaces ignored, listed in order of first appearance
    public static String findDuplicates(String text) {
        char[] chars = text.toCharArray();
        int[] count = new int[65536];
        for (char c : chars) {
            if (!isSpace(c)) {
                count[toLower(c)]++;
            }
        }
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            char key = toLower(c);
            if (!isSpace(c) && count[key] > 1) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(key).append(" (").append(count[key]).append(" times)");
                count[key] = 0;
            }
        }
        return result.toString();
    }

    // returns 0 if equal, negative if a comes first, positive if a comes after b
    public static int compare(String a, String b, boolean ignoreCase) {
        char[] x = a.toCharArray();
        char[] y = b.toCharArray();
        int shorter = x.length < y.length ? x.length : y.length;
        for (int i = 0; i < shorter; i++) {
            char cx = ignoreCase ? toLower(x[i]) : x[i];
            char cy = ignoreCase ? toLower(y[i]) : y[i];
            if (cx != cy) {
                return cx - cy;
            }
        }
        return x.length - y.length;
    }
}