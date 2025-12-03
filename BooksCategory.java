import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BooksCategory {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BooksCategory::createAndShow);
    }

    private static void createAndShow() {
        final JFrame frame = new JFrame("BOOKVERSE - Books");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(createNavBar(frame), BorderLayout.NORTH);
        frame.add(createCenterPanel(frame), BorderLayout.CENTER);
        frame.add(createBottomBar(frame), BorderLayout.SOUTH);

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

            if ("BOOKS".equals(item)) {
                btn.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
            }

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

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        right.setOpaque(false);
        nav.add(right, BorderLayout.EAST);

        return nav;
    }

    private static JPanel createCenterPanel(final JFrame frame) {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(250, 250, 252));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(24, 24, 24, 24);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JPanel grid = new JPanel(new GridLayout(3, 2, 20, 20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] categories = {"☪  Islamic ", "💻  Algorithm ", "🔢  Mathematics", "⚛  Physics", "🔬 Chemistry","💰  E-commerce"};

        for (String cat : categories) {
            JButton btn = new JButton(cat);
            btn.setFont(new Font("SansSerif", Font.BOLD, 50));
            btn.setForeground(new Color(60, 0, 90));
            btn.setBackground(new Color(245, 230, 255));
            btn.setFocusPainted(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> {
                if ("☪  Islamic ".equals(cat)) {
                    frame.dispose();
                    IslamicBooks.main(new String[]{});
                } 
                else if ("💻  Algorithm ".equals(cat)) {
                    frame.dispose();
                    AlgoBooks.main(new String[]{});
                }
                else if ("🔢  Mathematics".equals(cat)) {
                    frame.dispose();
                    MathBooks.main(new String[]{});
                }
                else if ("⚛  Physics".equals(cat)) {
                    frame.dispose();
                    PhysicsBooks.main(new String[]{});
                }
                else if ("🔬 Chemistry".equals(cat)) {
                    frame.dispose();
                    ChemistryBooks.main(new String[]{});
                }
                else if ("💰  E-commerce".equals(cat)) {
                    frame.dispose();
                    ECommerceBooks.main(new String[]{});
                }
                else  {
                    JOptionPane.showMessageDialog(frame, "Open: " + cat);
                }
            });
            grid.add(btn);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        center.add(grid, gbc);

        return center;
    }

    private static JPanel createBottomBar(final JFrame frame) {
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 16, 12, 16));

        JButton back = new JButton("Back");
        back.setFont(new Font("SansSerif", Font.BOLD, 14));
        back.setForeground(new Color(120, 0, 180));
        back.setBackground(Color.WHITE);
        back.setFocusPainted(false);
        back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        back.addActionListener(e -> {
            frame.dispose();
            mainPage.main(new String[]{});
        });

        bottom.add(back);
        return bottom;
    }
}