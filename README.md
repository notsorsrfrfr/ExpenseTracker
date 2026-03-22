#  Personal Expense Tracker

A command-line Java application that helps you log, categorize, filter, and summarize your daily expenses. All data is stored persistently in a local CSV file — no internet, no database required.

Built as a BYOP (Bring Your Own Project) submission for the **Programming in Java** evaluated course.

---

##  Table of Contents

- [Problem Statement](#problem-statement)
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Run](#setup--run)
- [How to Use](#how-to-use)
- [Data Storage](#data-storage)
- [Java Concepts Used](#java-concepts-used)

---

## Problem Statement

Students and young adults often lose track of where their money goes each month. Existing apps require internet access, accounts, or subscriptions. This project provides a simple, offline, terminal-based expense tracker that anyone can run on their own machine.

---

## Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | **Add Expense** | Log amount, category, date, and description |
| 2 | **View All** | Display every recorded expense in a table |
| 3 | **Filter by Category** | See expenses under Food, Transport, Health, etc. |
| 4 | **Filter by Month** | View all expenses in a specific month |
| 5 | **Monthly Summary** | Category-wise totals for any month |
| 6 | **Delete Expense** | Remove a record by its ID |
| 7 | **Persistent Storage** | All data saved to `data/expenses.csv` |

---

## Project Structure

```
ExpenseTracker/
├── src/
│   └── expensetracker/
│       ├── Main.java            # Entry point, menu-driven UI
│       ├── Expense.java         # Expense model (OOP)
│       ├── Category.java        # Enum for expense categories
│       ├── ExpenseManager.java  # Business logic (add, delete, filter, summarize)
│       ├── FileManager.java     # CSV read/write (File I/O)
│       └── InputValidator.java  # Input parsing and validation
├── data/
│   └── expenses.csv             # Auto-created on first run
├── out/                         # Compiled .class files (auto-created)
├── run.sh                       # Compile + run script (Linux/Mac)
├── .gitignore
└── README.md
```

---

## Prerequisites

- **Java JDK 8 or higher**
- Check with: `java -version`
- Download from: https://www.oracle.com/java/technologies/downloads/ or https://adoptium.net/

---

## Setup & Run

### Option 1 — Using the shell script (Linux / macOS)

```bash
git clone https://github.com/notsorsrfrfr/ExpenseTracker.git
cd ExpenseTracker
chmod +x run.sh
./run.sh
```

### Option 2 — Manual commands (Windows / Linux / macOS)

```bash
# 1. Clone the repository
git clone https://github.com/<your-username>/ExpenseTracker.git
cd ExpenseTracker

# 2. Create output directory
mkdir out

# 3. Compile
javac -d out src/expensetracker/*.java

# 4. Run
java -cp out expensetracker.Main
```

### Option 3 — Using an IDE (IntelliJ / Eclipse)

1. Open the project folder in your IDE
2. Mark `src/` as the Sources Root
3. Run `Main.java`

---

## How to Use

On launch, you will see the main menu:

```
─────────────────────────────────────────────────────────────────
  MAIN MENU
·················································~~~~~~~~~~~~~~~~
  1. Add new expense
  2. View all expenses
  3. Filter by category
  4. Filter by month
  5. Monthly summary
  6. Delete an expense
  7. Exit
─────────────────────────────────────────────────────────────────
  Your choice:
```

### Adding an Expense

```
  Date (YYYY-MM-DD): 2025-03-15
  Categories:
    1. FOOD
    2. TRANSPORT
    ...
  Enter category name or number: 1
  Description: Lunch at canteen
  Amount (Rs.): 85
  ✔ Expense added: [1] 2025-03-15 | FOOD            | Lunch at canteen          | Rs. 85.00
```

### Monthly Summary

```
  Month (YYYY-MM): 2025-03

  CATEGORY            AMOUNT (Rs.)
  ·····················~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  FOOD                450.00
  TRANSPORT           120.00
  EDUCATION           500.00
  ·····················~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  TOTAL               1070.00
```

---

## Data Storage

Expenses are saved in `data/expenses.csv`, created automatically on first run.

**Format:**
```
id,date,category,description,amount
1,2025-03-15,FOOD,Lunch at canteen,85.0
2,2025-03-16,TRANSPORT,Bus fare,20.0
```

You can open this file in Excel or Google Sheets to view your data outside the app.

---

## Java Concepts Used

| Concept | Where Applied |
|---------|--------------|
| **OOP — Classes & Encapsulation** | `Expense.java` with private fields and getters/setters |
| **OOP — Enum** | `Category.java` for type-safe categories |
| **OOP — Separation of Concerns** | Manager, Validator, FileManager, Model all separate |
| **File I/O** | `FileManager.java` — `BufferedReader`, `BufferedWriter`, `FileWriter` |
| **Collections** | `ArrayList` in `ExpenseManager`, `LinkedHashMap` for summaries |
| **Exception Handling** | `try-catch` for file errors, number parsing, invalid input |
| **Static Factory Method** | `Expense.fromCSV()` for parsing CSV lines |
| **Iterator** | Used in `deleteExpense()` to safely remove during iteration |

---

## Author

**[Sanidhya Raj]**  
Course: Programming in Java  
Submitted on: [25/03/2026]
