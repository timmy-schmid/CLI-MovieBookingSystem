package R18_G2_ASM2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
public class MovieSystem {

  private final String MOVIES_FILE_NAME = "movie.csv";
  private final String CINEMAS_FILE_NAME = "cinema.csv";
  private final String SHOWINGS_FILE_NAME = "showing.csv";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_YELLOW = "\u001B[33m";

  private HashMap<Integer,Movie> movies = new HashMap<>();
  private HashMap<Integer,Cinema> cinemas = new HashMap<>();
  private HashMap<Integer,Showing> showings = new HashMap<>();
  User currentUser = null;

  Scanner sc = new Scanner(System.in);
  
  public void run() {

  while (true) {
    printStartScreen();
    Registration reg = new Registration();
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
        currentUser = reg.retrieveUserInputDetails();
      } catch (Exception e) {
        System.out.println(e.getStackTrace());
      }
    } else if (selection.equals("3")) {
      importMovieData();
      Showing.getAllMovieShowings(showings);
      printShowingsScreen();

      if (currentUser != null) {
        selection = parseInput("EeQq",showings.size());
        if (selection.equals("E") || selection.equals("E")) {
          editUser();
        }
      } else {
        selection = parseInput("RrQq",showings.size());
        if (selection.equals("R") || selection.equals("r")) {
          try {
            currentUser = reg.retrieveUserInputDetails();
          } catch (Exception e) {
            System.out.println(e.getStackTrace());
          }
        } 
      }
      if (selection.equals("q") || selection.equals("Q")) {
        quit();
        return;
      } else {

        int movieId = Integer.parseInt(selection);
        printMovieScreen(movies.get(movieId));

        selection = parseInput("bB",0);
      }


    } else if (selection.equals("q") || selection.equals("Q")) {
      quit();
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
    s.append("\nWELCOME TO FANCY CINEMAS! PLEASE CHOOSE FROM THE FOLLOWING OPTIONS\n");
    s.append("\n******************************************************************\n");

    s.append("1 - Log In\n");
    s.append("2 - Register\n");
    s.append("3 - View upcoming showings\n");  
    s.append("Q - Quit\n");

    System.out.println(s);    
  }

  public void printShowingsScreen() {
    StringBuilder s = new StringBuilder();

    printBar(s);
    s.append("                                       SHOWINGS PAGE\n");
    printBar(s);


    if (currentUser != null) {
      s.append(String.format("Welcome, %s,\n",currentUser.getEmail()));
    } else {
      s.append(String.format("Welcome,\n"));
    }

    s.append("\nPlease select from the following options:\n");
    s.append(String.format("  %s - to see further details about a particular movie (listed above).\n",wrapColour("[ID]")));

    if (currentUser != null) {
      s.append(String.format("   %s - to edit account details\n", wrapColour("E")));
    } else {
      s.append(String.format("   %s - to register an account\n", wrapColour("R")));
    }
  
    s.append(String.format("   %s - to log out and quit\n\n", wrapColour("Q")));

    System.out.println(s);

  }

  public void printMovieScreen(Movie m) {
    StringBuilder s = new StringBuilder();

    s.append("\033[H\033[2J"); //clears console

    MovieSystem.printBar(s);
    s.append(ANSI_YELLOW);
    s.append(String.format("                                   %s\n",m.getName().toUpperCase()));
    s.append(ANSI_RESET); 
    MovieSystem.printBar(s);

    m.printMovieDetails(s);
    Showing.getSingleMovieShowings(showings,s, m);

    s.append("\nIf you would like to go back to all showings press " + wrapColour("B"));
      
    System.out.println(s);


  }

  public void quit() {
    System.out.println("SEE YOU NEXT TIME! :)");   
  }

  private String wrapColour(String s) {
    return ANSI_BLUE + s + ANSI_RESET;
  }


  public void editUser() {

    EditInformation edit = new EditInformation(currentUser);
    edit.giveChoice();

  }
  

  public static void printBar(StringBuilder s) {
    s.append("*********************************************************");
    s.append("**************************************\n");
  }


}
