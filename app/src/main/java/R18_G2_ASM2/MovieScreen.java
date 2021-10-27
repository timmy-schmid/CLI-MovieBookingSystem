package R18_G2_ASM2;

import java.util.ArrayList;
public class MovieScreen extends Screen {

  private static User user;
  private ArrayList<Showing> showings = new ArrayList<>();
  private Login login;
  private Registration reg;

  Movie movie;
  HomeScreen home;

  public MovieScreen(Movie movie) {
    super();
    this.movie = movie;
    this.login = new Login(home);
    this.reg = new Registration(home);
    this.showings = movie.getShowingsBeforeNextMonday();
 
    setUser(HomeScreen.getUser());
    title = movie.getName().toUpperCase(); //TODO create variable date
  }

  public void setUser(User user) {
    MovieScreen.user = user;

    if (user == null) {
      options = new ArrayList<>();
      options.add("L"); options.add("l");
      options.add("R"); options.add("r");

    }

    options.add("Q"); options.add("q");
    options.add("B"); options.add("b");
  }

  @Override
  protected void chooseOption() {   
    System.out.println("OPTION:" + intOption);
    System.out.println("MAX:"+maxInputInt);
    System.out.println("Line:"+selectedOption);
    System.out.println("goBack:"+goBack);
    if (intOption != NO_INT_OPTION) {
      BookingTicket book = new BookingTicket(showings.get(intOption),user);
      book.run();
    } else {
      switch (selectedOption) {
        case "L": case "l":
          login.run();
          break;
        case "R": case "r":
          reg.run();
          break;
        case "B": case "b":
          goBack = true;
          break;
        case "Q": case "q":
          System.out.print("SEE YOU NEXT TIME! :)\n");
          System.exit(0);
        default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
      }
    }
  }

  @Override
  public void print() {
    clearScreen();
    printHeader();

    movie.printMovieDetails();
    System.out.print("UPCOMING SESSIONS:\n");
    System.out.print("Current Date & Time: OCT 27 - THU 9:57PM\n");  // TODO make dynamic time
    maxInputInt = movie.printMovieShowings();
    if (user != null) {
      System.out.printf("Hi %s,\n\n", user.getEmail());
    }

    printOptionsText();
        
    if (user == null) {
      System.out.print(formatANSI("R",ANSI_USER_OPTION) + " - To book a session, please register an account (free).\n");
      System.out.print(formatANSI("L",ANSI_USER_OPTION) + " - Already a member? Login\n");
      System.out.print(formatANSI("B",ANSI_USER_OPTION) + " - To go back to all movies\n");
      System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Quit\n\n");
    } else {
      System.out.print(formatANSI("[ID]",ANSI_USER_OPTION) +" - To book a session\n");
      System.out.print(formatANSI("B",ANSI_USER_OPTION) + " - To go back to all movies\n");
      System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Log out and Quit\n\n");
    }
  
  }

}
