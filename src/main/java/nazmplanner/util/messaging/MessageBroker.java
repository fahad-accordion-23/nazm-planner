package nazmplanner.util.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <h2>MessageBroker</h2>
 * 
 * <p>Brokers system messages using the publish-subscribe pattern<p>
 * 
 * <p>WARNING: Be careful with circular subscriptions!</p>
 * 
 * @author Fahad Hassan
 * @version 04/12/2025
 */
public abstract class MessageBroker<T extends Message>
{
    private final Map<Class<? extends T>, List<MessageReceiver<? extends T>>>
        receivers = new HashMap<>();
    
    public <M extends T> void subscribe(Class<M> messageType, MessageReceiver<M> receiver)
    {
        receivers.computeIfAbsent(messageType, f -> new ArrayList<>()).add(receiver);
    }
    
    @SuppressWarnings("unchecked")
    public <M extends T> void publish(M message)
    {
        List<MessageReceiver<? extends T>> list = receivers.get(message.getClass());
        
        if (!Objects.isNull(list))
        {
            for (MessageReceiver<? extends T> r : list)
            {
                ((MessageReceiver<M>) r).onEvent(message);
            }
        }
    }
}
