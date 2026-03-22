package expensetracker;

/**
 * Represents a single expense entry.
 * Demonstrates OOP: encapsulation via private fields and public getters/setters.
 */
public class Expense {

    private int id;
    private String date;       // Format: YYYY-MM-DD
    private String category;
    private String description;
    private double amount;

    // Constructor
    public Expense(int id, String date, String category, String description, double amount) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    // Getters
    public int getId()            { return id; }
    public String getDate()       { return date; }
    public String getCategory()   { return category; }
    public String getDescription(){ return description; }
    public double getAmount()     { return amount; }

    // Setters
    public void setDate(String date)            { this.date = date; }
    public void setCategory(String category)    { this.category = category; }
    public void setDescription(String desc)     { this.description = desc; }
    public void setAmount(double amount)        { this.amount = amount; }

    /**
     * Converts expense to CSV line for file storage.
     */
    public String toCSV() {
        return id + "," + date + "," + category + "," + description.replace(",", ";") + "," + amount;
    }

    /**
     * Creates an Expense object from a CSV line.
     * Demonstrates static factory method pattern.
     */
    public static Expense fromCSV(String csvLine) throws IllegalArgumentException {
        String[] parts = csvLine.split(",", 5);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid CSV line: " + csvLine);
        }
        try {
            int id = Integer.parseInt(parts[0].trim());
            String date = parts[1].trim();
            String category = parts[2].trim();
            String description = parts[3].trim().replace(";", ",");
            double amount = Double.parseDouble(parts[4].trim());
            return new Expense(id, date, category, description, amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Malformed numeric field in: " + csvLine);
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %-15s | %-25s | Rs. %.2f", id, date, category, description, amount);
    }
}
