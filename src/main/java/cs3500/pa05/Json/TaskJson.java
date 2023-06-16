package cs3500.pa05.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Day;

/**
 * represents a task in Json formatting
 *
 * @param name - name of the task
 * @param description - description of the task
 * @param day - day of the task
 * @param isDone - is the task finished
 */
public record TaskJson(
    @JsonProperty("name") String name,
    @JsonProperty("description") String description,
    @JsonProperty("day") Day day,
    @JsonProperty("completion") boolean isDone) {
}
