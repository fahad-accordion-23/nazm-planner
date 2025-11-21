package nazmplanner.ui.components;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * <h2>CardListPanel</h2>
 * 
 * <p>A generic container for stacking CardPanels vertically. 
 * Stacks them like a vbox with top-alignment</p>
 */
public class CardListPanel extends JPanel
{
    private int cardCount;
    
    public CardListPanel()
    {
        initLayout();
        initStyling();
        
        cardCount = 0;
    }
    
    private void initLayout()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    private void initStyling()
    {
        setOpaque(false);
    }
    
    public void addCard(CardPanel card)
    {
        if (cardCount > 0)
        {
            remove(getComponentCount() - 1);    // remove the Glue
            add(Box.createVerticalStrut(10));
        }
        
        add(card);
        add(Box.createVerticalGlue());
        cardCount++;
        
        revalidate();
        repaint(); 
    }
    
    public void clear()
    {
        removeAll();
        cardCount = 0;
        
        revalidate();
        repaint();
    }
}
