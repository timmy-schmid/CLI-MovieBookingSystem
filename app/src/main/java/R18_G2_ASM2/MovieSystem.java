package R18_G2_ASM2;

import java.io.IOException;
import java.util.HashMap;
public class MovieSystem {

  private final String MOVIES_FILE_NAME = "movie.csv";
  private final String CINEMAS_FILE_NAME = "cinema.csv";
  private final String SHOWINGS_FILE_NAME = "showing.csv";

  private HashMap<Integer,Movie> movies = new HashMap<>();
  private HashMap<Integer,Cinema> cinemas = new HashMap<>();
  private HashMap<Integer,Showing> showings = new HashMap<>();

  private HashMap<Integer,String> movieReadErrors = new HashMap<>();
  private HashMap<Integer,String> cinemaReadErrors = new HashMap<>();
  private HashMap<Integer,String> showingReadErrors = new HashMap<>();

  public void run() {
    try {
      movieReadErrors = DataController.importMovies(movies,MOVIES_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Error reading file: " + MOVIES_FILE_NAME);
    } 

    try {
      cinemaReadErrors = DataController.importCinemas(cinemas,CINEMAS_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Error reading file: " + CINEMAS_FILE_NAME);
    } 

    try {
      showingReadErrors = DataController.importShowings(movies,cinemas, showings, SHOWINGS_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Error reading file: " + SHOWINGS_FILE_NAME);
    } 
    DataController.printErrorMap(movieReadErrors);
    DataController.printErrorMap(cinemaReadErrors);
    DataController.printErrorMap(showingReadErrors);

    StringBuilder s = new StringBuilder();
    Showing.getAllMovieShowings(showings, s);
    s.append("\n\n");
    movies.get(1).toString(s);
    Showing.getSingleMovieShowings(showings, s, movies.get(1));
    System.out.println(s);
  }




  

}
