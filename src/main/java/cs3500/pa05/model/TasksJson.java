package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Json representation of a list of tasks.
 *
 * @param tasks list of tasks
 */
public record TasksJson(@JsonProperty("tasks") List<Task> tasks) {
}
