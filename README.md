# Smart Bank Dashboard

**SmartBankDashboard** is a **Java-based desktop banking application** designed to simulate basic banking operations in a simple, interactive, and visually appealing way. Built using **Java Swing** and **FlatLaf**, this project demonstrates modern UI design concepts for desktop applications while providing practical banking functionality.

This program allows users to:
- Deposit money
- Withdraw money
- Check current balance
- View transaction history  

The UI is designed with a sidebar, a balance card, a transaction panel, and a scrollable history panel.

---

## Prerequisites

1. **Java JDK 17 or higher** installed.
2. **FlatLaf JAR**: [flatlaf-3.6.2.jar](https://www.formdev.com/flatlaf/)
3. Your project folder should contain:
    ```
    SmartBankDashboard/
    ├── SmartBankDashboard.java
    └── flatlaf-3.6.2.jar
    ```

---

## How to Run

### 1️⃣ Compile

Open a terminal/command prompt in your project folder and run:

**Windows:**
```bash
javac -cp ".;flatlaf-3.6.2.jar" SmartBankDashboard.java
```

### 2️⃣ Run

**Windows:**
```bash
java -cp ".;flatlaf-3.6.2.jar" SmartBankDashboard
```

After running, the SmartBankDashboard window should open, allowing you to perform transactions and view history.

## Features

- Modern UI with FlatLaf theme
- Sidebar navigation
- Gradient balance card
- Quick transaction panel
- Scrollable transaction history
- Error handling for invalid inputs
