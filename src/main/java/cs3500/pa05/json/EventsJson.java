package cs3500.pa05.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Event;
import java.util.List;

/**
 * JSON format for a list of events.
 *
 * @param events a list of bullet journal events
 */
public record EventsJson(@JsonProperty("events") List<Event> events) {
}
