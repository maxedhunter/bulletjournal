package cs3500.pa05.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * represents a week in a bullet journal in Json formatting
 *
 * @param days - days in the week
 */
public record WeekJson(
    @JsonProperty("name") String name,
    @JsonProperty("days") List<JsonNode> days) {
}
