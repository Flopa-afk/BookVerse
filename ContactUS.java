import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ContactUS {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactUS::createAndShow);
    }

    private static void createAndShow() {
        final JFrame frame = new JFrame("BOOKVERSE - Contact Us");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(createNavBar(frame), BorderLayout.NORTH);
        frame.add(createContactPanel(frame), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static JPanel createNavBar(final JFrame frame) {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(new Color(180, 140, 200));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 10));

        JLabel logo = new JLabel("📖 BOOKVERSE");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                mainPage.main(new String[]{});
            }
        });
        nav.add(logo, BorderLayout.WEST);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 5));
        menuPanel.setOpaque(false);
        String[] items = {"HOME", "BOOKS", "NEW RELEASE", "CONTACT US"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(180, 140, 200));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            btn.addActionListener(e -> {
                switch (item) {
                    case "HOME":
                        frame.dispose();
                        mainPage.main(new String[]{});
                        break;
                    case "BOOKS":
                        frame.dispose();
                        BooksCategory.main(new String[]{});
                        break;
                    case "NEW RELEASE":
                        frame.dispose();
                        NewRelease.main(new String[]{});
                        break;
                    case "CONTACT US":
                        frame.dispose();
                        ContactUS.main(new String[]{});
                        break;
                }
            });
            menuPanel.add(btn);
        }
        nav.add(menuPanel, BorderLayout.CENTER);

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        iconPanel.setOpaque(false);
        nav.add(iconPanel, BorderLayout.EAST);

        return nav;
    }

    private static JPanel createContactPanel(final JFrame frame) {
        JPanel contact = new JPanel(new GridBagLayout());
        contact.setBackground(new Color(250, 250, 252));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        card.setMaximumSize(new Dimension(600, 500));

        JLabel title = new JLabel("Contact Us");
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(new Color(60, 0, 90));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        card.add(Box.createVerticalStrut(24));

        JLabel emailLabel = new JLabel("📧 Email:");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        emailLabel.setForeground(new Color(70, 70, 90));
        card.add(emailLabel);

        JLabel email = new JLabel("info@bookverse.com");
        email.setFont(new Font("SansSerif", Font.PLAIN, 14));
        email.setForeground(new Color(100, 100, 120));
        card.add(email);

        card.add(Box.createVerticalStrut(16));

        JLabel phoneLabel = new JLabel("📞 Phone:");
        phoneLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        phoneLabel.setForeground(new Color(70, 70, 90));
        card.add(phoneLabel);

        JLabel phone = new JLabel("+213 123 456 789");
        phone.setFont(new Font("SansSerif", Font.PLAIN, 14));
        phone.setForeground(new Color(100, 100, 120));
        card.add(phone);

        card.add(Box.createVerticalStrut(16));

        JLabel addressLabel = new JLabel("📍 Address:");
        addressLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        addressLabel.setForeground(new Color(70, 70, 90));
        card.add(addressLabel);

        JLabel address = new JLabel("El biar, Algiers, Algeria");
        address.setFont(new Font("SansSerif", Font.PLAIN, 14));
        address.setForeground(new Color(100, 100, 120));
        card.add(address);

        card.add(Box.createVerticalStrut(16));

        JLabel hoursLabel = new JLabel("⏰ Hours:");
        hoursLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        hoursLabel.setForeground(new Color(70, 70, 90));
        card.add(hoursLabel);

        JLabel hours = new JLabel("Monday - Friday: 9:00 AM - 6:00 PM");
        hours.setFont(new Font("SansSerif", Font.PLAIN, 14));
        hours.setForeground(new Color(100, 100, 120));
        card.add(hours);

        card.add(Box.createVerticalGlue());

        JButton back = new JButton("Back to Home");
        back.setBackground(new Color(120, 0, 180));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        back.setFont(new Font("SansSerif", Font.BOLD, 14));
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(e -> {
            frame.dispose();
            mainPage.main(new String[]{});
        });
        card.add(back);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contact.add(card, gbc);

        return contact;
    }
}