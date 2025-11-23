package nazmplanner.util;

/**
 * <h2>EventReceiver</h2>
 * 
 * <p>Interface implemented by those who receive events</p>
 * 
 * @param <E> an event of the type/interface Event
 * 
 * @author Fahad Hassan
 * @version 24/11/2025
 */
public interface EventReceiver<E extends Event>
{
    void onEvent(E event);
}
