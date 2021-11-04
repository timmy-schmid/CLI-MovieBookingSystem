package R18_G2_ASM2;

import java.io.IOException;
import java.util.ArrayList;
public class HomeScreen extends Screen {

  private ArrayList<Movie> moviesSorted;
  private Login login;
  private Registration reg;
  private MovieScreen movScreen;
  private EditInformation edit;

  public HomeScreen(MovieSystem mSystem) {
    super(mSystem);
    this.login = new Login(mSystem);
    this.reg = new Registration(mSystem);
    title = "CURRENT SHOWINGS (25 OCT - 31 OCT)"; //TODO create variable date
  }

  @Override
  protected void setOptions() {
    if (mSystem.getUser().getUserType() == UserType.GUEST) {
      options = new ArrayList<>();
      options.add("L"); options.add("l");
      options.add("R"); options.add("r");
    } else {
      options.add("E"); options.add("e");
    }

    options.add("Q"); options.add("q");
    //options.add("A"); options.add("a");
    //options.add("G"); options.add("g");
    //options.add("S"); options.add("s");
    //options.add("B"); options.add("b");
  }

  @Override
  protected void chooseOption() {
    if (intOption != NO_INT_OPTION) {
      movScreen = new MovieScreen(mSystem,moviesSorted.get(intOption-1));
      movScreen.run();
      intOption = NO_INT_OPTION;
    } else {
      switch (selectedOption) {
        case "L": case "l":
          login.run();
          break;
        case "R": case "r":
          reg.run();
          break;
        case "E": case "e":
          edit = new EditInformation(mSystem.getUser());
          edit.run();
          break;
        case "G": case "g":
          // TODO add gold filter
          break;
        case "S": case "s":
          // TODO add silver filter
          break;
        case "B": case "b":
          // TODO add bronze filter
          break;
        case "A": case "a":
          // TODO add std filter
          break;
        case "Q": case "q":
          System.out.print("SEE YOU NEXT TIME! :)\n");
          System.exit(0);
          break;
        default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
      }
    }
  }

  @Override
  public void print() {
    clearScreen();
    //System.out.print("Current Date & Time: OCT 27 - THU 9:57PM\n");  // TODO make dynamic time
    moviesSorted = Movie.printAllShowings(mSystem.getMovies());
    maxInputInt = moviesSorted.size();

    printHeader();

    System.out.printf("Welcome %s,\n\n", mSystem.getUser().getNickname());

    printOptionsText();

    System.out.print(formatANSI("[ID]",ANSI_USER_OPTION) +" - To see further details about a particular movie (listed above)\n");
    /*
    System.out.print(formatANSI("[G|S|B]",ANSI_USER_OPTION) +
              " - To filter showings by " +
              formatANSI("Gold",ANSI_GOLD) + ", " +
              formatANSI("Silver",ANSI_SILVER) + " or " +
              formatANSI("Bronze",ANSI_BRONZE) + " screens\n");
    
              System.out.print(formatANSI("A",ANSI_USER_OPTION) + " - To display all showings for the coming week (default)\n");*/
    
    if (mSystem.getUser().getUserType() == UserType.GUEST) {
      System.out.print(formatANSI("R",ANSI_USER_OPTION) + " - To register an account (free)\n");
      System.out.print(formatANSI("L",ANSI_USER_OPTION) + " - Already a member? Login\n");
      System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Quit\n\n");
    } else {
      System.out.print(formatANSI("E",ANSI_USER_OPTION) + " - To edit your account details\n");
      System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Log out and Quit\n\n");

    }
  
  }
}
