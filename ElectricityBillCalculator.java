
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ElectricityBillCalculator extends JFrame {

    private JTextField nameField;
    private JTextField consumerField;
    private JTextField unitsField;
    private JTextArea resultArea;

    private final Color blue = new Color(35, 91, 190);
    private final Color darkBlue = new Color(23, 52, 105);
    private final Color background = new Color(242, 246, 252);

    public ElectricityBillCalculator() {

        setTitle("Electricity Bill Calculator");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(background);

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(darkBlue);
        header.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel icon = new JLabel("⚡");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 32));
        icon.setForeground(Color.WHITE);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("ELECTRICITY BILL");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Smart Electricity Bill Calculator");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(icon);
        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        // Input form
        JPanel form = new JPanel(new GridLayout(0, 1, 8, 8));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 25, 20, 25));

        nameField = new JTextField();
        consumerField = new JTextField();
        unitsField = new JTextField();

        addField(form, "Consumer Name", nameField);
        addField(form, "Consumer Number", consumerField);
        addField(form, "Units Consumed", unitsField);

        // Buttons
        JButton calculateButton = new JButton("Calculate Bill");
        calculateButton.setBackground(blue);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        calculateButton.setFocusPainted(false);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        clearButton.setFocusPainted(false);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 12, 0));
        buttons.setBackground(Color.WHITE);
        buttons.add(calculateButton);
        buttons.add(clearButton);
        form.add(buttons);

        // Result section
        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBackground(new Color(246, 249, 255));
        resultArea.setBorder(new EmptyBorder(12, 12, 12, 12));
        resultArea.setText("Your bill summary will appear here.");

        JPanel resultPanel = new JPanel(new BorderLayout(0, 8));
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(new EmptyBorder(15, 25, 20, 25));

        JLabel resultTitle = new JLabel("BILL SUMMARY");
        resultTitle.setFont(new Font("SansSerif", Font.BOLD, 17));
        resultTitle.setForeground(darkBlue);

        resultPanel.add(resultTitle, BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setBackground(background);
        center.setBorder(new EmptyBorder(18, 22, 10, 22));
        center.add(form, BorderLayout.NORTH);
        center.add(resultPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel(
                "Electricity Bill Calculator | Java Project",
                SwingConstants.CENTER);
        footer.setBorder(new EmptyBorder(10, 5, 15, 5));
        footer.setForeground(Color.GRAY);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);

        calculateButton.addActionListener(e -> calculateBill());

        clearButton.addActionListener(e -> {
            nameField.setText("");
            consumerField.setText("");
            unitsField.setText("");
            resultArea.setText("Your bill summary will appear here.");
        });
    }

    private void addField(
            JPanel panel, String labelText, JTextField field) {

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(darkBlue);

        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 32));

        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(fieldPanel);
    }

    private void calculateBill() {

        String name = nameField.getText().trim();
        String consumer = consumerField.getText().trim();
        String unitsText = unitsField.getText().trim();

        if (name.isEmpty() || consumer.isEmpty()
                || unitsText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please fill in all fields.");
            return;
        }

        int units;

        try {
            units = Integer.parseInt(unitsText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter a valid whole number for units.");
            return;
        }

        if (units < 0) {
            JOptionPane.showMessageDialog(
                    this, "Units cannot be negative.");
            return;
        }

        double bill;

        if (units <= 100) {
            bill = units * 2.0;
        } else if (units <= 200) {
            bill = 200 + (units - 100) * 3.0;
        } else if (units <= 300) {
            bill = 500 + (units - 200) * 5.0;
        } else {
            bill = 1000 + (units - 300) * 7.0;
        }

        resultArea.setText(
                "       ELECTRICITY BILL RECEIPT\n"
                + "====================================\n"
                + "Consumer Name : " + name + "\n"
                + "Consumer No.  : " + consumer + "\n"
                + "Units Used    : " + units + "\n"
                + "------------------------------------\n"
                + String.format("Total Amount  : Rs. %.2f%n", bill)
                + "====================================\n"
                + "Thank you!");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ElectricityBillCalculator app =
                    new ElectricityBillCalculator();
            app.setVisible(true);
        });
    }
}
