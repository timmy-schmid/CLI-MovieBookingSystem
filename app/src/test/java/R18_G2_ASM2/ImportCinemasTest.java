package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ImportCinemasTest {
  
  private HashMap<Integer,Cinema> cinemas;
  private HashMap<Integer,String> errors; 

  @BeforeAll static void initialSetup() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach void refreshVariables() {
    cinemas = new HashMap<>();
    errors = new HashMap<>();
  }

  @AfterEach void printErrors() {
    DataController.printErrorMap(errors);
  }

  @Test void importCinemasInvalidId() {
    try {
      errors = DataController.importCinemas(cinemas, "importCinemasInvalidId.csv");
    } catch (IOException e) {
    }
    assertEquals(2,errors.size());
    assertEquals(1,cinemas.size());
  }

  @Test void importCinemasInvalidScreen() {

    try {
      errors = DataController.importCinemas(cinemas, "importCinemasInvalidScreen.csv");
    } catch (IOException e) {
    }

    assertEquals(4,errors.size());
    assertEquals(7,cinemas.size());

  }

  @Test void importCinemasMissingFields() {

    try {
      errors = DataController.importCinemas(cinemas, "importCinemasMissingFields.csv");
    } catch (IOException e) {
    }

    assertEquals(1,errors.size());
    assertEquals(7,cinemas.size());
  }

  @Test void importMoviesWithNullFileName() {

    Exception e = assertThrows(FileNotFoundException.class, () -> {
      DataController.importCinemas(cinemas,null);
    });
  }


  
}
