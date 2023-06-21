package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * represents a week in a bullet journal in Json formatting
 */
public record WeekJson(
    @JsonProperty("name") String name,
    @JsonProperty("tasks") JsonNode tasks,
    @JsonProperty("events") JsonNode events,
    @JsonProperty("days") JsonNode days) {
}
