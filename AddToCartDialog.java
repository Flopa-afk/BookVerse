import javax.swing.*;
import java.awt.*;

public class AddToCartDialog extends JDialog {
    private boolean confirmed = false;

    public AddToCartDialog(JFrame parent, String bookTitle, String price) {
        super(parent, "Add to Cart", true);
        setSize(420, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        // Top banner
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(88, 50, 130));
        top.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        JLabel h = new JLabel("Confirm Your Selection");
        h.setForeground(Color.WHITE);
        h.setFont(new Font("SansSerif", Font.BOLD, 16));
        top.add(h, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);

        // Center content
        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        center.setBackground(Color.WHITE);

        JLabel title = new JLabel(bookTitle);
        title.setFont(new Font("Serif", Font.BOLD, 18));
        title.setForeground(new Color(30, 30, 30));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLabel.setForeground(new Color(40, 120, 160));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.add(title);
        info.add(Box.createVerticalStrut(8));
        info.add(priceLabel);
        info.add(Box.createVerticalStrut(10));

        JLabel note = new JLabel("You can adjust quantity later in your cart.");
        note.setFont(new Font("SansSerif", Font.PLAIN, 12));
        note.setForeground(new Color(120, 120, 120));
        info.add(note);

        center.add(info, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBorder(BorderFactory.createEmptyBorder(8, 8, 12, 12));
        buttons.setBackground(Color.WHITE);

        JButton cancel = new JButton("Cancel");
        cancel.setBackground(new Color(230,230,230));
        cancel.setFocusPainted(false);
        cancel.addActionListener(e -> { confirmed = false; dispose(); });

        JButton addCart = new JButton("Add to Cart");
        addCart.setBackground(new Color(40, 120, 160));
        addCart.setForeground(Color.WHITE);
        addCart.setFocusPainted(false);
        addCart.addActionListener(e -> { confirmed = true; dispose(); });

        buttons.add(cancel);
        buttons.add(addCart);

        add(buttons, BorderLayout.SOUTH);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getQuantity() {
        return 1;
    }
}



