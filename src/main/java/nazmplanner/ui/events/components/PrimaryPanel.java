package nazmplanner.ui.events.components;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import nazmplanner.ui.tasks.TasksMediator;

/**
 * <h2>EventsPrimaryPanel</h2>
 * * <p>Supposed to display the main calendar grid and event details.</p>
 */
public class PrimaryPanel extends JPanel
{
    public PrimaryPanel()
    {
        initStyling();
        initLayout();
    }
    
    private void initStyling()
    {
        super.setBackground(Color.WHITE);
    }
    
    private void initLayout()
    {
        // Placeholder for the full calendar UI
        super.setLayout(new BorderLayout());
        super.add(new JLabel("<< CALENDAR GRID/VIEW PLACEHOLDER >>", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}