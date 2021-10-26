package R18_G2_ASM2;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Screen {

  public static final String ANSI_BRONZE = "\033[38;2;176;141;87;m";
  public static final String ANSI_SILVER = "\033[38;2;170;169;173;m";
  public static final String ANSI_GOLD = "\033[38;2;212;175;55;m";

  public static final String ANSI_USER_OPTION = "\033[38;2;100;180;90;m";
  public static final String ANSI_UNAVAIL = "\u001B[34m";

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_CLEAR = "\u001B[0m";

  public static final int NO_INT_OPTION = -1;

  public static final int SCREEN_WIDTH = 90;

  protected ArrayList<String> options;
  protected int intOption = NO_INT_OPTION;
  protected int maxInputInt;

  protected String title;
  protected PrintStream out;
  protected String selectedOption;
  protected Scanner reader;

  public String getTitle() { return title; }

  protected abstract void chooseOption();
  //public abstract void chooseOption(int option);


  public Screen() {
    this.out = System.out;
    options = new ArrayList<>();
  }

  public Screen(PrintStream out) {
    this.out = out;
    options = new ArrayList<>();

  }

  public abstract void run(Scanner sc);

  public abstract void print();


  protected void askforInput() {

    printUserInputText ();

    while (reader.hasNextLine()) {
    
      String line = reader.nextLine();

      // checks if integer, and if is in range.
      try {
        int inputInt = Integer.parseInt(line); 

        if (inputInt <= 0 || inputInt > maxInputInt) { 
          printInvalidSelectionText();
          continue;
        }
      // if not int, checks against valid options.
      } catch (NumberFormatException e) {
        if (!options.contains(line)) {
          printInvalidSelectionText();
          continue;  
        }
      }
      selectedOption = line; // if input is successful.
      break;
    }
  }

  protected void printOptionsText () {
    out.print("Please choose from the following options:\n\n");
  }

  protected void printUserInputText () {
    out.print("User Input:");
  }

  protected void printInvalidSelectionText () {
    out.println("Invalid selection. Please try again.\n");
    printUserInputText ();
  }

  protected void printHeader () {

    int padding = (SCREEN_WIDTH - title.length()) / 2;

    out.print("*********************************************");
    out.print("*********************************************\n");
    out.printf("%" + padding + "s" + title + "\n"," ");
    out.print("*********************************************");
    out.print("*********************************************\n");

  }

  protected void clearScreen() {
    out.println(ANSI_CLEAR);
  }

  public static String formatANSI (String toFormat, String ANSICommand) {
    return ANSICommand + toFormat + ANSI_RESET;
  }
}
