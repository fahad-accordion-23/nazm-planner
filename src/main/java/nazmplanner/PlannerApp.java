package nazmplanner;

import java.awt.EventQueue;

import nazmplanner.domain.tasks.*;
import nazmplanner.application.tasks.*;
import nazmplanner.ui.MainFrame;
import nazmplanner.ui.tasks.*;

/**
 * <h2>PlannerApp</h2>
 * 
 * <p>Entry point for the app.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class PlannerApp
{
   
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> 
        {
            /* UI */
            MainFrame mainFrame = new MainFrame();
            
            /* Domain */
            TaskSystem taskSystem = new TaskSystem();
            
            /* Application */
            TaskController taskController = new TaskController(taskSystem, mainFrame.getTasksMediator());
            
            mainFrame.setVisible(true);
        });
    }

}
