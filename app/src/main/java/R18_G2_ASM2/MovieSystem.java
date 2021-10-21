package R18_G2_ASM2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
public class MovieSystem {

  private final String MOVIES_FILE_NAME = "movie.csv";
  private final String CINEMAS_FILE_NAME = "cinema.csv";
  private final String SHOWINGS_FILE_NAME = "showing.csv";

  private HashMap<Integer,Movie> movies = new HashMap<>();
  private HashMap<Integer,Cinema> cinemas = new HashMap<>();
  private HashMap<Integer,Showing> showings = new HashMap<>();

  Scanner sc = new Scanner(System.in);
  
  public void run() {

  while (true) {
    printStartScreen();

    String selection = parseInput("qQ", 3);

    if (selection.equals("1")) {
     try {
      Login login = new Login();
      login.retrieveUserInputDetails();
      } catch (Exception e) {
        System.out.println(e.getStackTrace());
      }
    } else if (selection.equals("2")) {
      try {
        Registration reg = new Registration();
        reg.retrieveUserInputDetails();
      } catch (Exception e) {
        System.out.println(e.getStackTrace());
      }
    } else if (selection.equals("3")) {
      importMovieData();
      Showing.getAllMovieShowings(showings);
    } else if (selection == "q" || selection == "Q") {
      System.out.println("SEE YOU NEXT TIME! :)");
      break;
    }
  }
}
    /*
      User tim = new User(1, "tim@gmail.com", "TestOne12!@");
      BookingTicket t = new BookingTicket(showings.get(2), tim);
      t.run();

      movies.get(1).toString(s);
      Showing.getSingleMovieShowings(showings, s, movies.get(1));
      System.out.println(s);
  */


  //parses either an integer selection OR a single character specified by pattern
  public String parseInput(String pattern, int max) {
    System.out.print("User Input: ");
    while (sc.hasNextLine()) {

      Scanner line = new Scanner(sc.nextLine());

      if (line.hasNextInt()) {
        int inputInt = line.nextInt();

        if (inputInt < 0 || inputInt > max) {
          System.out.println("Invalid selection. Please try again.\n");
          System.out.print("User Input: ");
          line.close();
          continue;
        } else if (line.hasNext()) {
          System.out.println("Invalid selection. Please try again.\n");
          System.out.print("User Input: ");
          line.close();
          continue;       
        }
        line.close();
        return String.valueOf(inputInt);
      } else if (line.hasNext("[" + pattern + "]")) {
        String s = line.next();
        line.close();
        return s;
      } else {
        System.out.println("Invalid selection. Please try again.\n");
        System.out.print("User Input:");
        line.close();
        continue; 
      }
    }
    return null; //should never get here.
  }

  public void importMovieData() {
    try {
      DataController.importMovies(movies,MOVIES_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Error reading file: " + MOVIES_FILE_NAME);
    } 

    try {
      DataController.importCinemas(cinemas,CINEMAS_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Error reading file: " + CINEMAS_FILE_NAME);
    } 

    try {
      DataController.importShowings(movies,cinemas, showings, SHOWINGS_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Error reading file: " + SHOWINGS_FILE_NAME);
    } 
  }

  public void printStartScreen() {

    StringBuilder s = new StringBuilder();
    s.append("******************************************************************\n");
    // s.append("------------------------------------------------------------------\n");
    s.append("\nWELCOME TO FANCY CINEMAS! PLEASE CHOOSE FROM THE FOLLOWING OPTIONS\n");
    // s.append("------------------------------------------------------------------\n");
    s.append("\n******************************************************************\n");

    s.append("1 - Log In\n");
    s.append("2 - Register\n");
    s.append("3 - Show Movies\n");  
    s.append("Q - Quit\n");

    System.out.println(s);    
  }

  

}
