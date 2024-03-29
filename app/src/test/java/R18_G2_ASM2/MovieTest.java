package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class MovieTest {

  private HashMap<Integer,Movie> movies;
  private HashMap<Integer,Cinema> cinemas;
  private ArrayList<Showing> showings;
  private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
  private final DateFormat df2 = new SimpleDateFormat("EEE dd MMM - K:mma",Locale.ENGLISH);
  private final DateFormat df3 = new SimpleDateFormat("EEE K:mma",Locale.ENGLISH);

  public static final String ANSI_BRONZE = "\033[38;2;176;141;87;m";
  public static final String ANSI_SILVER = "\033[38;2;170;169;173;m";
  public static final String ANSI_GOLD = "\033[38;2;212;175;55;m";
  public static final String ANSI_RESET = "\u001B[0m";
  final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

  private static ByteArrayOutputStream actualOut;
  ArrayList<Calendar> showingTime;

  @BeforeAll static void setup() {
    actualOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(actualOut));
  }
  
  @BeforeEach void setupTestData() {
    actualOut.reset(); 
    movies = new HashMap<>();
    cinemas = new HashMap<>();
    showings = new ArrayList<>();

    df.setTimeZone(AEST); df2.setTimeZone(AEST); df3.setTimeZone(AEST);
    
    String[] cast1Array = {"actor 1", "actor 2"};
    String[] cast2Array = {"actor 1", "actor 2", "actor 3", "actor 4", "actor 5", "actor 6"};

    String[] dir1Array = {"director 1", "director 2"};
    String[] dir2Array = {"director 1", "director 2", "director 3", "director 4", "director 5", "director 6"};
    
    List<String> cast1 = Arrays.asList(cast1Array);
    List<String> cast2 = Arrays.asList(cast2Array);
    List<String> dir1 = Arrays.asList(dir1Array);
    List<String> dir2 = Arrays.asList(dir2Array);

    ArrayList<Calendar> releaseDate= new ArrayList<>();
    for (int i = 0; i < 6; i++ ) {
      releaseDate.add(Calendar.getInstance(Locale.ENGLISH));
      releaseDate.get(i).set(2021, 9, i+1);
    }
    movies.put(1, new Movie(1,"Mov 1",cast1,Classification.G,dir1,"synop 1",releaseDate.get(0)));
    movies.put(2, new Movie(2,"Mov 2",cast1,Classification.G,dir1,"synop 2",releaseDate.get(1)));
    movies.put(3, new Movie(3,"Mov 3",cast1,Classification.G,dir1,"synop 3",releaseDate.get(2)));
    movies.put(4, new Movie(4,"Mov 4",cast2,Classification.G,dir2,"synop 4",releaseDate.get(3)));
    movies.put(5, new Movie(5,"Mov 5",cast2,Classification.G,dir2,"synop 5",releaseDate.get(4)));
    movies.put(6, new Movie(6,"Mov 6",cast2,Classification.G,dir2,"synop 6",releaseDate.get(5)));
  

    cinemas.put(1, new Cinema(1,MovieClass.BRONZE));
    cinemas.put(2, new Cinema(1,MovieClass.SILVER));
    cinemas.put(3, new Cinema(1,MovieClass.GOLD));

    showingTime = new ArrayList<>();
    for (int i = 0; i < 6; i++ ) {
      showingTime.add(Calendar.getInstance(AEST, Locale.ENGLISH));
      showingTime.get(i).add(Calendar.HOUR,24 + i);
    }
    Calendar inThePast = Calendar.getInstance(AEST, Locale.ENGLISH);

    inThePast.setTimeInMillis(1635455293000L);
    try {
      showings.add(new Showing(1,movies.get(1),cinemas.get(1),showingTime.get(0)));
      showings.add(new Showing(1,movies.get(1),cinemas.get(2),showingTime.get(1)));
      showings.add(new Showing(1,movies.get(1),cinemas.get(2),showingTime.get(2)));
      showings.add(new Showing(1,movies.get(2),cinemas.get(3),showingTime.get(3)));
      showings.add(new Showing(1,movies.get(2),cinemas.get(3),showingTime.get(4)));
      showings.add(new Showing(1,movies.get(3),cinemas.get(3),showingTime.get(5)));
      showings.add(new Showing(1,movies.get(1),cinemas.get(3),inThePast));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test void testGetterMethods() {
    assertEquals(1,movies.get(1).getId());
    assertEquals("Mov 1",movies.get(1).getName());
    assertEquals("actor 1",movies.get(1).getCast().get(0));
    assertEquals("actor 2",movies.get(1).getCast().get(1));
    assertEquals(Classification.G,movies.get(1).getClassification());
    assertEquals("director 1",movies.get(1).getDirectors().get(0));
    assertEquals("director 2",movies.get(1).getDirectors().get(1));
    assertEquals("synop 1",movies.get(1).getSynopsis()); 
    assertEquals("2021-10-01",df.format(movies.get(1).getReleaseDate().getTime())); //assertion failed, expected: 2021-10-02
  }

  //TODO test null behavior.
  @Test void testConstructor() {
    for (int j = 0; j < 6; j++){
      assertNotNull(showings.get(j));
    }
    for (Map.Entry<Integer, Movie> entry: movies.entrySet()){
      assertNotNull(entry.getValue());
    }
  }

  @Test void testPrintMovieDetails() {

    movies.get(1).addShowing(showings.get(0));
    movies.get(1).addShowing(showings.get(1));
    movies.get(1).addShowing(showings.get(2));
    movies.get(1).addShowing(showings.get(6));
    assertEquals(3,movies.get(1).printMovieShowings());


    String expected = "-----------------------------------------\n" +
    "ID  TIME                 CINEMA\n" +
    "-----------------------------------------\n" +
    "1   " + String.format("%-21s",df2.format(showingTime.get(0).getTime()).toUpperCase()) + "1 - BRONZE CLASS\n" +
    "2   " + String.format("%-21s",df2.format(showingTime.get(1).getTime()).toUpperCase()) + "1 - SILVER CLASS\n" +
    "3   " + String.format("%-21s",df2.format(showingTime.get(2).getTime()).toUpperCase()) + "1 - SILVER CLASS\n\n";

    assertEquals(expected,actualOut.toString());

  }
  @Test void printMovieScreenTest() {
    movies.get(1).printMovieDetails();
    String screen = "SYNOPSIS: synop 1\n\n" +
                    "CLASSIFICATION: G\n" +
                    "RELEASE DATE: 01/10/21\n" +
                    "DIRECTORS: director 1, director 2\n" +
                    "CAST: actor 1, actor 2\n\n";

    assertEquals(screen,actualOut.toString()); 
  }

  @Test void testPrintMovieShowings() {
    //public static ArrayList<Movie> printAllShowings(HashMap<Integer,Movie> movies) {

    movies.get(1).addShowing(showings.get(0));
    movies.get(1).addShowing(showings.get(1));
    movies.get(1).addShowing(showings.get(2));
    movies.get(1).addShowing(showings.get(6));
    assertEquals(3,movies.get(1).printMovieShowings());


    String expected = "-----------------------------------------\n" +
    "ID  TIME                 CINEMA\n" +
    "-----------------------------------------\n" +
    "1   " + String.format("%-21s",df2.format(showingTime.get(0).getTime()).toUpperCase()) + "1 - BRONZE CLASS\n" +
    "2   " + String.format("%-21s",df2.format(showingTime.get(1).getTime()).toUpperCase()) + "1 - SILVER CLASS\n" +
    "3   " + String.format("%-21s",df2.format(showingTime.get(2).getTime()).toUpperCase()) + "1 - SILVER CLASS\n\n";

    assertEquals(expected,actualOut.toString());

  }

  @Test void testprintAllShowings() {

    movies.get(1).addShowing(showings.get(0));
    movies.get(1).addShowing(showings.get(1));
    movies.get(1).addShowing(showings.get(2));
    movies.get(2).addShowing(showings.get(3));
    movies.get(2).addShowing(showings.get(4));
    movies.get(3).addShowing(showings.get(5));

    Movie.printAllShowings(movies, Calendar.getInstance(AEST, Locale.ENGLISH), Movie.getWeekEnd(1), DateSize.SHORT);

    String screen = "------------------------------------------------------------------------------------------\n" +
                    "ID  MOVIE                                              TIMES\n" +
                    "------------------------------------------------------------------------------------------\n" +
                    "1   Mov 1                                              " + ANSI_BRONZE + df3.format(showingTime.get(0).getTime()).toUpperCase() + ANSI_RESET + ", " +
                                                                                ANSI_SILVER + df3.format(showingTime.get(1).getTime()).toUpperCase() + ANSI_RESET +  ",\n" +
                    "                                                       " + ANSI_SILVER + df3.format(showingTime.get(2).getTime()).toUpperCase() + ANSI_RESET +  "\n" +
                    "2   Mov 2                                              " + ANSI_GOLD + df3.format(showingTime.get(3).getTime()).toUpperCase() + ANSI_RESET +  ", " +
                                                                                ANSI_GOLD + df3.format(showingTime.get(4).getTime()).toUpperCase() + ANSI_RESET +  "\n" +
                    "3   Mov 3                                              " + ANSI_GOLD + df3.format(showingTime.get(5).getTime()).toUpperCase() + ANSI_RESET +  "\n";
                                  
    assertEquals(screen,actualOut.toString()); 
  }
}
