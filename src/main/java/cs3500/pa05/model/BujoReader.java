package cs3500.pa05.model;

import java.nio.file.Path;
import java.util.List;
import javafx.event.Event;

/**
 * Represents a bullet journal file reader.
 */
public class BujoReader {

  private Path path;
  private Readable input;
  private Week week;


  /**
   * Initializes the bujo reader
   *
   * @param readable
   */
  BujoReader(Readable readable) {
    input = readable;
  }

}
