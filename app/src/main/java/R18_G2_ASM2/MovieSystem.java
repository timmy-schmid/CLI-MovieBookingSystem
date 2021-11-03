package R18_G2_ASM2;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class MovieSystem {

  private String MOVIES_FILE_NAME = "movie.csv";
  private String CINEMAS_FILE_NAME = "cinema.csv";
  private String SHOWINGS_FILE_NAME = "showing.csv";

  private HashMap<Integer,Movie> movies = new HashMap<>();
  private HashMap<Integer,Cinema> cinemas = new HashMap<>();

  User user;
  private PrintStream out;
  
  public MovieSystem(InputStream in, PrintStream out) {
    this.out = out;
    this.user = new Guest();
    importMovieData();
  }

  public MovieSystem() {
    this(System.in, System.out);
  }

  public void run() {
    StartScreen startScreen = new StartScreen(this);
    startScreen.run();
  }

  public HashMap<Integer,Movie> getMovies() {
    return movies;
  }

  public HashMap<Integer,Cinema> getCinemas() {
    return cinemas;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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






}
