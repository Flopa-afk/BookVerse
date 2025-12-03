import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.text.NumberFormat;
import java.util.Locale;

public class CartWindow {
    private JFrame frame;
    private JLabel headerCountLabel;
    private JLabel headerTotalLabel;
    private JLabel rightItemsLabel;
    private JLabel rightSubtotalLabel;
    private JLabel rightTaxLabel;
    private JLabel rightTotalLabel;
    private JPanel leftContainer;

    public CartWindow() {
        frame = new JFrame("BookVerse - Cart");
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(createHeader(), BorderLayout.NORTH);
        frame.add(createContent(), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel title = new JLabel("Your Cart");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(40, 40, 40));
        p.add(title, BorderLayout.WEST);

        // right side: small summary
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setOpaque(false);
        int totalItems = CartManager.getItems().stream().mapToInt(it -> it.quantity).sum();
        headerCountLabel = new JLabel(totalItems + " items");
        headerCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        headerCountLabel.setForeground(new Color(100, 100, 100));

        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
        headerTotalLabel = new JLabel(nf.format(CartManager.getTotal()) + " DA");
        headerTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        headerTotalLabel.setForeground(new Color(40, 120, 160));

        right.add(headerCountLabel);
        right.add(headerTotalLabel);
        p.add(right, BorderLayout.EAST);

        return p;
    }

