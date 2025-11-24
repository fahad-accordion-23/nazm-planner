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
 * <p>Full CRUD implementation for SQLite.</p>
 */
public class SqliteTaskRepository implements TaskRepository {

    // --------------------------------------------------------
    // 1. CREATE & UPDATE (Save)
    // --------------------------------------------------------
    @Override
    public void save(Task task) {
        String sql = "INSERT OR REPLACE INTO tasks (id, title, description, status, due_date, creation_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task.getID().toString());
            pstmt.setString(2, task.getTitle());
            pstmt.setString(3, task.getDescription());
            pstmt.setString(4, task.getStatus().name());
            
            if (task.getDueDate() != null) {
                pstmt.setString(5, task.getDueDate().toString());
            } else {
                pstmt.setString(5, null);
            }

            pstmt.setString(6, task.getCreationDate().toString());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error saving task: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------
    // 2. READ ALL (Find All)
    // --------------------------------------------------------
    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        
        // FIX: Added ORDER BY creation_date to ensure the list order remains stable 
        // even when a task is updated (which triggers a REPLACE).
        String sql = "SELECT * FROM tasks ORDER BY creation_date ASC";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Task task = mapRowToTask(rs);
                if (task != null) {
                    tasks.add(task);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error finding all tasks: " + e.getMessage());
            e.printStackTrace();
        }

        return tasks;
    }

    // --------------------------------------------------------
    // 3. READ ONE (Find By ID) - NEW IMPLEMENTATION
    // --------------------------------------------------------
    @Override
    public Task findById(UUID id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTask(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding task by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if not found
    }

    // --------------------------------------------------------
    // 4. DELETE (Delete by ID) - NEW IMPLEMENTATION
    // --------------------------------------------------------
    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id.toString());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting task: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------
    // HELPER METHOD (To avoid code duplication)
    // --------------------------------------------------------
    private Task mapRowToTask(ResultSet rs) throws SQLException {
        try {
            String idStr = rs.getString("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String statusStr = rs.getString("status");
            String dueDateStr = rs.getString("due_date");
            String creationDateStr = rs.getString("creation_date");

            UUID id = UUID.fromString(idStr);
            TaskStatus status = TaskStatus.valueOf(statusStr);
            LocalDateTime creationDate = LocalDateTime.parse(creationDateStr);
            
            LocalDateTime dueDate = null;
            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                dueDate = LocalDateTime.parse(dueDateStr);
            }

            return new Task(id, title, description, status, dueDate, creationDate);
        } catch (Exception e) {
            System.err.println("Skipping invalid row: " + e.getMessage());
            return null;
        }
    }
}