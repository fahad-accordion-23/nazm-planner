package nazmplanner.ui.contracts;

import nazmplanner.ui.components.ViewType;
import nazmplanner.util.Event;

public record ViewSwitchEvent(ViewType viewType) implements Event
{

}
