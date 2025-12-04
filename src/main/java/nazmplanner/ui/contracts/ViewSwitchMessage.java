package nazmplanner.ui.contracts;

import nazmplanner.ui.components.ViewType;
import nazmplanner.util.messaging.Message;

public record ViewSwitchMessage(ViewType viewType) implements Message
{

}
