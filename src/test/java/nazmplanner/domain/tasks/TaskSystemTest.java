package nazmplanner.domain.tasks;

// Imports required because these classes are in the 'domain' package
import nazmplanner.domain.tasks.Task;
import nazmplanner.domain.tasks.TaskStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskSystemTest
{

    private TaskSystem system;

    @BeforeEach
    void setUp()
    {
        // Fresh system for every test
        system = new TaskSystem();
    }

    // --- Adding Tasks ---

    @Test
    @DisplayName("addTask should store and return a new Task")
    void shouldAddTaskSuccessfully()
    {
        Task created = system.addTask("Buy Milk", "Groceries", LocalDateTime.now());

        assertNotNull(created);
        assertNotNull(created.getID());
        assertEquals("Buy Milk", created.getTitle());

        // Verify it is in the list
        List<Task> allTasks = system.getAllTasks();
        assertEquals(1, allTasks.size());
        assertTrue(allTasks.contains(created));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "   " }) // Spaces only
    @DisplayName("addTask should throw Exception for invalid titles")
    void shouldThrowOnInvalidTitle(String badTitle)
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            system.addTask(badTitle, "Desc", LocalDateTime.now());
        });
    }

    @Test
    @DisplayName("addTask should throw Exception for null due date")
    void shouldThrowOnNullDueDate()
    {
        assertThrows(IllegalArgumentException.class, () ->
        {
            system.addTask("Title", "Desc", null);
        });
    }

    // --- Retrieving Tasks ---

    @Test
    @DisplayName("getAllTasks should return empty list initially")
    void shouldStartEmpty()
    {
        assertTrue(system.getAllTasks().isEmpty());
    }

    @Test
    @DisplayName("getAllTasks should return unmodifiable list")
    void shouldReturnUnmodifiableList()
    {
        system.addTask("T1", "D", LocalDateTime.now());
        List<Task> tasks = system.getAllTasks();

        // Try to modify the returned list directly
        assertThrows(UnsupportedOperationException.class, () ->
        {
            tasks.clear();
        });
    }

//    @Test
//    @DisplayName("getTask should return specific task by ID")
//    void shouldGetTaskById()
//    {
//        Task created = system.addTask("Target", "Desc", LocalDateTime.now());
//        Task retrieved = system.getTask(created.getID());
//
//        assertEquals(created, retrieved);
//    }
//
//    @Test
//    @DisplayName("getTask should return null for unknown ID")
//    void shouldReturnNullForUnknownId()
//    {
//        assertNull(system.getTask(UUID.randomUUID()));
//    }
//
//    // --- Completing Tasks ---
//
//    @Test
//    @DisplayName("completeTask should update status of existing task")
//    void shouldCompleteTask()
//    {
//        Task created = system.addTask("To Finish", "Desc", LocalDateTime.now());
//
//        system.completeTask(created.getID());
//
//        assertEquals(TaskStatus.COMPLETED, created.getStatus());
//    }
//
//    @Test
//    @DisplayName("completeTask should throw Exception if ID is unknown")
//    void shouldThrowOnUnknownComplete()
//    {
//        assertThrows(IllegalArgumentException.class, () ->
//        {
//            system.completeTask(UUID.randomUUID());
//        });
//    }
//
//    @Test
//    @DisplayName("completeTask should throw Exception if ID is null")
//    void shouldThrowOnNullComplete()
//    {
//        assertThrows(IllegalArgumentException.class, () ->
//        {
//            system.completeTask(null);
//        });
//    }
}