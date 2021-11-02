package R18_G2_ASM2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;
public class MovieSystem {

  private String MOVIES_FILE_NAME = "movie.csv";
  private String CINEMAS_FILE_NAME = "cinema.csv";
  private String SHOWINGS_FILE_NAME = "showing.csv";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_YELLOW = "\u001B[33m";

  private HashMap<Integer,Movie> movies = new HashMap<>();
  private HashMap<Integer,Cinema> cinemas = new HashMap<>();

  User currentUser = null;

  private Scanner sc;
  private PrintStream out;
  
  public MovieSystem(ByteArrayInputStream in, PrintStream out) {
    this.sc = new Scanner(in);
    this.out = out;

    importMovieData();
  }

  public MovieSystem() {
    this.sc = new Scanner(System.in);
    this.out = System.out;
    importMovieData();
  }

  public void run() throws IOException {

    HomeScreen home = new HomeScreen(movies);
    StartScreen startScreen = new StartScreen(home);

    startScreen.run();
    //test booking ticket
    // Showing show = new Showing(2, new Movie(1, null, null, null, null, null, null), new Cinema(1, MovieClass.GOLD), null);

    // BookingTicket bt = new BookingTicket(show, new User(1, "Nick", "123@gmail.com", "123123123", "00000"));
    // bt.run();
  }

  public void importMovieData() {
    try {
      DataController.importMovies(movies,MOVIES_FILE_NAME);
    } catch (IOException e) {
      out.println("Error reading file: " + MOVIES_FILE_NAME);
    } 

    try {
      DataController.importCinemas(cinemas,CINEMAS_FILE_NAME);
    } catch (IOException e) {
      out.println("Error reading file: " + CINEMAS_FILE_NAME);
    } 

    try {
      DataController.importShowings(movies,cinemas, SHOWINGS_FILE_NAME);
    } catch (IOException e) {
      out.println("Error reading file: " + SHOWINGS_FILE_NAME);
    } 
  }

  public void setMovieDataFile(String name) {
    MOVIES_FILE_NAME = name;
  }

  public void setCinemaDataFile(String name) {
    SHOWINGS_FILE_NAME = name;
  }

  public void setShowingDataFile(String name) {
    CINEMAS_FILE_NAME = name;
  }




}
