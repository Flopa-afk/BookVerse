import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.sql.SQLException;

public class CheckoutWindow {
    private JFrame frame;

    public CheckoutWindow(int totalPrice, int totalItems) {
        frame = new JFrame("BookVerse - Checkout");
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createHeader(), BorderLayout.NORTH);
        frame.add(createContent(totalPrice, totalItems), BorderLayout.CENTER);

        // Auto-create database and schema on first run
        DBHelper.initializeDatabase();

        frame.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel title = new JLabel("Checkout");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(30, 30, 30));
        p.add(title, BorderLayout.WEST);

        return p;
    }

    private JPanel createContent(int totalPrice, int totalItems) {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);

        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

        // Summary panel (top)
        JPanel summary = new JPanel();
        summary.setLayout(new BoxLayout(summary, BoxLayout.Y_AXIS));
        summary.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        summary.setBackground(Color.WHITE);

        JLabel ordTitle = new JLabel("Order Summary");
        ordTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        ordTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.add(ordTitle);
        summary.add(Box.createVerticalStrut(10));

        // Recompute totals from CartManager to ensure quantities are counted
        java.util.List<CartManager.CartItem> itemsList = CartManager.getItems();
        int computedItems = itemsList.stream().mapToInt(it -> it.quantity).sum();
        int computedSubtotal = CartManager.getTotal();

        JLabel items = new JLabel("Items: " + computedItems);
        items.setFont(new Font("SansSerif", Font.PLAIN, 13));
        items.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.add(items);
        summary.add(Box.createVerticalStrut(6));

        JLabel subtotal = new JLabel("Subtotal: " + nf.format(computedSubtotal) + " DA");
        subtotal.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.add(subtotal);
        summary.add(Box.createVerticalStrut(6));
        int taxVal = Math.round(computedSubtotal * 0.10f);
        JLabel tax = new JLabel("Tax (10%): " + nf.format(taxVal) + " DA");
        tax.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tax.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.add(tax);
        summary.add(Box.createVerticalStrut(8));

        int finalTotal = computedSubtotal + taxVal;
        JLabel total = new JLabel("Total: " + nf.format(finalTotal) + " DA");
        total.setFont(new Font("SansSerif", Font.BOLD, 16));
        total.setForeground(new Color(30, 90, 120));
        total.setAlignmentX(Component.LEFT_ALIGNMENT);
        summary.add(total);

        main.add(summary, BorderLayout.WEST);

        // Form panel (right)
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230,230,230)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        form.setBackground(Color.WHITE);

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        nameField.setBorder(BorderFactory.createTitledBorder("Full name"));
        form.add(nameField);
        form.add(Box.createVerticalStrut(10));

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        form.add(emailField);
        form.add(Box.createVerticalStrut(10));

        JTextField addressField = new JTextField();
        addressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        addressField.setBorder(BorderFactory.createTitledBorder("Shipping address"));
        form.add(addressField);
        form.add(Box.createVerticalStrut(16));

        // Phone field
        JTextField phoneField = new JTextField();
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        phoneField.setBorder(BorderFactory.createTitledBorder("Phone number"));
        form.add(phoneField);
        form.add(Box.createVerticalStrut(12));

        JButton placeOrder = new JButton("Place Order — " + nf.format(finalTotal) + " DA");
        placeOrder.setFont(new Font("SansSerif", Font.BOLD, 14));
        placeOrder.setBackground(new Color(40, 120, 160));
        placeOrder.setForeground(Color.WHITE);
        placeOrder.setFocusPainted(false);
        placeOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeOrder.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        placeOrder.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields (including phone).", "Incomplete", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Recompute final total at confirmation time to be safe
            int currentSubtotal = CartManager.getTotal();
            int currentTax = Math.round(currentSubtotal * 0.10f);
            int currentFinal = currentSubtotal + currentTax;
            // Generate order metadata
            String orderNum = "ORD-" + System.currentTimeMillis();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Save client -> order -> order_items in DB
            try {
                int clientId = DBHelper.insertClient(name, email, address, phone);
                if (clientId == -1) {
                    JOptionPane.showMessageDialog(frame, "Failed to save client to database.", "DB Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int orderId = DBHelper.insertOrder(clientId, orderNum, date, currentFinal);
                if (orderId == -1) {
                    JOptionPane.showMessageDialog(frame, "Failed to create order record.", "DB Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean itemsOk = DBHelper.insertOrderItems(orderId, CartManager.getItems());
                if (!itemsOk) {
                    JOptionPane.showMessageDialog(frame, "Failed to save order items.", "DB Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            showOrderConfirmation(orderNum, date, currentFinal);
        });

        form.add(placeOrder);

        main.add(form, BorderLayout.EAST);

        // Footer: cancel
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        JButton cancel = new JButton("Cancel");
        cancel.setBackground(new Color(230,230,230));
        cancel.setFocusPainted(false);
        cancel.addActionListener(e -> frame.dispose());
        footer.add(cancel);
        main.add(footer, BorderLayout.SOUTH);

        return main;
    }

    private void showOrderConfirmation(String orderNum, String date, int total) {
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.Y_AXIS));
        confirmPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel success = new JLabel("✓ Order Placed Successfully");
        success.setFont(new Font("SansSerif", Font.BOLD, 18));
        success.setForeground(new Color(76, 175, 80));
        success.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(success);

        confirmPanel.add(Box.createVerticalStrut(16));

        JLabel orderNum_lbl = new JLabel("Order Number: " + orderNum);
        orderNum_lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        orderNum_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(orderNum_lbl);

        JLabel orderDate = new JLabel("Date: " + date);
        orderDate.setFont(new Font("SansSerif", Font.PLAIN, 14));
        orderDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(orderDate);

        JLabel totalLabel = new JLabel("Total Amount: " + total + " DA");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPanel.add(totalLabel);

        JOptionPane.showMessageDialog(frame, confirmPanel, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
        CartManager.clear();
        frame.dispose();
    }
}
