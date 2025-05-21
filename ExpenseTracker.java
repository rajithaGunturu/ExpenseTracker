import java.io.*;
import java.util.*;

public class ExpenseTracker {

    static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose input mode:");
        System.out.println("1. Enter transactions manually");
        System.out.println("2. Load transactions from file");
        int choice = sc.nextInt();
        sc.nextLine(); // clear buffer

        if (choice == 1) {
            manualInput(sc);
        } else if (choice == 2) {
            loadFromFile();
        } else {
            System.out.println("Invalid choice");
            sc.close();
            return;
        }

        printSummary();

        sc.close();
    }

    public static void manualInput(Scanner sc) {
        System.out.print("How many transactions do you want to enter? ");
        int n = sc.nextInt();
        sc.nextLine(); // clear buffer

        for (int i = 0; i < n; i++) {
            System.out.println("Transaction " + (i + 1));

            System.out.print("Type (Income/Expense): ");
            String type = sc.nextLine();

            System.out.print("Category (e.g., Salary, Food, Rent): ");
            String category = sc.nextLine();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // clear buffer

            System.out.print("Date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            transactions.add(new Transaction(type, category, amount, date));
        }
    }

    public static void loadFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String type = parts[0];
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String date = parts[3];

                transactions.add(new Transaction(type, category, amount, date));
            }
        }
        reader.close();
    }

    public static void printSummary() {
        double totalIncome = 0;
        double totalExpense = 0;
        HashMap<String, Double> expenseByCategory = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.type.equalsIgnoreCase("Income")) {
                totalIncome += t.amount;
            } else if (t.type.equalsIgnoreCase("Expense")) {
                totalExpense += t.amount;
                expenseByCategory.put(t.category,
                    expenseByCategory.getOrDefault(t.category, 0.0) + t.amount);
            }
        }

        double balance = totalIncome - totalExpense;

        System.out.println("\n----- Monthly Summary -----");
        System.out.println("Total Income  : ₹" + totalIncome);
        System.out.println("Total Expense : ₹" + totalExpense);
        System.out.println("Balance       : ₹" + balance);

        System.out.println("\nCategory-wise Expenses:");
        for (String category : expenseByCategory.keySet()) {
            System.out.println(category + " : ₹" + expenseByCategory.get(category));
        }
    }
}
