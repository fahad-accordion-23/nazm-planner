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
            
            MainFrame mainFrame = new MainFrame();

            TaskSystem taskSystem = new TaskSystem();
            
            TaskController taskController = new TaskController(
                    taskSystem,
                    mainFrame.getTasksPanel().getPrimaryPanel().getTaskCardListPanel(),
                    mainFrame.getTasksPanel().getPrimaryPanel().getCreationFormPanel());
            
            mainFrame.setVisible(true);
        });
    }

}
