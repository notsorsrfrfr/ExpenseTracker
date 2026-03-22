package expensetracker;

/**
 * Enum representing valid expense categories.
 * Demonstrates use of Java enums for type-safe constants.
 */
public enum Category {
    FOOD,
    TRANSPORT,
    EDUCATION,
    ENTERTAINMENT,
    HEALTH,
    SHOPPING,
    UTILITIES,
    OTHER;

    /**
     * Returns all category names as a formatted numbered list.
     */
    public static String listAll() {
        StringBuilder sb = new StringBuilder();
        Category[] values = Category.values();
        for (int i = 0; i < values.length; i++) {
            sb.append("  ").append(i + 1).append(". ").append(values[i].name());
            if (i < values.length - 1) sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Parses a string to a Category, case-insensitive.
     * Throws IllegalArgumentException if invalid.
     */
    public static Category fromString(String input) {
        try {
            return Category.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: '" + input + "'");
        }
    }

    /**
     * Parses a 1-based index to a Category.
     */
    public static Category fromIndex(int index) {
        Category[] values = Category.values();
        if (index < 1 || index > values.length) {
            throw new IllegalArgumentException("Invalid category number. Choose 1–" + values.length);
        }
        return values[index - 1];
    }
}
