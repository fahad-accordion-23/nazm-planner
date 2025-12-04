package nazmplanner.ui.tasks.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nazmplanner.application.tasks.TasksMessageBroker;
import nazmplanner.ui.core.HeaderPanel;
import nazmplanner.ui.util.GBC;

/**
 * <h2>PrimaryPanel</h2>
 * 
 * <p>Not functional right now. Supposed to display the tasks in the current list
 * and an interface at the bottom to add new tasks.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class PrimaryPanel extends JPanel
{
    
    private TasksMessageBroker tasksMediator;
    private HeaderPanel headerPanel;
    private TaskCardListPanel taskCardListPanel;
    private JScrollPane taskCardListScrollPane;
    private CreationFormPanel creationFormPanel;
    private DetailPanel detailPanel;    
    
    public PrimaryPanel(TasksMessageBroker tasksMediator)
    {
        this.tasksMediator = tasksMediator;

        initComponents();
        initStyling();
        initLayout();
    }
    
    private void initComponents()
    {
        headerPanel = new HeaderPanel("Tasks");
        taskCardListPanel = new TaskCardListPanel(tasksMediator);
        taskCardListScrollPane = new JScrollPane(taskCardListPanel);
        creationFormPanel = new CreationFormPanel(tasksMediator);
        detailPanel = new DetailPanel(tasksMediator);
    }
    
    private void initStyling()
    {
        super.setBackground(Color.DARK_GRAY);
    }
    
    private void initLayout()
    {
        super.setLayout(new GridBagLayout());
                
        super.add(headerPanel, 
                  new GBC(0, 0, 2, 1)
                  .setWeight(1.00, 0.00)
                  .setAnchor(GridBagConstraints.NORTH)
                  .setFill(GridBagConstraints.HORIZONTAL));
                
        super.add(taskCardListScrollPane,
                  new GBC(0, 1, 1, 1)
                  .setWeight(0.70, 1.00)
                  .setFill(GridBagConstraints.BOTH));

        super.add(detailPanel,
                  new GBC(1, 1, 1, 2)
                  .setWeight(0.30, 1.00)
                  .setFill(GridBagConstraints.BOTH));
                
        super.add(creationFormPanel,
                  new GBC(0, 2, 1, 1)
                  .setWeight(1.00, 0.00)
                  .setAnchor(GridBagConstraints.SOUTH)
                  .setFill(GridBagConstraints.HORIZONTAL));
    }
    
}
