package expensetracker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Core business logic for managing expenses.
 * Handles add, delete, filter, and summary operations.
 * Demonstrates: Collections (ArrayList), encapsulation, separation of concerns.
 */
public class ExpenseManager {

    private List<Expense> expenses;
    private final FileManager fileManager;
    private int nextId;

    public ExpenseManager(String dataFilePath) {
        this.fileManager = new FileManager(dataFilePath);
        this.expenses = fileManager.loadAll();
        this.nextId = computeNextId();
    }

    private int computeNextId() {
        return expenses.stream()
                       .mapToInt(Expense::getId)
                       .max()
                       .orElse(0) + 1;
    }

    /**
     * Adds a new expense and persists it.
     */
    public void addExpense(String date, String category, String description, double amount) {
        Expense e = new Expense(nextId++, date, category, description, amount);
        expenses.add(e);
        fileManager.appendExpense(e);
        System.out.println("\n✔ Expense added: " + e);
    }

    /**
     * Returns all expenses, or a message if none exist.
     */
    public List<Expense> getAllExpenses() {
        return Collections.unmodifiableList(expenses);
    }

    /**
     * Returns expenses filtered by category (case-insensitive).
     */
    public List<Expense> getByCategory(String category) {
        String cat = category.trim().toUpperCase();
        List<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(cat)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Returns expenses filtered by month (format: YYYY-MM).
     */
    public List<Expense> getByMonth(String yearMonth) {
        List<Expense> result = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getDate().startsWith(yearMonth)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Deletes an expense by ID.
     * Returns true if found and deleted, false otherwise.
     */
    public boolean deleteExpense(int id) {
        Iterator<Expense> it = expenses.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                fileManager.saveAll(expenses); // Rewrite file after deletion
                return true;
            }
        }
        return false;
    }

    /**
     * Returns total amount spent in a given month.
     */
    public double getMonthlyTotal(String yearMonth) {
        return getByMonth(yearMonth).stream()
                                    .mapToDouble(Expense::getAmount)
                                    .sum();
    }

    /**
     * Returns a map of category -> total amount for a given month.
     */
    public Map<String, Double> getMonthlySummary(String yearMonth) {
        Map<String, Double> summary = new LinkedHashMap<>();
        for (Category cat : Category.values()) {
            summary.put(cat.name(), 0.0);
        }
        for (Expense e : getByMonth(yearMonth)) {
            summary.merge(e.getCategory(), e.getAmount(), Double::sum);
        }
        // Remove zero-value categories
        summary.entrySet().removeIf(entry -> entry.getValue() == 0.0);
        return summary;
    }

    /**
     * Returns the grand total of all expenses ever.
     */
    public double getGrandTotal() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public int getExpenseCount() {
        return expenses.size();
    }
}
