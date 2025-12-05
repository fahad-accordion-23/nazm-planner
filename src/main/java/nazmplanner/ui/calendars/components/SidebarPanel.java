package nazmplanner.ui.calendars.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.ui.util.GBC;

/**
 * <h2>EventsSidebarPanel</h2>
 *
 * <p>Sidebar with navigation controls for calendar.</p>
 * 
 * @author Fahad Hassan
 * @version 05/12/2025
 */
public class SidebarPanel extends JPanel
{
    
    private final CalendarsMessageBroker calendarsMessageBroker;
    private final MonthNavigationListener navigationListener;
    
    private JButton addEventButton;
    private JButton previousMonthButton;
    private JButton nextMonthButton;
    private JPanel buttonPanel;
    
    public SidebarPanel(CalendarsMessageBroker calendarsMessageBroker, MonthNavigationListener navigationListener)
    {
        this.calendarsMessageBroker = calendarsMessageBroker;
        this.navigationListener = navigationListener;
        
        initComponents();
        initLayout();
        initStyling();
        initEvents();
    }
    
    private void initComponents()
    {
        addEventButton = new JButton("Add Event");
        previousMonthButton = new JButton("<");
        nextMonthButton = new JButton(">");
        buttonPanel = new JPanel();

        buttonPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(previousMonthButton);
        buttonPanel.add(nextMonthButton);
    }
    
    private void initLayout()
    {
        setLayout(new GridBagLayout());

        add(addEventButton, new GBC(0, 0, 1, 1)
                .setInsets(5, 5, 5, 5)
                .setAnchor(GridBagConstraints.NORTH)
                .setFill(GridBagConstraints.HORIZONTAL));
        
        add(buttonPanel, new GBC(0, 1, 1, 1)
                .setInsets(5, 5, 5, 5)
                .setAnchor(GridBagConstraints.NORTH)
                .setFill(GridBagConstraints.HORIZONTAL));
        
        add(Box.createGlue(), new GBC(0, 2)
                .setWeight(1.0, 1.0));
    }

    private void initStyling()
    {
        super.setBackground(Color.DARK_GRAY);
        buttonPanel.setOpaque(false);
    }
    
    private void initEvents()
    {
        addEventButton.addActionListener(e -> 
        {
            EventCreationDialog dialog = new EventCreationDialog(
                (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this), 
                calendarsMessageBroker
            );
            dialog.setVisible(true);
        });
        
        previousMonthButton.addActionListener(e -> {
            if (navigationListener != null)
            {
                navigationListener.onPreviousMonth();
            }
        });
        
        nextMonthButton.addActionListener(e -> {
            if (navigationListener != null)
            {
                navigationListener.onNextMonth();
            }
        });
    }
    
    /**
     * Interface for handling month navigation events
     */
    public interface MonthNavigationListener
    {
        void onPreviousMonth();
        void onNextMonth();
    }
}
