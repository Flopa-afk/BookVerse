import java.util.*;
import java.text.SimpleDateFormat;

public class CartManager {
    private static final List<CartItem> items = new ArrayList<>();

    public static void addBook(String title, String author, String price, String imagePath, int quantity) {
        int value = parsePrice(price);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        items.add(new CartItem(title, author, value, imagePath, date, quantity, false));
    }

    public static List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public static int getTotal() {
        int sum = 0;
        for (CartItem it : items) sum += it.priceDA * it.quantity;
        return sum;
    }

    public static void clear() {
        items.clear();
    }

    public static void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public static int getCartCount() {
        return items.size();
    }

    public static void updateQuantity(int index, int quantity) {
        if (index >= 0 && index < items.size() && quantity > 0) {
            CartItem old = items.get(index);
            items.set(index, new CartItem(old.title, old.author, old.priceDA, old.imagePath, old.dateAdded, quantity, old.confirmed));
        }
    }

    public static void setConfirmed(int index, boolean confirmed) {
        if (index >= 0 && index < items.size()) {
            CartItem old = items.get(index);
            items.set(index, new CartItem(old.title, old.author, old.priceDA, old.imagePath, old.dateAdded, old.quantity, confirmed));
        }
    }

    private static int parsePrice(String p) {
        if (p == null) return 0;
        String digits = p.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return 0;
        try { return Integer.parseInt(digits); } catch (NumberFormatException e) { return 0; }
    }

    public static class CartItem {
        public final String title;
        public final String author;
        public final int priceDA;
        public final String imagePath;
        public final String dateAdded;
        public final int quantity;
        public final boolean confirmed;

        public CartItem(String t, String a, int p, String img, String date, int qty) {
            this(t, a, p, img, date, qty, false);
        }

        public CartItem(String t, String a, int p, String img, String date, int qty, boolean confirmed) {
            this.title = t;
            this.author = a;
            this.priceDA = p;
            this.imagePath = img;
            this.dateAdded = date;
            this.quantity = qty;
            this.confirmed = confirmed;
        }
    }
}
