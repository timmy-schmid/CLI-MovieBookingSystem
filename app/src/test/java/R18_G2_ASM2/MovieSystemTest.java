package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.PrintStream;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class MovieSystemTest {

  private static ByteArrayInputStream mockIn;
  private static ByteArrayOutputStream actualOut;
  private static PrintStream actualOutPrint;
  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

  MovieSystem  movSystem;

  @BeforeAll static void importData() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach void resetSystem() {
    actualOut = new ByteArrayOutputStream();
    actualOutPrint = new PrintStream(actualOut);
  }
  /* TODO move these tests somewhere else


  @Test void parseInputTestInvalidInteger() {
    mockIn = new ByteArrayInputStream("-1\n-980\n100\n10 a\n10\n".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);

  @Test void quitTest() {
    mockIn = new ByteArrayInputStream("".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);
    movSystem.quit();
    assertEquals("SEE YOU NEXT TIME! :)\n",actualOut.toString()); 
  }
  /* TODO fix this test.
  @Test void printMovieScreenTest() {
    mockIn = new ByteArrayInputStream("".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);

    ArrayList<String> testCast = new ArrayList<>();

    testCast.add("actor one");
    testCast.add("actor two");

    ArrayList<String> testDirector = new ArrayList<>();
    testDirector.add("director");

    Calendar releaseDate = Calendar.getInstance(AEST,Locale.ENGLISH);
    releaseDate.set(2021,11,20);

    Movie testMovie = new Movie(10,"Some Movie", testCast,Classification.PG,testDirector,"some movie description",releaseDate);

    String screen = "\033[H\033[2J" +
                    "*********************************************************" +
                    "**************************************\n" +
                    "\u001B[33m" +
                    "                                   SOME MOVIE\n" +
                    "\u001B[0m" +
                    "*********************************************************" +
                    "**************************************\n" +
                    "SYNOPSIS: some movie description\n\n" +
                    "CLASSIFICATION: PG\n" +
                    "RELEASE DATE: 20/12/21\n" +
                    "DIRECTORS: director\n" +
                    "CAST: actor one, actor two\n" +
                    "UPCOMING SESSIONS:\n" +
                    "-----------------------------------------\n" +
                    "ID  TIME                 CINEMA\n" +
                    "-----------------------------------------\n\n" +
                    "If you would like to go back press \u001B[34mB\u001B[0m\n";

    movSystem.printMovieScreen(testMovie);
    assertEquals(screen,actualOut.toString()); 
  }*/
/* TODO BROKEN TEST
  @Test void printStartScreenTest() {
    mockIn = new ByteArrayInputStream("".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);
    movSystem.printStartScreen();

    // String screen = "******************************************************************\n" +
    //                 "\nWELCOME TO FANCY CINEMAS! PLEASE CHOOSE FROM THE FOLLOWING OPTIONS\n" +
    //                 "\n******************************************************************\n" +
    //                 "1 - Log In\n2 - Register\n3 - View upcoming showings\nQ - Quit\n\n";
    String screen = "******************************************************************\n" +
    "\nWELCOME TO FANCY CINEMAS! PLEASE CHOOSE FROM THE FOLLOWING OPTIONS\n" +
    "\n******************************************************************\n" +
    "1 - Log In\n2 - Register\n3 - View upcoming showings\n4 - Direct to transaction page\nQ - Quit\n\n";
    assertEquals(screen,actualOut.toString());
  }*/

  @Test void importMovieDataTestWithFalseFiles() {

    mockIn = new ByteArrayInputStream("".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);

    movSystem.setMovieDataFile("badFileName.csv");
    movSystem.setCinemaDataFile("badFileName2.csv");
    movSystem.setShowingDataFile("badFileName3.csv");

    movSystem.importMovieData();
    String errors = "Error reading file: badFileName.csv\n" +
                    "Error reading file: badFileName3.csv\n" +
                    "Error reading file: badFileName2.csv\n";

    assertEquals(errors,actualOut.toString());
  }

  @Test void importMovieDataTestWithGoodData() {
    mockIn = new ByteArrayInputStream("".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);
    DataController.setBasePath("src/test/resources/");
    movSystem.setMovieDataFile("movie1.csv");
    movSystem.setCinemaDataFile("cinema1.csv");
    movSystem.setShowingDataFile("showing1.csv");

    movSystem.importMovieData();
    assertEquals("",actualOut.toString());
  }
  /*
  @Test void printShowingsScreenTest() {
    mockIn = new ByteArrayInputStream("".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);

    movSystem.setMovieDataFile("movie.csv");
    movSystem.setCinemaDataFile("cinema.csv");
    movSystem.setShowingDataFile("showing.csv");

    movSystem.importMovieData();
    
    movSystem.printShowingsScreen();
    String screen = "*********************************************************" +
                    "**************************************\n" +
                    "                                       SHOWINGS PAGE\n" +
                    "*********************************************************" +
                    "**************************************\n" +
                    "Welcome,\n" +
                    "\nPlease select from the following options:\n" +  
                    "  \u001B[34m[ID]\u001B[0m - to see further details about a particular movie (listed above).\n" +
                    "   \u001B[34mQ\u001B[0m - to log out and quit\n\n\n";
    
  assertEquals(screen,actualOut.toString());
  }*/
}
