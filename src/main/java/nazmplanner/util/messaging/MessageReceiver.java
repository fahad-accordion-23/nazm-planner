package nazmplanner.util.messaging;

/**
 * <h2>EventReceiver</h2>
 * 
 * <p>Interface implemented by those who receive events</p>
 * 
 * @param <M> a message of the type/interface Message
 * 
 * @author Fahad Hassan
 * @version 24/11/2025
 */
@FunctionalInterface
public interface MessageReceiver<M extends Message>
{
    void onEvent(M message);
}
