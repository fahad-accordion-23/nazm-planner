package nazmplanner.infrastructure.persistence.tasks;

import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskRepository;
import nazmplanner.domain.tasks.TaskStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h2>SqliteTaskRepository</h2>
 *
 * <p>Implementation of TaskRepository that persists tasks to a SQLite database.</p>
 * * @author Saifullah Khan Safi
 * @version 23/11/2025
 */
public class SqliteTaskRepository implements TaskRepository {

    @Override
    public void save(Task task) {
        // SQLite specific command: This will Insert a new row, OR Update it if the ID already exists.
        String sql = "INSERT OR REPLACE INTO tasks (id, title, description, status, due_date, creation_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 1. Map UUID to String
            pstmt.setString(1, task.getID().toString());

            // 2. Map Strings
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());

            // 3. Map Enum to String (e.g., "TODO", "COMPLETED")
            pstmt.setString(4, task.getStatus().name());

            // 4. Map LocalDateTime to String (ISO-8601 format)
            // We check for null because dueDate might be optional
            if (task.getDueDate() != null) {
                pstmt.setString(5, task.getDueDate().toString());
            } else {
                pstmt.setString(5, null);
            }

            pstmt.setString(6, task.getCreationDate().toString());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error saving task to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    // 1. Extract data from the ResultSet
                    String idStr = rs.getString("id");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    String statusStr = rs.getString("status");
                    String dueDateStr = rs.getString("due_date");
                    String creationDateStr = rs.getString("creation_date");

                    // 2. Convert Strings back to Java Objects
                    UUID id = UUID.fromString(idStr);
                    TaskStatus status = TaskStatus.valueOf(statusStr); // Converts "TODO" back to TaskStatus.TODO
                    LocalDateTime creationDate = LocalDateTime.parse(creationDateStr);
                    
                    LocalDateTime dueDate = null;
                    if (dueDateStr != null && !dueDateStr.isEmpty()) {
                        dueDate = LocalDateTime.parse(dueDateStr);
                    }

                    // 3. Use the "Reconstruction Constructor" from your Task.java
                    Task task = new Task(id, title, description, status, dueDate, creationDate);
                    tasks.add(task);

                } catch (Exception e) {
                    System.err.println("Skipping corrupted row: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading tasks from database: " + e.getMessage());
            e.printStackTrace();
        }

        return tasks;
    }
}