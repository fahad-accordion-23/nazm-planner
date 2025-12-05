package nazmplanner.ui.calendars.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;

import nazmplanner.application.calendars.CalendarsMessageBroker;
import nazmplanner.application.calendars.messages.CalendarEventAddedMessage;
import nazmplanner.ui.util.GBC;

/**
 * <h2>EventCreationDialog</h2>
 * 
 * <p>Dialog for creating new calendar events.</p>
 * 
 * @author Fahad Hassan
 * @version 05/12/2025
 */
public class EventCreationDialog extends JDialog
{
    private final CalendarsMessageBroker calendarsMessageBroker;
    
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JSpinner startDateSpinner;
    private JSpinner startTimeSpinner;
    private JSpinner endDateSpinner;
    private JSpinner endTimeSpinner;
    private JButton createButton;
    private JButton cancelButton;
    
    public EventCreationDialog(JFrame parent, CalendarsMessageBroker calendarsMessageBroker)
    {
        super(parent, "Create New Event", true);
        this.calendarsMessageBroker = calendarsMessageBroker;
        
        initComponents();
        initLayout();
        initStyling();
        initEvents();
    }
    
    private void initComponents()
    {
        titleField = new JTextField(20);
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        
        // Start date/time spinners
        LocalDateTime now = LocalDateTime.now();
        Date startDate = Date.from(now.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date startTime = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        
        startDateSpinner = new JSpinner(new SpinnerDateModel(startDate, null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor startDateEditor = new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy");
        startDateSpinner.setEditor(startDateEditor);
        
        startTimeSpinner = new JSpinner(new SpinnerDateModel(startTime, null, null, java.util.Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);
        
        // End date/time spinners (default to 2 hours later)
        Date endDate = Date.from(now.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(now.plusHours(2).atZone(ZoneId.systemDefault()).toInstant());
        
        endDateSpinner = new JSpinner(new SpinnerDateModel(endDate, null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor endDateEditor = new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy");
        endDateSpinner.setEditor(endDateEditor);
        
        endTimeSpinner = new JSpinner(new SpinnerDateModel(endTime, null, null, java.util.Calendar.HOUR_OF_DAY));
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);
        
        createButton = new JButton("Create Event");
        cancelButton = new JButton("Cancel");
    }
    
    private void initLayout()
    {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        mainPanel.add(new JLabel("Title:"), 
            new GBC(0, 0).setAnchor(GridBagConstraints.WEST).setInsets(5));
        mainPanel.add(titleField, 
            new GBC(1, 0, 2, 1).setFill(GridBagConstraints.HORIZONTAL).setWeight(1.0, 0.0).setInsets(5));
        
        // Description
        mainPanel.add(new JLabel("Description:"), 
            new GBC(0, 1).setAnchor(GridBagConstraints.NORTHWEST).setInsets(5));
        mainPanel.add(new JScrollPane(descriptionArea), 
            new GBC(1, 1, 2, 1).setFill(GridBagConstraints.BOTH).setWeight(1.0, 0.5).setInsets(5));
        
        // Start Date
        mainPanel.add(new JLabel("Start Date:"), 
            new GBC(0, 2).setAnchor(GridBagConstraints.WEST).setInsets(5));
        mainPanel.add(startDateSpinner, 
            new GBC(1, 2).setFill(GridBagConstraints.HORIZONTAL).setWeight(0.5, 0.0).setInsets(5));
        mainPanel.add(new JLabel("Time:"), 
            new GBC(2, 2).setAnchor(GridBagConstraints.WEST).setInsets(5));
        mainPanel.add(startTimeSpinner, 
            new GBC(3, 2).setFill(GridBagConstraints.HORIZONTAL).setWeight(0.5, 0.0).setInsets(5));
        
        // End Date
        mainPanel.add(new JLabel("End Date:"), 
            new GBC(0, 3).setAnchor(GridBagConstraints.WEST).setInsets(5));
        mainPanel.add(endDateSpinner, 
            new GBC(1, 3).setFill(GridBagConstraints.HORIZONTAL).setWeight(0.5, 0.0).setInsets(5));
        mainPanel.add(new JLabel("Time:"), 
            new GBC(2, 3).setAnchor(GridBagConstraints.WEST).setInsets(5));
        mainPanel.add(endTimeSpinner, 
            new GBC(3, 3).setFill(GridBagConstraints.HORIZONTAL).setWeight(0.5, 0.0).setInsets(5));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, 
            new GBC(0, 4, 4, 1).setAnchor(GridBagConstraints.CENTER).setInsets(10));
        
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void initStyling()
    {
        setSize(500, 350);
        setLocationRelativeTo(getParent());
    }
    
    private void initEvents()
    {
        createButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            if (title.isEmpty())
            {
                titleField.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }
            
            String description = descriptionArea.getText().trim();
            
            LocalDateTime start = combineDateAndTime(
                (Date) startDateSpinner.getValue(), 
                (Date) startTimeSpinner.getValue()
            );
            
            LocalDateTime end = combineDateAndTime(
                (Date) endDateSpinner.getValue(), 
                (Date) endTimeSpinner.getValue()
            );
            
            if (end.isBefore(start))
            {
                endDateSpinner.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED));
                endTimeSpinner.setBorder(BorderFactory.createLineBorder(java.awt.Color.RED));
                return;
            }
            
            calendarsMessageBroker.publish(new CalendarEventAddedMessage(title, description, start, end));
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
    }
    
    private LocalDateTime combineDateAndTime(Date dateValue, Date timeValue)
    {
        LocalDate date = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime time = timeValue.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        return LocalDateTime.of(date, time);
    }
}