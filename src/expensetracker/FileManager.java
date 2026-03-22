package expensetracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all File I/O operations for expense data.
 * Reads from and writes to a CSV file for persistent storage.
 */
public class FileManager {

    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
        ensureFileExists();
    }

    /**
     * Creates the CSV file and header if it doesn't exist.
     */
    private void ensureFileExists() {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                // Write CSV header
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("id,date,category,description,amount");
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Warning: Could not create data file: " + e.getMessage());
            }
        }
    }

    /**
     * Loads all expenses from the CSV file.
     * Skips the header line and any malformed lines gracefully.
     */
    public List<Expense> loadAll() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return expenses;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // Skip header
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    expenses.add(Expense.fromCSV(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping malformed line: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return expenses;
    }

    /**
     * Saves all expenses back to the CSV file (overwrites).
     */
    public void saveAll(List<Expense> expenses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,date,category,description,amount");
            writer.newLine();
            for (Expense e : expenses) {
                writer.write(e.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Appends a single expense to the CSV file.
     * More efficient than rewriting the whole file for additions.
     */
    public void appendExpense(Expense expense) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(expense.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending expense: " + e.getMessage());
        }
    }
}
