package nazmplanner;

import java.awt.EventQueue;

import nazmplanner.domain.calendars.CalendarsSystem;
import nazmplanner.domain.tasks.*;
import nazmplanner.infrastructure.persistence.tasks.DatabaseManager;
import nazmplanner.application.calendars.CalendarsController;
import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.tasks.*;
import nazmplanner.ui.MainFrame;

/**
 * <h2>PlannerApp</h2>
 * 
 * <p>Entry point for the app.</p>
 * 
 * @author Fahad Hassan
 * @version 04/11/2025
 */
public class PlannerApp
{
   
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> 
        {
        	/* Database */
        	DatabaseManager.initializeDatabase();
        	            
            /* Domain */
            TasksSystem taskSystem = new TasksSystem();
            CalendarsSystem calendarsSystem= new CalendarsSystem();
            
            /* Application */
            CalendarsMessageBroker calendarsMessageBroker = new CalendarsMessageBroker();
            TasksMessageBroker tasksMessageBroker = new TasksMessageBroker();
            TasksController taskController = new TasksController(taskSystem, tasksMessageBroker);
            CalendarsController calendarsController = new CalendarsController(calendarsSystem, calendarsMessageBroker);

            
            /* UI */
            MainFrame mainFrame = new MainFrame(tasksMessageBroker, calendarsMessageBroker);
            taskController.updateTasks();
            calendarsController.updateEvents();
            
            mainFrame.setVisible(true);
        });
    }

}
