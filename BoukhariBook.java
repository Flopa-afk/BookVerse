import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BoukhariBook {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoukhariBook::createAndShow);
    }

    private static void createAndShow() {
        final JFrame frame = new JFrame("BOOKVERSE - Product");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(1100, 650); 
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(createNavBar(frame), BorderLayout.NORTH);
        frame.add(createMainPanel(), BorderLayout.CENTER);
        

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
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(255, 255, 255, 200)),
                        BorderFactory.createEmptyBorder(4, 6, 2, 6)
                ));
            }
            
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setForeground(new Color(255, 200, 255));
                    btn.setFont(new Font("SansSerif", Font.BOLD, 15));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("SansSerif", Font.BOLD, 14));
                }
            });
            
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
        
        JButton cartBtn = new JButton("");
        cartBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        cartBtn.setForeground(Color.WHITE);
        cartBtn.setBackground(new Color(180, 140, 200));
        cartBtn.setFocusPainted(false);
        cartBtn.setBorderPainted(false);
        cartBtn.setContentAreaFilled(false);
        cartBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cartBtn.addActionListener(e -> new CartWindow());
        iconPanel.add(cartBtn);
                java.util.function.Function<String, ImageIcon> resizeIcon = p -> scaleIconSafe(loadIcon(p), 24, 24);
        for (String icon : new String[]{"facebook.png", "instagram.png", "linkedin.png", "search.png"}) {
            JLabel l = new JLabel(resizeIcon.apply(icon));
            // keep pointer cursor, remove scaling on hover
            l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            iconPanel.add(l);
        }
        nav.add(iconPanel, BorderLayout.EAST);

        return nav;
    }

    private static JPanel createMainPanel() {
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(250, 250, 252));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(24, 24, 24, 24);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        // Left: Book image and brief info
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon raw = loadIcon("BooksImg\\ImgIslamic/Boukhari.jpg");
        ImageIcon cover = scaleIconSafe(raw, 380, 520);
        JLabel coverLabel = new JLabel(cover);
        // remove hover scaling; keep pointer cursor only
        coverLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        left.add(coverLabel);
        left.add(Box.createVerticalStrut(12));

        JLabel title = new JLabel("Sahihi El Bokhari");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setForeground(new Color(60, 0, 90));
        left.add(title);

        JLabel author = new JLabel("Imam El Boukhari");
        author.setFont(new Font("SansSerif", Font.PLAIN, 14));
        author.setForeground(new Color(70, 70, 90));
        left.add(author);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        main.add(left, gbc);

        // Right: purchase/options panel
        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new GridBagLayout());
        GridBagConstraints r = new GridBagConstraints();
        r.insets = new Insets(8, 8, 8, 8);
        r.gridx = 0;
        r.anchor = GridBagConstraints.NORTH;
        r.fill = GridBagConstraints.HORIZONTAL;

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel lblTitle = new JLabel("Sahih El Boukhari");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lblTitle.setForeground(new Color(60, 0, 90));
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(8));

        JTextArea desc = new JTextArea("Sahih El Boukhari is one of the most authentic collections of hadiths in Islam, compiled by the renowned scholar Imam Al-Bukhari. This comprehensive work encompasses thousands of sayings and actions of the Prophet Muhammad (peace be upon him), meticulously gathered and verified for authenticity. It serves as a vital source of guidance for Muslims worldwide, offering insights into Islamic teachings, jurisprudence, and moral conduct. Sahih El Boukhari is revered for its rigorous methodology and remains a cornerstone of Islamic scholarship.");
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        desc.setForeground(new Color(70, 70, 90));
        card.add(desc);
        card.add(Box.createVerticalStrut(14));

        JLabel price = new JLabel("8000 DA");
        price.setFont(new Font("SansSerif", Font.BOLD, 20));
        price.setForeground(new Color(120, 0, 180));
        card.add(price);
        card.add(Box.createVerticalStrut(12));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);

        JButton buy = styledPrimaryButton("Acheter ");
        JButton buyPdf = styledSecondaryButton("Acheter PDF");
        JButton share = styledSecondaryButton("🔗 Partager");

        // Add action listener to buy button
        buy.addActionListener(e -> {
            String priceText = price.getText().replace(" DA", "").trim();
            
            // Open the add to cart dialog with the selected quantity
            AddToCartDialog dialog = new AddToCartDialog(null, "Sahih El Boukhari", priceText + " DA");
            dialog.setVisible(true);
            
            // If user confirmed in the dialog, use their selected quantity and add to cart
            if (dialog.isConfirmed()) {
                int finalQuantity = dialog.getQuantity();
                CartManager.addBook("Sahih El Boukhari", "Imam El Boukhari", priceText + " DA", "BooksImg\\ImgIslamic/Boukhari.jpg", finalQuantity);
                SwingUtilities.invokeLater(() -> new CartWindow());
            }
        });

        btnRow.add(buy);
        btnRow.add(buyPdf);
        btnRow.add(share);
        card.add(btnRow);
        card.add(Box.createVerticalStrut(12));

        JPanel categories = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        categories.setOpaque(false);
        categories.add(categoryChip("Religion"));
        categories.add(categoryChip("Islam"));
        categories.add(categoryChip("Aquidah"));
        categories.add(categoryChip("Education"));
        card.add(categories);
        
        card.add(Box.createVerticalStrut(12));
        JLabel note = new JLabel("<html><small>Après l'achat, le lien de téléchargement sera envoyé par email. Vérifiez l'orthographe de l'email.</small></html>");
        note.setFont(new Font("SansSerif", Font.PLAIN, 12));
        note.setForeground(new Color(100, 100, 110));
        card.add(note);

        r.gridy = 0;
        right.add(card, r);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        main.add(right, gbc);

        return main;
    }

    private static JButton styledPrimaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(255, 64, 129));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(220, 20, 100)); }
            public void mouseExited(MouseEvent e) { b.setBackground(new Color(255, 64, 129)); }
        });
        return b;
    }

    private static JButton styledSecondaryButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(new Color(245, 245, 246)); }
            public void mouseExited(MouseEvent e) { b.setBackground(Color.WHITE); }
        });
        return b;
    }

    private static JLabel categoryChip(String text) {
        JLabel l = new JLabel(text);
        l.setOpaque(true);
        l.setBackground(new Color(186, 186, 186));
        l.setForeground(Color.WHITE);
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        return l;
    }

    private static ImageIcon loadIcon(String path) {
        java.net.URL url = BoukhariBook.class.getResource(path.startsWith("/") ? path : "/" + path);
        if (url != null) return new ImageIcon(url);
        java.io.File f = new java.io.File(path);
        if (f.exists()) return new ImageIcon(path);
        BufferedImage buf = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        System.err.println("Image not found: " + path + " (working dir: " + System.getProperty("user.dir") + ")");
        return new ImageIcon(buf);
    }

    private static ImageIcon scaleIconSafe(ImageIcon icon, int w, int h) {
        if (icon == null || icon.getIconWidth() <= 0) {
            return new ImageIcon(new BufferedImage(Math.max(1, w), Math.max(1, h), BufferedImage.TYPE_INT_ARGB));
        }
        Image img = icon.getImage().getScaledInstance(Math.max(1, w), Math.max(1, h), Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}