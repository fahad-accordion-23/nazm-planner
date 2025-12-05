package nazmplanner.ui.calendars.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.calendars.messages.CalendarEventAddedMessage;
import nazmplanner.ui.util.GBC;

/**
 * <h2>EventsSidebarPanel</h2>
 *
 * <p>Supposed to display a list of calendar views or event lists.</p>
 * 
 * @author Fahad Hassan
 * @version 04/12/2025
 */
public class SidebarPanel extends JPanel
{
    
    private final CalendarsMessageBroker calendarsMessageBroker;
    
    private JButton addEventButton;
    private JButton previousMonthButton;
    private JButton nextMonthButton;
    private JPanel  buttonPanel;
    
    public SidebarPanel(CalendarsMessageBroker calendarsMessageBroker)
    {
        this.calendarsMessageBroker = calendarsMessageBroker;
        
        initComponents();
        initLayout();
        initStyling();
        initEvents();
    }
    
    private void initComponents()
    {
        addEventButton      =   new JButton("Add Event");
        previousMonthButton =   new JButton("<");
        nextMonthButton     =   new JButton(">");
        buttonPanel         =   new JPanel();

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
            calendarsMessageBroker.publish(new CalendarEventAddedMessage(
                    "title",
                    "description",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusHours(2)));
        });
    }
}