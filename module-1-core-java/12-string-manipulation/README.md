# Assignment 12 - String Manipulation without Built-in Shortcuts

## What it does
A menu app with six string tools. Each one is written by hand using loops and char arrays, without ready-made methods that solve the task directly.

## Concepts
- Strings as char arrays
- Loops and two-pointer technique
- Counting with arrays
- Writing your own helper methods (isLetter, toLower, isVowel)

## Not used on purpose
    StringBuilder.reverse(), equals(), equalsIgnoreCase(), compareTo(),
    split(), Character.isLetter(), toLowerCase(), Collections

## Files
    src/stringutils/StringUtils.java -> all six tasks + helpers
    src/stringutils/StringApp.java   -> the menu

## Run
    cd 12-string-manipulation
    javac -d out src\stringutils\*.java
    java -cp out stringutils.StringApp

## Sample test
    Type: 1, Hello World
    Result: Reversed: dlroW olleH

    Type: 2, A man, a plan, a canal: Panama
    Result: IS a palindrome

    Type: 3, Hello World
    Result: Vowels 3, Consonants 7

    Type: 4,   Java   is fun
    Result: Words: 3   (extra spaces are ignored)

    Type: 5, programming
    Result: r (2 times), g (2 times), m (2 times)

    Type: 6, Apple, apple
    Result: Case-sensitive: first comes BEFORE second | Ignoring case: EQUAL

## What happens inside
1. Every method turns the text into a char array with toCharArray() and loops over it.
2. Reverse fills a new array from the back. Palindrome compares from both ends.
3. Duplicates uses an int array where the character itself is the index (count['a']++).
4. Compare stops at the first different character, or compares lengths if one text is shorter.