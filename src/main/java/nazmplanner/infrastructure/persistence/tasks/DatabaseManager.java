package nazmplanner.infrastructure.persistence.tasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    
    private static final String URL = "jdbc:sqlite:nazmplanner.db";

    public static Connection getConnection() throws SQLException {
        try {
            // FORCE LOAD THE DRIVER
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC Driver not found in classpath!", e);
        }
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                     "id TEXT PRIMARY KEY, " +
                     "title TEXT NOT NULL, " +
                     "description TEXT, " +
                     "status TEXT NOT NULL, " +
                     "due_date TEXT, " +
                     "creation_date TEXT NOT NULL" +
                     ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(sql);
            System.out.println("Database initialized: Table 'tasks' is ready.");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace(); // Print full stack trace to debug
        }
    }
}