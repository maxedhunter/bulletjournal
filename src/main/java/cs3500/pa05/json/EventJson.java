package cs3500.pa05.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;

/**
 * represents an event in Json formatting
 *
 * @param name - name of event
 * @param description - decription of event
 * @param day - day the event occurs
 * @param time - time the event starts
 * @param duration - length of the event
 */
public record EventJson(
    @JsonProperty("name")  String name,
    @JsonProperty("description") String description,
    @JsonProperty("day") Day day,
    @JsonProperty("startTime") String time,
    @JsonProperty("duration") String duration) {
}
