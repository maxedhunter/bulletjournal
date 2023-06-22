package cs3500.pa05.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents helpful string methods
 */
public class StringUtils {
  /**
   * Parses links from description (assuming there is a space following them or it reaches
   * the end of the string)
   *
   * @param string a string to parse
   * @return valid urls
   */
  public static List<String> parseLinks(String string) {
    List<String> links = new ArrayList<>();
    String lowerCaseDesc = string.toLowerCase();
    String lowerCaseDescCopy = lowerCaseDesc;

    int start;
    int end;

    while (lowerCaseDesc.contains("http:")) {
      // if the string contains a link with http
      start = lowerCaseDesc.indexOf("http:");

      // if it doesn't exist
      if (lowerCaseDesc.indexOf(" ", start) == -1) {
        end = lowerCaseDesc.length();
      } else {
        end = lowerCaseDesc.indexOf(" ", start);
      }

      String temp = lowerCaseDesc.substring(start, end);
      links.add(temp);
      lowerCaseDesc = lowerCaseDesc.substring(end);
    }

    int startCopy;
    int endCopy;

    while (lowerCaseDescCopy.contains("https:")) {
      startCopy = lowerCaseDescCopy.indexOf("https:");

      // if it doesn't exist
      if (lowerCaseDescCopy.indexOf(" ", startCopy) == -1) {
        endCopy = lowerCaseDescCopy.length();
      } else {
        endCopy = lowerCaseDescCopy.indexOf(" ", startCopy);
      }

      String temp = lowerCaseDescCopy.substring(startCopy, endCopy);
      links.add(temp);
      lowerCaseDescCopy = lowerCaseDescCopy.substring(endCopy);
    }

    return links;
  }
}
