package R18_G2_ASM2;
public class StartScreen extends Screen {

  private Login login;
  private Registration reg;
  private HomeScreen home;

  public StartScreen(HomeScreen home) {
    super();

    this.login = new Login(home);
    this.reg = new Registration(home);
    this.home = home;

    options.add("1");
    options.add("2");
    options.add("3");
    options.add("Q"); options.add("q");

    title = "WELCOME TO FANCY CINEMAS!";
    maxInputInt = 3;
  }

  @Override
  public void chooseOption() {

    switch (selectedOption) {
      case "1": login.run();
                break;
      case "2": reg.run();
                break;
      case "3": home.run();
                break;
      case "Q": case "q":
        System.out.print("SEE YOU NEXT TIME! :)\n");
        System.exit(0);
        break;
      default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
    }
  }

  @Override
  public void print() {
    clearScreen();
    printHeader();

    printOptionsText();

    System.out.print(formatANSI("1",ANSI_USER_OPTION) + " - Log In\n");
    System.out.print(formatANSI("2",ANSI_USER_OPTION) + " - Register\n");
    System.out.print(formatANSI("3",ANSI_USER_OPTION) + " - View upcoming showings (continue as guest)\n");  
    System.out.print(formatANSI("Q",ANSI_USER_OPTION) + " - Quit\n\n");

  }


}
