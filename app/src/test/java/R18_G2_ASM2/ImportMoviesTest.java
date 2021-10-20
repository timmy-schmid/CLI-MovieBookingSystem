package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ImportMoviesTest {
  
  private HashMap<Integer,Movie> movies;
  private HashMap<Integer,String> errors; 

  @BeforeAll static void initialSetup() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach void refreshVariables() {
    movies = new HashMap<>();
    errors = new HashMap<>();
  }

  @AfterEach void printErrors() {
    DataController.printErrorMap(errors);
  }

  @Test void importMoviesInvalidClassification() {
    try {
      errors = DataController.importMovies(movies, "importMoviesInvalidClassification.csv");
    } catch (IOException e) {
    }
    assertEquals(4,errors.size());
    assertEquals(1,movies.size());
  }

  @Test void importMoviesCastSplit() {

    try {
      errors = DataController.importMovies(movies, "importMoviesCastSplit.csv");
    } catch (IOException e) {
    }

    assertEquals(0,errors.size());
    assertEquals(5,movies.size());
    assertEquals(1,movies.get(1).getCast().size());
    assertEquals(1,movies.get(2).getCast().size());
    assertEquals(6,movies.get(3).getCast().size());
    assertEquals(6,movies.get(4).getCast().size());
    assertEquals(6,movies.get(5).getCast().size());
  }

  @Test void importMoviesWithInvalidID() {

    try {
      errors = DataController.importMovies(movies, "importMoviesWithInvalidID.csv");
    } catch (IOException e) {
    }

    assertEquals(4,errors.size());
    assertEquals(2,movies.size());
  }

  @Test void importMoviesDirectorsSplit() {

    try {
      errors = DataController.importMovies(movies, "importMoviesDirectorsSplit.csv");
    } catch (IOException e) {
    }

    assertEquals(0,errors.size());
    assertEquals(5,movies.size());
    assertEquals(3,movies.get(1).getDirectors().size());
    assertEquals(1,movies.get(2).getDirectors().size());
    assertEquals(1,movies.get(3).getDirectors().size());
    assertEquals(2,movies.get(4).getDirectors().size());
    assertEquals(1,movies.get(5).getDirectors().size());
  }
  @Test void importMoviesRowsWithMissingFields() {

    try {
      errors = DataController.importMovies(movies, "importMoviesRowsWithMissingFields.csv");
    } catch (IOException e) {
    }

    assertEquals(2,errors.size());
    assertEquals(3,movies.size());
  }
  @Test void importMoviesWithInvalidDateFormat() {

    try {
      errors = DataController.importMovies(movies, "importMoviesWithInvalidDateFormat.csv");
    } catch (IOException e) {
    }

    assertEquals(3,errors.size());
    assertEquals(17,movies.size());
  }

  @Test void importMoviesWithNullFileName() {

    Exception e = assertThrows(FileNotFoundException.class, () -> {
      DataController.importMovies(movies,null);
    });
  }


  
}
