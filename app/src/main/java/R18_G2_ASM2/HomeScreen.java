package R18_G2_ASM2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HomeScreen extends Screen {

  private static User user;
  private HashMap<Integer,Showing> showings = new HashMap<>();
  private Login login;
  private Registration reg;

  public HomeScreen(HashMap<Integer,Showing> shows) {
    super();

    this.login = new Login();
    this.reg = new Registration();
    this.showings = shows;
    setUser(null);
    title = "CURRENT SHOWINGS (25 OCT - 31 OCT)"; //TODO create variable date
  }

  public void setUser(User user) {
    HomeScreen.user = user;

    if (user == null) {
      options = new ArrayList<>();
      options.add("L"); options.add("l");
      options.add("R"); options.add("r");

    } else {
      options.add("E"); options.add("e");
    }
    
    options.add("Q"); options.add("q");
    options.add("A"); options.add("a");
    options.add("G"); options.add("g");
    options.add("S"); options.add("s");
    options.add("B"); options.add("b");
  }

  @Override
  public void run(Scanner sc) {
    this.reader = sc;
    while (true) {
      print();
      askforInput();
      chooseOption();
    }
  }

  @Override
  protected void chooseOption() {

    if (intOption != NO_INT_OPTION) {

    }

    switch (selectedOption) {
      case "L": case "l":
        login.run(reader);
        break;
      case "R": case "r":
        reg.run(reader);
        break;
      case "E": case "e":
        // TODO add edit
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
      case "Q": out.print("SEE YOU NEXT TIME! :)\n");
        System.exit(0);
        break;
      default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
    }
  }

  @Override
  public void print() {

    printHeader();

    if (user == null) {
      out.print ("Welcome guest,\n\n");
    } else {
      out.printf("Welcome %s,\n\n", user.getEmail());
    }

    printOptionsText();

    out.print(formatANSI("[ID]",ANSI_USER_OPTION) +" - To see further details about a particular movie (listed below)\n");
    
    out.print(formatANSI("[G|S|B]",ANSI_USER_OPTION) +
              " - To filter showings by " +
              formatANSI("Gold",ANSI_GOLD) + ", " +
              formatANSI("Silver",ANSI_SILVER) + " or " +
              formatANSI("Bronze",ANSI_BRONZE) + " screens\n");
    
              out.print(formatANSI("A",ANSI_USER_OPTION) + " - To display all showings for the coming week (default)\n");  
    
    if (user == null) {
      out.print(formatANSI("R",ANSI_USER_OPTION) + " - To register an account (free)\n");
      out.print(formatANSI("L",ANSI_USER_OPTION) + " - Already a member? Login\n");
      out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Quit\n\n");
    } else {
      out.print(formatANSI("E",ANSI_USER_OPTION) + " - To edit your account details\n");
      out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Log out and Quit\n\n");

    }
    out.print("Current Date & Time: OCT 27 - THU 9:57PM\n");  // TODO make dynamic time
   
    Showing.getAllMovieShowings(showings);
  }

}
