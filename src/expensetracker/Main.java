package expensetracker;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Entry point for the Personal Expense Tracker.
 *
 * Features:
 *   1. Add a new expense
 *   2. View all expenses
 *   3. Filter by category
 *   4. Filter by month
 *   5. Monthly summary (totals per category)
 *   6. Delete an expense
 *   7. Exit
 *
 * Data is persisted to data/expenses.csv across runs.
 */
public class Main {

    private static final String DATA_FILE = "data/expenses.csv";
    private static final String DIVIDER   = "─".repeat(65);
    private static final String THIN_DIV  = "·".repeat(65);

    public static void main(String[] args) {
        ExpenseManager manager   = new ExpenseManager(DATA_FILE);
        Scanner scanner          = new Scanner(System.in);
        InputValidator validator = new InputValidator(scanner);

        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = validator.readMenuChoice("  Your choice: ");

            switch (choice) {
                case "1": handleAdd(manager, validator);          break;
                case "2": handleViewAll(manager);                 break;
                case "3": handleFilterCategory(manager, validator); break;
                case "4": handleFilterMonth(manager, validator);  break;
                case "5": handleMonthlySummary(manager, validator); break;
                case "6": handleDelete(manager, validator);       break;
                case "7":
                    System.out.println("\n  Goodbye! Keep tracking your expenses. 👋\n");
                    running = false;
                    break;
                default:
                    System.out.println("  Invalid choice. Enter a number 1–7.");
            }
        }
        scanner.close();
    }

    // ─── Menu Display ───────────────────────────────────────────────────────

    private static void printBanner() {
        System.out.println("\n" + DIVIDER);
        System.out.println("       PERSONAL EXPENSE TRACKER  |  Java BYOP Project");
        System.out.println(DIVIDER);
        System.out.println("  Data file: " + DATA_FILE);
        System.out.println(DIVIDER + "\n");
    }

    private static void printMenu() {
        System.out.println("\n" + DIVIDER);
        System.out.println("  MAIN MENU");
        System.out.println(THIN_DIV);
        System.out.println("  1. Add new expense");
        System.out.println("  2. View all expenses");
        System.out.println("  3. Filter by category");
        System.out.println("  4. Filter by month");
        System.out.println("  5. Monthly summary");
        System.out.println("  6. Delete an expense");
        System.out.println("  7. Exit");
        System.out.println(DIVIDER);
    }

    // ─── Feature Handlers ───────────────────────────────────────────────────

    private static void handleAdd(ExpenseManager manager, InputValidator validator) {
        System.out.println("\n  ── ADD EXPENSE ──");
        String date        = validator.readDate("  Date (YYYY-MM-DD): ");
        String category    = validator.readCategory();
        String description = validator.readString("  Description: ");
        double amount      = validator.readPositiveDouble("  Amount (Rs.): ");
        manager.addExpense(date, category, description, amount);
    }

    private static void handleViewAll(ExpenseManager manager) {
        System.out.println("\n  ── ALL EXPENSES ──");
        List<Expense> all = manager.getAllExpenses();
        if (all.isEmpty()) {
            System.out.println("  No expenses recorded yet.");
            return;
        }
        printExpenseList(all);
        System.out.printf("%n  Total: Rs. %.2f  (%d entries)%n",
                          manager.getGrandTotal(), manager.getExpenseCount());
    }

    private static void handleFilterCategory(ExpenseManager manager, InputValidator validator) {
        System.out.println("\n  ── FILTER BY CATEGORY ──");
        String category = validator.readCategory();
        List<Expense> result = manager.getByCategory(category);
        if (result.isEmpty()) {
            System.out.println("  No expenses found for category: " + category);
            return;
        }
        printExpenseList(result);
        double total = result.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("%n  Total for %s: Rs. %.2f  (%d entries)%n", category, total, result.size());
    }

    private static void handleFilterMonth(ExpenseManager manager, InputValidator validator) {
        System.out.println("\n  ── FILTER BY MONTH ──");
        String month = validator.readMonth("  Month (YYYY-MM): ");
        List<Expense> result = manager.getByMonth(month);
        if (result.isEmpty()) {
            System.out.println("  No expenses found for: " + month);
            return;
        }
        printExpenseList(result);
        System.out.printf("%n  Total for %s: Rs. %.2f  (%d entries)%n",
                          month, manager.getMonthlyTotal(month), result.size());
    }

    private static void handleMonthlySummary(ExpenseManager manager, InputValidator validator) {
        System.out.println("\n  ── MONTHLY SUMMARY ──");
        String month = validator.readMonth("  Month (YYYY-MM): ");
        Map<String, Double> summary = manager.getMonthlySummary(month);
        if (summary.isEmpty()) {
            System.out.println("  No expenses found for: " + month);
            return;
        }
        System.out.println();
        System.out.printf("  %-18s  %s%n", "CATEGORY", "AMOUNT (Rs.)");
        System.out.println(THIN_DIV);
        for (Map.Entry<String, Double> entry : summary.entrySet()) {
            System.out.printf("  %-18s  %.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.println(THIN_DIV);
        System.out.printf("  %-18s  %.2f%n", "TOTAL", manager.getMonthlyTotal(month));
    }

    private static void handleDelete(ExpenseManager manager, InputValidator validator) {
        System.out.println("\n  ── DELETE EXPENSE ──");
        if (manager.getExpenseCount() == 0) {
            System.out.println("  No expenses to delete.");
            return;
        }
        handleViewAll(manager);
        int id = validator.readPositiveInt("\n  Enter ID to delete: ");
        boolean deleted = manager.deleteExpense(id);
        if (deleted) {
            System.out.println("  ✔ Expense #" + id + " deleted successfully.");
        } else {
            System.out.println("  ✘ No expense found with ID: " + id);
        }
    }

    // ─── Helpers ────────────────────────────────────────────────────────────

    private static void printExpenseList(List<Expense> list) {
        System.out.println();
        System.out.printf("  %-5s %-12s %-15s %-25s %s%n",
                          "ID", "DATE", "CATEGORY", "DESCRIPTION", "AMOUNT (Rs.)");
        System.out.println(THIN_DIV);
        for (Expense e : list) {
            System.out.printf("  %-5d %-12s %-15s %-25s %.2f%n",
                              e.getId(), e.getDate(), e.getCategory(),
                              e.getDescription(), e.getAmount());
        }
    }
}
