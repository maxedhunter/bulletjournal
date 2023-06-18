package cs3500.pa05.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * represents a journal day in Json formatting
 *
 * @param tasks - tasks to be carried out
 * @param events - events taking place
 */
public record DayJson(
    @JsonProperty("tasks") List<JsonNode> tasks,
    @JsonProperty("events") List<JsonNode> events) {
}
