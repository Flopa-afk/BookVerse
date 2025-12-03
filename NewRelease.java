import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Arrays;

public class NewRelease {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewRelease::createAndShow);
    }

    private static void createAndShow() {
        final JFrame frame = new JFrame("BOOKVERSE - Books");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(createNavBar(frame), BorderLayout.NORTH);
        frame.add(createContentPanel(frame), BorderLayout.CENTER);

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

    private static JScrollPane createContentPanel(final JFrame frame) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 0;
        c.gridy = 0;

        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setOpaque(false);

        List<Book> sample = Arrays.asList(
            new Book("E_commece Essentials", "by Jhon Ilpoulous", "600 DA", "BooksImg\\\\\\\\ImgE_commerce/E_commeceEssentials.jpg"),
            new Book("Numerical Analysis", "C. Programmer", "2000 DA", "BooksImg\\\\ImgMath/NumericalAnalysis.jpg"),
            new Book("Princip Of Physics", "D. Scientist", "1500 DA", "BooksImg\\\\ImgPhysics/PrincipOfPhysics.jpg"),
            new Book("Sahih El Boukhari", "Imame Boukhari", "9900 DA", "BooksImg\\\\ImgIslamic/Boukhari.jpg"),
            new Book("Data Structures", "F. Engineer", "1800 DA", "BooksImg\\\\ImgAlgo/DataStructures.jpg"),
            new Book("Organic Chemistry", "G. Researcher", "1400 DA", "BooksImg\\\\ImgChemistry/OrganicChemistry.jpg")
        );

        for (Book b : sample) grid.add(createBookCard(b, frame));

        container.add(grid, c);

        JScrollPane sp = new JScrollPane(container);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.WHITE);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return sp;
    }

    private static JPanel createBookCard(Book book, final JFrame frame) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(250, 250, 252));
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        card.setPreferredSize(new Dimension(300, 320));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon raw = loadIcon(book.imagePath);
        ImageIcon imgIcon = scaleIcon(raw, 140, 190);
        JLabel imgLabel = new JLabel(imgIcon);
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(12));
        card.add(imgLabel);
        card.add(Box.createVerticalStrut(10));

        JLabel title = new JLabel("<html><b>" + book.title + "</b></html>");
        title.setFont(new Font("Serif", Font.BOLD, 16));
        title.setForeground(new Color(60, 0, 90));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        JLabel author = new JLabel(book.author);
        author.setFont(new Font("SansSerif", Font.PLAIN, 13));
        author.setForeground(new Color(80, 80, 90));
        author.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(author);

        card.add(Box.createVerticalGlue());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottom.setOpaque(false);

        JLabel price = new JLabel(book.price);
        price.setFont(new Font("SansSerif", Font.BOLD, 14));
        price.setForeground(new Color(120, 0, 180));
        bottom.add(price);

        JButton buy = new JButton("Buy Now");
        buy.setBackground(new Color(255, 64, 129));
        buy.setForeground(Color.WHITE);
        buy.setFocusPainted(false);
        buy.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        buy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buy.addActionListener(e -> {
            if("E_commece Essentials".equals(book.title)) {
                frame.dispose();
                E_commerceEssentialsBook.main(new String[]{});
            }
           else if("Numerical Analysis".equals(book.title)) {
                    frame.dispose();
                    NumericalAnalysisBook.main(new String[]{});
            }
            else if("Princip Of Physics".equals(book.title)) {
                    frame.dispose();
                    PrincipOfPhysicsBook.main(new String[]{});
            }
            else if("Sahih El Boukhari".equals(book.title)) {
                    frame.dispose();
                    BoukhariBook.main(new String[]{});
            }
            else if("Data Structures".equals(book.title)) {
                frame.dispose();
                DataStructuresBook.main(new String[]{});
            }
            else if("Organic Chemistry".equals(book.title)) {
                frame.dispose();
                OrganicChemistryBook.main(new String[]{});
            }
            else {
                frame.dispose();
            }
    });
        bottom.add(buy);

        card.add(bottom);
        card.add(Box.createVerticalStrut(12));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if("E_commece Essentials".equals(book.title)) {
                frame.dispose();
                E_commerceEssentialsBook.main(new String[]{});
                }
                else if("Numerical Analysis".equals(book.title)) {
                    frame.dispose();
                    NumericalAnalysisBook.main(new String[]{});
                }
                else if("Princip Of Physics".equals(book.title)) {
                    frame.dispose();
                    PrincipOfPhysicsBook.main(new String[]{});
                }
                else if("Sahih El Boukhari".equals(book.title)) {
                    frame.dispose();
                    BoukhariBook.main(new String[]{});
                }
                else if("Data Structures".equals(book.title)) {
                frame.dispose();
                DataStructuresBook.main(new String[]{});
                }
                else if("Organic Chemistry".equals(book.title)) {
                frame.dispose();
                OrganicChemistryBook.main(new String[]{});
                }
                else {
                    frame.dispose();
                }
            }
        });

        return card;
    }

    private static ImageIcon loadIcon(String path) {
        java.net.URL url = NewRelease.class.getResource(path.startsWith("/") ? path : "/" + path);
        if (url != null) return new ImageIcon(url);
        java.io.File f = new java.io.File(path);
        if (f.exists()) return new ImageIcon(path);
        BufferedImage buf = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(buf);
    }

    private static ImageIcon scaleIcon(ImageIcon icon, int w, int h) {
        if (icon == null || icon.getIconWidth() <= 0) {
            BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            return new ImageIcon(buf);
        }
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private static class Book {
        String title, author, price, imagePath;
        Book(String t, String a, String p, String ip) {
            title = t;
            author = a;
            price = p;
            imagePath = ip;
        }
    }
}