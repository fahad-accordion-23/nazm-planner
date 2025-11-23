package nazmplanner.ui.tasks.components;

import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import nazmplanner.ui.components.CardPanel;
import nazmplanner.ui.tasks.TasksMediator;

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

    private TasksMediator tasksMediator;
    private JButton addButton;
    private JTextField titleField;
    private JSpinner dateSpinner;
    
    public CreationFormPanel(TasksMediator tasksMediator)
    {
        this.tasksMediator = tasksMediator;
        
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
        dateSpinner.setPreferredSize(new Dimension(150, 25));
    }
    
    private void initEvents()
    {
        addButton.addActionListener(e -> 
        {
            String title = titleField.getText();

            if (Objects.isNull(title) || title.trim().isBlank())
            {
                return;
            }
            
            LocalDateTime dueDate = LocalDateTime.ofInstant(
                ((Date) dateSpinner.getValue()).toInstant(),
                ZoneId.systemDefault()
            );
            
            tasksMediator.requestAddTask(title, "", dueDate);
        });
    }
    
}