import java.sql.*;

public class DBHelper {
    // Update these constants to match your MySQL/phpMyAdmin setup
    private static final String USER = "bookuser";              // your new user
    private static final String PASS = "123";        // the password you set
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bookverse?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static final String DB_NAME = "bookverse";
    private static boolean initialized = false;

    // Attempt to load the MySQL JDBC driver early so we fail fast with a clear message
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC driver not found. Please add the MySQL Connector/J jar to the classpath.");
            System.err.println("Download: https://dev.mysql.com/downloads/connector/j/");
            System.err.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Initialize database: create if not exists, then create schema.
     * Call this once on app startup.
     */
    public static void initializeDatabase() {
        if (initialized) return;
        initialized = true;

        try {
            // Create database if it doesn't exist
            createDatabaseIfNotExists();
            System.out.println("Database '" + DB_NAME + "' ready.");

            // Create tables in the database
            createSchemaIfNotExists();
            System.out.println("Schema created/verified.");
        } catch (Exception e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Connect to MySQL server (without selecting a database) to create bookverse database.
     */
    private static Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(SERVER_URL, USER, PASS);
    }

    /**
     * Create the bookverse database if it doesn't exist.
     */
    private static void createDatabaseIfNotExists() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
        try (Connection c = getServerConnection(); Statement s = c.createStatement()) {
            s.execute(sql);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void createSchemaIfNotExists() {
        String clients = "CREATE TABLE IF NOT EXISTS clients ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "full_name VARCHAR(255) NOT NULL,"
                + "email VARCHAR(255) NOT NULL,"
                + "delivery_address VARCHAR(500) NOT NULL,"
                + "phone VARCHAR(50) NOT NULL"
                + ");";

        String orders = "CREATE TABLE IF NOT EXISTS orders ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "order_number VARCHAR(100) NOT NULL,"
            + "order_date DATETIME,"
            + "total_amount INT NOT NULL,"
            + "client_id INT,"
            + "FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE SET NULL"
            + ");";

        String orderItems = "CREATE TABLE IF NOT EXISTS order_items ("
            + "id INT AUTO_INCREMENT PRIMARY KEY,"
            + "order_id INT NOT NULL,"
            + "title VARCHAR(500),"
            + "author VARCHAR(255),"
            + "unit_price INT,"
            + "quantity INT,"
            + "total_price INT,"
            + "FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE"
            + ");";

        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            s.execute(clients);
            s.execute(orders);
            s.execute(orderItems);
        } catch (SQLException e) {
            System.err.println("Failed to create schema: " + e.getMessage());
        }
    }

    /**
     * Inserts a client and returns the generated client id, or -1 on failure.
     */
    public static int insertClient(String fullName, String email, String deliveryAddress, String phone) throws SQLException {
        String sql = "INSERT INTO clients (full_name, email, delivery_address, phone) VALUES (?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, deliveryAddress);
            ps.setString(4, phone);
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Inserts an order linked to a client id and returns the generated order id, or -1 on failure.
     */
    public static int insertOrder(int clientId, String orderNumber, String orderDate, int total) throws SQLException {
        String sql = "INSERT INTO orders (order_number, order_date, total_amount, client_id) VALUES (?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, orderNumber);
            ps.setString(2, orderDate);
            ps.setInt(3, total);
            if (clientId > 0) ps.setInt(4, clientId); else ps.setNull(4, java.sql.Types.INTEGER);
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Inserts order items for a given order id.
     */
    public static boolean insertOrderItems(int orderId, java.util.List<CartManager.CartItem> items) throws SQLException {
        if (items == null || items.isEmpty()) return true;
        String sql = "INSERT INTO order_items (order_id, title, author, unit_price, quantity, total_price) VALUES (?,?,?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (CartManager.CartItem it : items) {
                ps.setInt(1, orderId);
                ps.setString(2, it.title);
                ps.setString(3, it.author);
                ps.setInt(4, it.priceDA);
                ps.setInt(5, it.quantity);
                ps.setInt(6, it.priceDA * it.quantity);
                ps.addBatch();
            }
            int[] results = ps.executeBatch();
            for (int r : results) if (r == Statement.EXECUTE_FAILED) return false;
            return true;
        }
    }
}