    private JSplitPane createContent() {
        leftContainer = new JPanel();
        leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.Y_AXIS));
        leftContainer.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        populateLeftItems();

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder(24,24,24,24));
        right.setBackground(Color.WHITE);

        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
        java.util.List<CartManager.CartItem> items = CartManager.getItems();
        int totalCount = items.stream().mapToInt(it -> it.quantity).sum();

        JLabel summary = new JLabel("Order Summary");
        summary.setFont(new Font("SansSerif", Font.BOLD, 16));
        summary.setAlignmentX(Component.LEFT_ALIGNMENT);
        right.add(summary);
        right.add(Box.createVerticalStrut(12));

        rightItemsLabel = new JLabel("Items: " + totalCount);
        rightItemsLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        rightItemsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        right.add(rightItemsLabel);
        right.add(Box.createVerticalStrut(8));

        int subtotalVal = CartManager.getTotal();
        rightSubtotalLabel = new JLabel("Subtotal: " + nf.format(subtotalVal) + " DA");
        rightSubtotalLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        rightSubtotalLabel.setForeground(new Color(40, 120, 160));
        rightSubtotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        right.add(rightSubtotalLabel);
        right.add(Box.createVerticalStrut(6));

        int taxVal = Math.round(subtotalVal * 0.10f);
        rightTaxLabel = new JLabel("Tax (10%): " + nf.format(taxVal) + " DA");
        rightTaxLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        rightTaxLabel.setForeground(new Color(120,120,120));
        rightTaxLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        right.add(rightTaxLabel);
        right.add(Box.createVerticalStrut(12));

        int finalTotal = subtotalVal + taxVal;
        rightTotalLabel = new JLabel("Total: " + nf.format(finalTotal) + " DA");
        rightTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        rightTotalLabel.setForeground(new Color(30, 90, 120));
        rightTotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        right.add(rightTotalLabel);
        right.add(Box.createVerticalStrut(16));

        JButton checkout = new JButton("Checkout");
        checkout.setBackground(new Color(40, 120, 160));
        checkout.setForeground(Color.WHITE);
        checkout.setFocusPainted(false);
        checkout.setFont(new Font("SansSerif", Font.BOLD, 13));
        checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkout.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        checkout.addActionListener(e -> new CheckoutWindow(subtotalVal, totalCount));
        right.add(checkout);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(leftContainer), right);
        split.setResizeWeight(0.75);
        split.setBorder(null);
        return split;
    }

    private void populateLeftItems() {
        leftContainer.removeAll();
        java.util.List<CartManager.CartItem> items = CartManager.getItems();
        if (items.isEmpty()) {
            JLabel empty = new JLabel("Your cart is empty.");
            empty.setFont(new Font("SansSerif", Font.PLAIN, 16));
            empty.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
            leftContainer.add(empty);
        } else {
            for (int i = 0; i < items.size(); i++) {
                CartManager.CartItem it = items.get(i);
                leftContainer.add(createItemPanel(it, i));
                leftContainer.add(Box.createVerticalStrut(10));
            }
        }
        leftContainer.revalidate();
        leftContainer.repaint();
    }

    private JPanel createItemPanel(CartManager.CartItem it, int index) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230,230,230)),
            BorderFactory.createEmptyBorder(8,8,8,8)));

        ImageIcon icon = loadIcon(it.imagePath);
        ImageIcon thumb = scaleIcon(icon, 72, 96);
        JLabel img = new JLabel(thumb);
        img.setBorder(BorderFactory.createEmptyBorder(0,0,0,12));
        img.setVerticalAlignment(SwingConstants.TOP);
        p.add(img, BorderLayout.WEST);

        JPanel mid = new JPanel();
        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
        mid.setOpaque(false);
        mid.setAlignmentY(Component.TOP_ALIGNMENT);
        JLabel t = new JLabel(it.title);
        t.setFont(new Font("Serif", Font.BOLD, 14));
        mid.add(t);
        JLabel a = new JLabel(it.author);
        a.setFont(new Font("SansSerif", Font.PLAIN, 12));
        a.setForeground(new Color(110,110,110));
        mid.add(a);
        JLabel d = new JLabel("Added: " + it.dateAdded);
        d.setFont(new Font("SansSerif", Font.PLAIN, 11));
        d.setForeground(new Color(140,140,140));
        mid.add(d);
        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
        JLabel price = new JLabel(nf.format(it.priceDA * it.quantity) + " DA");
        price.setFont(new Font("SansSerif", Font.BOLD, 14));

        JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(it.quantity, 1, 100, 1));
        qtySpinner.setPreferredSize(new Dimension(60, 22));
        qtySpinner.setEnabled(!it.confirmed);
        qtySpinner.addChangeListener(e -> {
            int newQty = (Integer) qtySpinner.getValue();
            CartManager.updateQuantity(index, newQty);
            // update this row price label
            price.setText(nf.format(it.priceDA * newQty) + " DA");
            // refresh summary labels
            refreshSummary();
        });
        JPanel qtyWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        qtyWrap.setOpaque(false);
        // only show spinner (no "Qty:" label) for a cleaner look
        qtyWrap.add(qtySpinner);
        mid.add(qtyWrap);

        p.add(mid, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JPanel qtyRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        qtyRow.setOpaque(false);
        qtyRow.add(new JLabel("Qty:"));
        qtyRow.add(qtySpinner);

        JButton confirmBtn = new JButton(it.confirmed ? "Confirmed" : "Confirm");
        confirmBtn.setEnabled(!it.confirmed);
        confirmBtn.setBackground(it.confirmed ? new Color(200,200,200) : new Color(40, 180, 99));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFocusPainted(false);
        confirmBtn.addActionListener(e -> {
            CartManager.setConfirmed(index, true);
            qtySpinner.setEnabled(false);
            confirmBtn.setText("Confirmed");
            confirmBtn.setBackground(new Color(200,200,200));
        });
        qtyRow.add(confirmBtn);

        rightPanel.add(qtyRow);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(price);
        rightPanel.add(Box.createVerticalStrut(8));

        JButton removeBtn = new JButton("Remove");
        removeBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        removeBtn.setBackground(new Color(230, 80, 90));
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setFocusPainted(false);
        removeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeBtn.addActionListener(e -> {
            CartManager.removeItem(index);
            populateLeftItems();
            refreshSummary();
        });
        rightPanel.add(removeBtn);

        p.add(rightPanel, BorderLayout.EAST);

        return p;
    }

    private ImageIcon loadIcon(String path) {
        if (path == null) return new ImageIcon(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
        // Try as resource on classpath
        try {
            java.net.URL url = CartWindow.class.getResource(path.startsWith("/") ? path : "/" + path);
            if (url != null) {
                try {
                    BufferedImage img = ImageIO.read(url);
                    if (img != null) return new ImageIcon(img);
                } catch (IOException ignored) {}
                return new ImageIcon(url);
            }
        } catch (Exception ignored) {}

        // Try as file path
        try {
            File f = new File(path);
            if (f.exists()) {
                BufferedImage img = ImageIO.read(f);
                if (img != null) return new ImageIcon(img);
            }
            // try normalized path (replace backslashes)
            String p2 = path.replace('\\', '/');
            File f2 = new File(p2);
            if (f2.exists()) {
                BufferedImage img = ImageIO.read(f2);
                if (img != null) return new ImageIcon(img);
            }
        } catch (IOException ignored) {}

        // Fallback empty image
        return new ImageIcon(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
    }

    private ImageIcon scaleIcon(ImageIcon icon, int w, int h) {
        if (icon == null || icon.getIconWidth() <= 0) {
            return new ImageIcon(new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB));
        }
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void refreshSummary() {
        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
        java.util.List<CartManager.CartItem> items = CartManager.getItems();
        int totalCount = items.stream().mapToInt(it -> it.quantity).sum();
        int subtotalVal = CartManager.getTotal();
        int taxVal = Math.round(subtotalVal * 0.10f);
        int finalTotal = subtotalVal + taxVal;

        if (headerCountLabel != null) headerCountLabel.setText(totalCount + " items");
        if (headerTotalLabel != null) headerTotalLabel.setText(nf.format(subtotalVal) + " DA");

        if (rightItemsLabel != null) rightItemsLabel.setText("Items: " + totalCount);
        if (rightSubtotalLabel != null) rightSubtotalLabel.setText("Subtotal: " + nf.format(subtotalVal) + " DA");
        if (rightTaxLabel != null) rightTaxLabel.setText("Tax (10%): " + nf.format(taxVal) + " DA");
        if (rightTotalLabel != null) rightTotalLabel.setText("Total: " + nf.format(finalTotal) + " DA");
    }
}
