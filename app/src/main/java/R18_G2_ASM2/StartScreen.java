package R18_G2_ASM2;

import java.io.PrintStream;
import java.util.Scanner;

public class StartScreen extends Screen {

  private Login login;
  private Registration reg;
  private HomeScreen home;

  public StartScreen(HomeScreen home) {
    super();

    this.login = new Login();
    this.reg = new Registration();
    this.home = home;

    options.add("1");
    options.add("2");
    options.add("3");
    options.add("Q"); options.add("q");

    title = "WELCOME TO FANCY CINEMAS!";
    maxInputInt = 3;
  }

  public StartScreen(PrintStream out) {
    super(out);
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
  public void chooseOption() {

    switch (selectedOption) {
      case "1": login.run(reader);
                break;
      case "2": reg.run(reader);
                break;
      case "3": home.run(reader);
                break;
      case "Q": case "q":
        out.print("SEE YOU NEXT TIME! :)\n");
        System.exit(0);
        break;
      default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
    }
  }

  @Override
  public void print() {

    printHeader();

    printOptionsText();

    out.print(formatANSI("1",ANSI_USER_OPTION) + " - Log In\n");
    out.print(formatANSI("2",ANSI_USER_OPTION) + " - Register\n");
    out.print(formatANSI("3",ANSI_USER_OPTION) + " - View upcoming showings (continue as guest)\n");  
    out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Quit\n\n");

  }


}
