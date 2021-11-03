package R18_G2_ASM2;

import java.io.IOException;
import java.util.ArrayList;
public class MovieScreen extends Screen {
  private ArrayList<Showing> showings = new ArrayList<>();
  private Login login;
  private Registration reg;

  Movie movie;
  public MovieScreen(MovieSystem mSystem, Movie movie) {
    super(mSystem);
    this.movie = movie;
    this.login = new Login(mSystem);
    this.reg = new Registration(mSystem);
    this.showings = movie.getShowingsBeforeNextMonday();
    title = movie.getName().toUpperCase(); //TODO create variable date
  }

  @Override
  protected void setOptions() {

    if (mSystem.getUser().getUserType() == UserType.GUEST) {
      options = new ArrayList<>();
      options.add("L"); options.add("l");
      options.add("R"); options.add("r");
    }
    options.add("Q"); options.add("q");
    options.add("B"); options.add("b");     
  }

  @Override
  protected void chooseOption() {   
    
    if (intOption != NO_INT_OPTION) {
      if (mSystem.getUser().getUserType() == UserType.CUSTOMER) {
        BookingTicket book = new BookingTicket(showings.get(intOption-1), (Customer) mSystem.getUser());
        book.run();

        try {
          Transaction t = new Transaction((Customer) mSystem.getUser());
          t.run();
        } catch (IOException e) {e.printStackTrace();}

      } else {
        throw new IllegalArgumentException("Critical error - invalid selection passed validation");
      }
      intOption = NO_INT_OPTION;
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
    //System.out.print("Current Date & Time: OCT 27 - THU 9:57PM\n");  // TODO make dynamic time
    maxInputInt = movie.printMovieShowings();
    if (mSystem.getUser().getUserType() == UserType.CUSTOMER) {
      System.out.printf("Hi %s,\n\n", mSystem.getUser().getNickname());
    }

    printOptionsText();
        
    if (mSystem.getUser().getUserType() == UserType.GUEST) {
      System.out.print(formatANSI("R",ANSI_USER_OPTION) + " - To book a session, please register an account (free).\n");
      System.out.print(formatANSI("L",ANSI_USER_OPTION) + " - Already a member? Login\n");
      System.out.print(formatANSI("B",ANSI_USER_OPTION) + " - To go back to all movies\n");
      System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Quit\n\n");
    } else if (mSystem.getUser().getUserType() == UserType.CUSTOMER) {
      System.out.print(formatANSI("[ID]",ANSI_USER_OPTION) +" - To book a session\n");
      System.out.print(formatANSI("B",ANSI_USER_OPTION) + " - To go back to all movies\n");
      System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Log out and Quit\n\n");
    }  else {
      System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Log out and Quit\n\n");
    }
  
  }
}
