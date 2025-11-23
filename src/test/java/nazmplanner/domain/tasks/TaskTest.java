package nazmplanner.domain.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// No import needed for Task or TaskStatus because they are in the same package!

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest
{

    // --- Constructor & Field Validation ---

    @Test
    @DisplayName("Constructor should generate ID, creation date, and default status")
    void shouldCreateValidTask()
    {
        String title = "Test Task";
        String description = "Description";
        LocalDate dueDate = LocalDate.now().plusDays(1);

        Task task = new Task(title, description, dueDate);

        assertNotNull(task.getID(), "ID should be generated");
        assertNotNull(task.getCreationDate(), "Creation date should be generated");
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(TaskStatus.TODO, task.getStatus(), "Status should default to TODO");

        // Verify creation time is recent
        assertTrue(task.getCreationDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    @DisplayName("Constructor should default title to 'New Task' if null provided")
    void shouldDefaultTitleIfNull()
    {
        Task task = new Task(null, "Desc", LocalDate.now());
        assertEquals("New Task", task.getTitle());
    }

    @Test
    @DisplayName("Constructor should throw NPE if due date is null")
    void shouldThrowOnNullDueDate()
    {
        assertThrows(NullPointerException.class, () ->
        {
            new Task("Title", "Desc", null);
        });
    }

    // --- Setters & Logic ---

    @Test
    @DisplayName("setTitle should handle nulls gracefully by defaulting")
    void setTitleShouldHandleNull()
    {
        Task task = new Task("Original", "Desc", LocalDate.now());
        task.setTitle(null);
        assertEquals("New Task", task.getTitle());
    }

    @Test
    @DisplayName("setDueDate should throw NPE on null")
    void setDueDateShouldThrowOnNull()
    {
        Task task = new Task("Title", "Desc", LocalDate.now());
        assertThrows(NullPointerException.class, () ->
        {
            task.setDueDate(null);
        });
    }

    // --- State Transitions ---

    @Test
    @DisplayName("markCompleted should change status to COMPLETED")
    void shouldMarkCompleted()
    {
        Task task = new Task("Title", "Desc", LocalDate.now());
        task.markCompleted();
        assertEquals(TaskStatus.COMPLETED, task.getStatus());
    }

    @Test
    @DisplayName("markTodo should change status back to TODO")
    void shouldMarkTodo()
    {
        Task task = new Task("Title", "Desc", LocalDate.now());
        task.markCompleted(); // First set to complete
        task.markTodo(); // Then revert
        assertEquals(TaskStatus.TODO, task.getStatus());
    }
}