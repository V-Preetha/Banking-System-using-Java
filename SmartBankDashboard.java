import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.formdev.flatlaf.FlatLightLaf;

public class SmartBankDashboard {

    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea historyArea;
    private JTextField amountField;
    private JLabel balanceLabel;
    private double balance = 0.0;
    private ArrayList<String> history = new ArrayList<>();

    public SmartBankDashboard() {
        // Apply FlatLaf look and feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("🏦 Smart Banking Dashboard");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top gradient bar
        JPanel topBar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0x2193b0),
                        getWidth(), 0, new Color(0x6dd5ed));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topBar.setPreferredSize(new Dimension(frame.getWidth(), 60));
        topBar.setLayout(new BorderLayout());
        JLabel title = new JLabel("Smart Banking Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        topBar.add(title, BorderLayout.CENTER);
        frame.add(topBar, BorderLayout.NORTH);

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(5, 1, 0, 10));
        sidebar.setBackground(new Color(245, 245, 245));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));

        JButton dashboardBtn = createSidebarButton("Dashboard");
        JButton transactionsBtn = createSidebarButton("Transactions");
        JButton settingsBtn = createSidebarButton("Settings");
        JButton exitBtn = createSidebarButton("Exit");

        sidebar.add(dashboardBtn);
        sidebar.add(transactionsBtn);
        sidebar.add(settingsBtn);
        sidebar.add(exitBtn);

        frame.add(sidebar, BorderLayout.WEST);

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel dashTitle = new JLabel("Account Overview", SwingConstants.CENTER);
        dashTitle.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
        dashTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(dashTitle);
        mainPanel.add(Box.createVerticalStrut(15));

        // Transaction input panel
        JPanel transactionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Quick Transaction"));
        amountField = new JTextField(10);
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");

        transactionPanel.add(new JLabel("Amount (₹):"));
        transactionPanel.add(amountField);
        transactionPanel.add(depositBtn);
        transactionPanel.add(withdrawBtn);
        transactionPanel.add(balanceBtn);
        mainPanel.add(transactionPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Transaction history
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        historyArea = new JTextArea(10, 40);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(historyPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Bottom balance label
        balanceLabel = new JLabel("Current Balance: ₹0.00", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        balanceLabel.setForeground(new Color(0x1E90FF));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(balanceLabel);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Event handling
        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        balanceBtn.addActionListener(e -> checkBalance());
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBackground(new Color(250, 250, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(230, 230, 230));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(250, 250, 250));
            }
        });
        return button;
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) throw new NumberFormatException();
            balance += amount;
            addHistory("Deposited ₹" + amount);
            updateBalance();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Enter a valid positive number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0 || amount > balance) throw new NumberFormatException();
            balance -= amount;
            addHistory("Withdrew ₹" + amount);
            updateBalance();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid amount or insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkBalance() {
        JOptionPane.showMessageDialog(frame, "Your current balance is ₹" + balance, "Balance Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addHistory(String entry) {
        history.add(entry);
        historyArea.setText(String.join("\n", history));
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }

    private void updateBalance() {
        balanceLabel.setText("Current Balance: ₹" + String.format("%.2f", balance));
        amountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartBankDashboard::new);
    }
}

