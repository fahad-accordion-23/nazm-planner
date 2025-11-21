package nazmplanner.ui.tasks;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import nazmplanner.ui.components.HeaderPanel;
import nazmplanner.ui.util.GBC;

/**
 * <h2>PrimaryPanel</h2>
 * 
 * <p>Not functional right now. Supposed to display the tasks in the current list
 * and an interface at the bottom to add new tasks.</p>
 * 
 * @author Fahad Hassan
 * @version 21/11/2025
 */
public class PrimaryPanel extends JPanel
{
    private HeaderPanel headerPanel;
    private ListViewPanel listViewPanel;
    private JScrollPane listViewScrollPane;
    private CreationFormPanel creationFormPanel;
    
    public PrimaryPanel()
    {
        headerPanel = new HeaderPanel("Tasks");
        listViewPanel = new ListViewPanel();
        listViewScrollPane = new JScrollPane(listViewPanel);
        creationFormPanel = new CreationFormPanel();
        
        initStyling();
        initLayout();
    }
    
    private void initStyling()
    {
        super.setBackground(Color.DARK_GRAY);
    }
    
    private void initLayout()
    {
        super.setLayout(new GridBagLayout());
        
        super.add(headerPanel, 
                  new GBC(0, 0, 1, 0)
                  .setWeight(1.00, 0.00)
                  .setAnchor(GridBagConstraints.NORTH)
                  .setFill(GridBagConstraints.HORIZONTAL));
        
        super.add(listViewScrollPane,
                  new GBC(0, 1, 1, 0)
                  .setWeight(1.00, 1.00)
                  .setFill(GridBagConstraints.BOTH));
        
        super.add(creationFormPanel,
                  new GBC(0, 0, 2, 0)
                  .setWeight(1.00, 0.00)
                  .setAnchor(GridBagConstraints.SOUTH)
                  .setFill(GridBagConstraints.HORIZONTAL));
    }
}
