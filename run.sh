#!/bin/bash
# Compile and run the Personal Expense Tracker

echo "Compiling..."
mkdir -p out
javac -d out src/expensetracker/*.java

if [ $? -eq 0 ]; then
    echo "Running..."
    java -cp out expensetracker.Main
else
    echo "Compilation failed. Please check your Java version (requires Java 8+)."
fi
