# Expense Tracker CLI (https://roadmap.sh/projects/expense-tracker)

This is a simple CLI (command-line interface) for tracking user's expenses, stored in a .csv file

## Installation

1. **Clone the repository:**
    ```bash
   git clone https://github.com/java-backend-projects/Expense-Tracker
   cd Expense-Tracker
    ```
2. **Unzip archive with .jar file and executable script:**
    ```bash
   unzip expense-tracker.zip
    ```

## Usage

```bash
./expense-tracker add --description "Lunch" --amount 20
# Expense added successfully (ID: 1)

./expense-tracker add --description "Dinner" --amount 10
# Expense added successfully (ID: 2)

./expense-tracker update --amount 23.5 --description "Breakfast" 1

./expense-tracker list
# ID  Date       Description  Amount
# 1   2024-08-06 Breakfast    $23.5
# 2   2024-08-06 Dinner       $10.0

./expense-tracker summary
# Total expenses: $33.5

./expense-tracker delete 1
# Expense deleted successfully

./expense-tracker summary
# Total expenses: $10.0

./expense-tracker summary --month 8
# Total expenses for August: $10
```