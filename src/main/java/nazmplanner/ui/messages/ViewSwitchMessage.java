package nazmplanner.ui.messages;

import nazmplanner.ui.components.ViewType;
import nazmplanner.util.messaging.Message;

public record ViewSwitchMessage(ViewType viewType) implements Message
{

}
