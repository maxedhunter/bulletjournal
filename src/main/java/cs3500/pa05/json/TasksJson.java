package cs3500.pa05.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa05.model.Task;
import java.util.List;

public record TasksJson(@JsonProperty("tasks") List<Task> tasks) {
}
