import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class PizzaGUIFrame extends JFrame {
    private JTextArea orderTextArea;
    private JRadioButton thinCrustRadio, regularCrustRadio, deepDishCrustRadio;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppingCheckboxes;
    private JButton orderButton, clearButton, quitButton;
    private ButtonGroup toppingsGroup;

    public PizzaGUIFrame() {
        // Frame setup
        setTitle("Pizza Order System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Create panels
        JPanel orderPanel = new JPanel(new BorderLayout());
        JPanel toppingsPanel = new JPanel(new GridLayout(0, 1));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // JTextArea for displaying order
        orderTextArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        // Create radio buttons for crust type
        thinCrustRadio = new JRadioButton("Thin");
        regularCrustRadio = new JRadioButton("Regular");
        deepDishCrustRadio = new JRadioButton("Deep-dish");
        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustRadio);
        crustGroup.add(regularCrustRadio);
        crustGroup.add(deepDishCrustRadio);

        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        crustPanel.add(thinCrustRadio);
        crustPanel.add(regularCrustRadio);
        crustPanel.add(deepDishCrustRadio);

        // Create JComboBox for pizza size
        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Pizza Size"));
        sizePanel.add(sizeComboBox);

        // Create checkboxes for toppings
        String[] toppings = {"Pepperoni", "Mushrooms", "Olives", "Onions", "Bacon", "Pineapple"};
        toppingCheckboxes = new JCheckBox[toppings.length];
        JPanel toppingsCheckPanel = new JPanel(new GridLayout(0, 1));
        toppingsCheckPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        for (int i = 0; i < toppings.length; i++) {
            toppingCheckboxes[i] = new JCheckBox(toppings[i]);
            toppingsCheckPanel.add(toppingCheckboxes[i]);
        }

        // Add buttons
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate the order and display it in the JTextArea
                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                double baseCost = getBaseCost();
                double toppingCost = getToppingCost();
                double subTotal = baseCost + toppingCost;
                double tax = subTotal * 0.07;
                double total = subTotal + tax;

                orderTextArea.setText(
                        "=========================================\n" +
                                "Type of Crust & Size:\t" + getSelectedCrust() + " " + getSelectedSize() + "\t$" + decimalFormat.format(baseCost) + "\n" +
                                "Ingredient\t\tPrice\n" +
                                getSelectedToppings() +
                                "Sub-total:\t$" + decimalFormat.format(subTotal) + "\n" +
                                "Tax:\t$" + decimalFormat.format(tax) + "\n" +
                                "-------------------------------------\n" +
                                "Total:\t$" + decimalFormat.format(total) + "\n" +
                                "=========================================\n"
                );
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(PizzaGUIFrame.this,
                        "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add panels to the frame
        add(orderPanel, BorderLayout.NORTH);
        add(crustPanel, BorderLayout.WEST);
        add(sizePanel, BorderLayout.EAST);
        add(toppingsCheckPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private double getBaseCost() {
        String size = (String) sizeComboBox.getSelectedItem();
        switch (size) {
            case "Small":
                return 8.00;
            case "Medium":
                return 12.00;
            case "Large":
                return 16.00;
            case "Super":
                return 20.00;
            default:
                return 0.00;
        }
    }

    private String getSelectedCrust() {
        if (thinCrustRadio.isSelected()) {
            return "Thin";
        } else if (regularCrustRadio.isSelected()) {
            return "Regular";
        } else if (deepDishCrustRadio.isSelected()) {
            return "Deep-dish";
        } else {
            return "Unknown";
        }
    }

    private String getSelectedSize() {
        return (String) sizeComboBox.getSelectedItem();
    }

    private double getToppingCost() {
        double toppingCost = 0.0;
        for (JCheckBox checkbox : toppingCheckboxes) {
            if (checkbox.isSelected()) {
                toppingCost += 1.00;
            }
        }
        return toppingCost;
    }

    private String getSelectedToppings() {
        StringBuilder toppings = new StringBuilder();
        for (JCheckBox checkbox : toppingCheckboxes) {
            if (checkbox.isSelected()) {
                toppings.append(checkbox.getText()).append("\t\t$1.00\n");
            }
        }
        return toppings.toString();
    }

    private void clearForm() {
        orderTextArea.setText("");
        toppingsGroup.clearSelection();
        sizeComboBox.setSelectedIndex(0);
    }

}
