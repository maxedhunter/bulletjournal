package cs3500.pa05.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;
import cs3500.pa05.model.DayEnum;
import java.util.Map;

/**
 * Represents the json format for a list of days.
 */
public record DaysJson(@JsonProperty("days") Map<DayEnum, Day> days) {
}
