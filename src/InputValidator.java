package expensetracker;

import java.util.Scanner;

/**
 * Utility class for validated user input.
 * Centralizes all input parsing and validation, demonstrating
 * exception handling and the single-responsibility principle.
 */
public class InputValidator {

    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Reads a non-empty string from the user.
     */
    public String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("  Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads a positive double (amount) from the user.
     */
    public double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("  Amount must be greater than 0.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number. Please enter a numeric value (e.g. 250.50).");
            }
        }
    }

    /**
     * Reads a positive integer from the user.
     */
    public int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("  Please enter a positive number.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Reads and validates a date string in YYYY-MM-DD format.
     */
    public String readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return input;
            }
            System.out.println("  Invalid format. Use YYYY-MM-DD (e.g. 2025-03-15).");
        }
    }

    /**
     * Reads and validates a month string in YYYY-MM format.
     */
    public String readMonth(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}-\\d{2}")) {
                return input;
            }
            System.out.println("  Invalid format. Use YYYY-MM (e.g. 2025-03).");
        }
    }

    /**
     * Reads a category selection from the numbered menu.
     */
    public String readCategory() {
        System.out.println("  Categories:");
        System.out.println(Category.listAll());
        while (true) {
            System.out.print("  Enter category name or number: ");
            String input = scanner.nextLine().trim();
            try {
                // Try numeric selection first
                int index = Integer.parseInt(input);
                return Category.fromIndex(index).name();
            } catch (NumberFormatException e) {
                // Try string match
                try {
                    return Category.fromString(input).name();
                } catch (IllegalArgumentException ex) {
                    System.out.println("  " + ex.getMessage() + ". Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("  " + e.getMessage() + ". Try again.");
            }
        }
    }

    /**
     * Reads a single-character menu choice.
     */
    public String readMenuChoice(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
