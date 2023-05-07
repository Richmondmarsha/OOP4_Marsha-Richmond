import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverterGUI extends JFrame {
    private JTextField inputField;
    private JTextField resultField;
    private JComboBox<String> currencyComboBox;
    private JButton convertButton;
    private JButton clearButton;

    private static final double CAN_TO_JMD = 97.50;
    private static final double US_TO_JMD = 129.02;
    private static final double EURO_TO_JMD = 164.33;

    public CurrencyConverterGUI() {
        setTitle("Currency Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        addComponents();
        addListeners();
    }

    private void initComponents() {
        inputField = new JTextField(10);
        resultField = new JTextField(10);
        resultField.setEditable(false);

        currencyComboBox = new JComboBox<>(new String[]{"USD", "CAD", "EUR"});
        currencyComboBox.setSelectedIndex(0);

        convertButton = new JButton("Convert");
        clearButton = new JButton("Clear");
    }

    private void addComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Input $:"));
        panel.add(inputField);
        panel.add(new JLabel("JMD Amount $:"));
        panel.add(resultField);
        panel.add(currencyComboBox);
        panel.add(convertButton);
        panel.add(clearButton);

        add(panel);
    }

    private void addListeners() {
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void convertCurrency() {
        String inputText = inputField.getText();
        if (inputText.isEmpty()) {
            showError("Please enter an amount to convert.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter a valid number.");
            return;
        }

        String selectedCurrency = (String) currencyComboBox.getSelectedItem();
        double conversionRate;
        switch (selectedCurrency) {
            case "USD":
                conversionRate = US_TO_JMD;
                break;
            case "CAD":
                conversionRate = CAN_TO_JMD;
                break;
            case "EUR":
                conversionRate = EURO_TO_JMD;
                break;
            default:
                showError("Invalid currency selection.");
                return;
        }

        double result = amount * conversionRate;
        resultField.setText(String.format("%.2f", result));
    }

    private void clearFields() {
        inputField.setText("");
        resultField.setText("");
        currencyComboBox.setSelectedIndex(0);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverterGUI().setVisible(true);
            }
        });
    }
}
