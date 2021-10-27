package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ImportShowingsTest {
  
  private static HashMap<Integer,Cinema> cinemas;
  private static HashMap<Integer,Movie> movies;
  private HashMap<Integer,String> errors; 

  @BeforeAll static void initialSetup() {
    DataController.setBasePath("src/test/resources/");
    cinemas = new HashMap<>();
    cinemas.put(1, new Cinema(1, null));
    cinemas.put(2, new Cinema(2, null));
    cinemas.put(3, new Cinema(3, null));
    cinemas.put(4, new Cinema(4, null));

  }

  @BeforeEach void refreshVariables() {
    errors = new HashMap<>();
    movies = new HashMap<>();
    movies.put(1, new Movie(1, null, null, null, null, null, null));
    movies.put(30, new Movie(30, null, null, null, null, null, null));
    movies.put(70, new Movie(70, null, null, null, null, null, null));
    movies.put(10000, new Movie(10000, null, null, null, null, null, null));
  }

  @AfterEach void printErrors() {
    DataController.printErrorMap(errors);
  }

  @Test void importShowingsWithNoMatchingCinema() {

    try {
      errors = DataController.importShowings(movies,cinemas,"importShowingsWithNoMatchingCinema.csv");
    } catch (IOException e) {
    }
    assertEquals(7,errors.size());
    assertEquals(2,movies.get(1).getShowings().size());
  }

  @Test void importShowingsWithNoMatchingMovie() {

    try {
      errors = DataController.importShowings(movies,cinemas,"importShowingsWithNoMatchingMovie.csv");
    } catch (IOException e) {
    }

    assertEquals(7,errors.size());
    assertEquals(1,movies.get(1).getShowings().size());
    assertEquals(1,movies.get(1).getShowings().size());
  }

  @Test void importShowingsWithInvalidIntegers() {

    try {
      errors = DataController.importShowings(movies,cinemas,"importShowingsWithInvalidIntegers.csv");
    } catch (IOException e) {
    }

    assertEquals(4,errors.size());
    assertEquals(1,movies.get(1).getShowings().size());
  }

  @Test void importShowingsNotEnoughFields() {

    try {
      errors = DataController.importShowings(movies,cinemas, "importShowingsNotEnoughFields.csv");
    } catch (IOException e) {
    }

    assertEquals(3,errors.size());
    assertEquals(6,movies.get(1).getShowings().size());
  }
}
