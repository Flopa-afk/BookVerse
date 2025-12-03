import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class mainPage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(mainPage::createAndShow);
    }

    private static void createAndShow() {
        final JFrame frame = new JFrame("BOOKVERSE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(createNavBar(frame), BorderLayout.NORTH);
        frame.add(createHeroPanel(frame), BorderLayout.CENTER);

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
                }
            });
            menuPanel.add(btn);
        }
        
        nav.add(menuPanel, BorderLayout.CENTER);

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        iconPanel.setOpaque(false);
        
        JButton cartBtn = new JButton("🛒");
        cartBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cartBtn.setForeground(Color.WHITE);
        cartBtn.setBackground(new Color(180, 140, 200));
        cartBtn.setFocusPainted(false);
        cartBtn.setBorderPainted(false);
        cartBtn.setContentAreaFilled(false);
        cartBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cartBtn.addActionListener(e -> new CartWindow());
        iconPanel.add(cartBtn);
        
        nav.add(iconPanel, BorderLayout.EAST);

        return nav;
    }

    private static JPanel createHeroPanel(final JFrame frame) {
        JPanel hero = new JPanel(new GridBagLayout());
        hero.setBackground(new Color(250, 250, 252));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 40, 0, 40);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Left panel with text
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeTitle = new JLabel("Welcome To BOOKVERSE");
        welcomeTitle.setFont(new Font("Serif", Font.BOLD, 42));
        welcomeTitle.setForeground(new Color(60, 0, 90));
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeText = new JLabel("<html>"
                + "BOOKVERSE offers a curated selection of essential books<br>"
                + "in three key domains: Islamic knowledge, scientific discovery,<br>"
                + "and computer science innovation.<br>"
                + "It's a destination for readers seeking spiritual insight,<br>"
                + "academic growth, and technical expertise — all in one place."
                + "</html>");
        welcomeText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        welcomeText.setForeground(new Color(45, 45, 55));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeText.setHorizontalAlignment(SwingConstants.CENTER);

        JButton readMore = new JButton("READ MORE");
        readMore.setFont(new Font("SansSerif", Font.BOLD, 15));
        readMore.setForeground(Color.WHITE);
        readMore.setBackground(new Color(120, 0, 180));
        readMore.setFocusPainted(false);
        readMore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        readMore.setAlignmentX(Component.CENTER_ALIGNMENT);
        readMore.addActionListener(e -> {
            frame.dispose();
            BooksCategory.main(new String[]{});
        });

        left.add(Box.createVerticalStrut(10));
        left.add(welcomeTitle);
        left.add(Box.createVerticalStrut(14));
        left.add(welcomeText);
        left.add(Box.createVerticalStrut(24));
        left.add(readMore);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        hero.add(left, gbc);

        // Right panel with book image
        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon bookCover = loadIcon("books.png");
        Image baseImg = bookCover.getImage();
        int bw = Math.max(1, bookCover.getIconWidth());
        int bh = Math.max(1, bookCover.getIconHeight());

        Image scaledImg = baseImg.getScaledInstance((int)(bw * 0.4), (int)(bh * 0.4), Image.SCALE_SMOOTH);
        ImageIcon smallBookCover = new ImageIcon(scaledImg);

        JLabel bookLabel = new JLabel(smallBookCover);
        bookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                BooksCategory.main(new String[]{});
            }
        });

        right.add(Box.createVerticalGlue());
        right.add(bookLabel);
        right.add(Box.createVerticalGlue());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        hero.add(right, gbc);

        return hero;
    }

    private static ImageIcon loadIcon(String path) {
        java.net.URL url = mainPage.class.getResource(path.startsWith("/") ? path : "/" + path);
        if (url != null) return new ImageIcon(url);
        java.io.File f = new java.io.File(path);
        if (f.exists()) return new ImageIcon(path);
        return new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    }
}
