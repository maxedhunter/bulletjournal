package cs3500.pa05.Json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * represents a week in a bullet journal in Json formatting
 *
 * @param days - days in the week
 */
public record WeekJson(
    @JsonProperty("days") List<JsonNode> days) {
}
