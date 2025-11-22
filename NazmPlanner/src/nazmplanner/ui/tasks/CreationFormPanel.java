package nazmplanner.ui.tasks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import nazmplanner.application.tasks.TaskController;
import nazmplanner.ui.components.CardPanel;

/**
 * <h2>CreationFormPanel</h2>
 * 
 * <p>Sticky footer panel for creating new tasks.</p>
 * 
 * @author Fahad Hassan
 * @version 22/11/2025
 */
public class CreationFormPanel extends CardPanel
{
    private TaskCreationListener listener;
    private JButton addButton;
    private JTextField titleField;
    private JSpinner dateSpinner;
    
    public CreationFormPanel()
    {
        initComponents();
        initLayout();
        initStyling();
        initEvents();
    }
    
    private void initComponents()
    {
        addButton = new JButton("+");        
        titleField = new JTextField();
        titleField.setToolTipText("What needs to be done?");
        
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.HOUR_OF_DAY);
        dateSpinner = new JSpinner(model);        
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm");
        dateSpinner.setEditor(editor);
        dateSpinner.setPreferredSize(new Dimension(150, 25));
    }
    
    private void initLayout()
    {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        add(addButton);
        add(Box.createHorizontalStrut(10)).setMaximumSize(getPreferredSize());
        add(titleField);
        add(Box.createHorizontalGlue());
        add(dateSpinner);
    }
    
    private void initStyling()
    {
        setBackground(Color.DARK_GRAY);
    }
    
    private void initEvents()
    {
        addButton.addActionListener(e -> 
        {
            if (listener != null) 
            {
                String title = titleField.getText();

                LocalDateTime dueDate = LocalDateTime.ofInstant(
                    ((Date) dateSpinner.getValue()).toInstant(),
                    ZoneId.systemDefault()
                );
                listener.taskCreated(title, "", dueDate);
            }
        });
    }
    
    public void setTaskCreationListener(TaskCreationListener listener)
    {
        this.listener = listener;
    }
    
}