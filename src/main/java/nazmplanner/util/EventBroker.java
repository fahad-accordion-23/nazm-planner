package nazmplanner.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class EventBroker
{
    private final Map<Class<? extends Event>, List<EventReceiver<? extends Event>>>
        receivers = new HashMap<>();
    
    public <E extends Event> void subscribe(Class<E> eventType, EventReceiver<E> receiver)
    {
        receivers.computeIfAbsent(eventType, f -> new ArrayList<>()).add(receiver);
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Event> void publish(E event)
    {
        List<EventReceiver<? extends Event>> list = receivers.get(event.getClass());
        
        if (!Objects.isNull(list))
        {
            for (EventReceiver<? extends Event> r : list)
            {
                ((EventReceiver<E>) r).onEvent(event);
            }
        }
    }
}
