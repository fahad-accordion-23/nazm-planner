package nazmplanner.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * <h2>CardPanel</h2>
 * 
 * <p>Generic base style-class for card-like UI items (Task cards, Event cards, etc).</p>
 * 
 * @author Fahad Hassan
 * @version 21/11/2025
 */
public class CardPanel extends JPanel
{
    public CardPanel()
    {
        initStyling();
        initLayout();
    }
    
    private void initStyling()
    {
        setBackground(Color.WHITE);
        
        Border lineBorder = BorderFactory.createLineBorder(new Color(230, 230, 230), 1);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(lineBorder, paddingBorder));
    }
    
    private void initLayout()
    {
        setLayout(new BorderLayout(10, 0));
    }
    
}