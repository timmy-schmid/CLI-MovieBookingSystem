package R18_G2_ASM2;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import jline.ConsoleReader;

public class Login {
  /*
  This class: prints screen for when user clicks: 'Log in' and direct them to type their username
  and password
  */
  private File userCsvFile;

  public Login(){
    this.userCsvFile = new File("src/main/datasets/user1.csv");
//    this.userCsvFile = new File("/Users/robingo/Desktop/usyd yr 2 s2/soft2412/asm2/R18_G2_ASM2/app/src/main/datasets/user1.csv");
  }

  public void setUserFile(File name){
    this.userCsvFile = name;
  }

  public void retrieveUserInputDetails() throws IOException{
    this.printScreen();
    Scanner scan = new Scanner(System.in);
//    ConsoleReader consoleReader = new ConsoleReader();
    //validate user details after retrieving input!!!
    String username = null;
    String password = null;
    while (true) {
      System.out.printf("Please enter your username: ");
//      username = consoleReader.readLine();
      username = scan.nextLine();
//      System.out.printf("Please enter your password: ");
//      password = new jline.ConsoleReader().readLine(new Character('*'));
      Console con = System.console();
      if (con != null) {
        char[] pwd = con.readPassword("Please enter your password: ");
        password = new String(pwd);
      }
      int result = this.checkIfUserExists(username, password);
      if (result == 1){
        System.out.println("Welcome back " + username + "!");
        // break;
        return;
        //Direct to next page!!!
      } else if (result == -1){
        int temp = 0;
        while (temp == 0) {
          String textinput = this.nextOption();
          if (textinput.equals("1")) {
            temp = 1;
          } else if (textinput.equals("2")) {
            System.out
                .println("Please answer the following questions: --To be add in the user.csv");
            temp = 2;
          } else if (textinput.equals("3")) {
            System.out.println("Back to default page--Tim part default screen");
            temp = 2;
          } else {
            System.out.println("Invalid input, please choose agian!");
          }
        }
        if (temp == 1) {
          continue;
        } else if (temp == 2) {
          break;
        }
      }
    }
  }

  public int checkIfUserExists(String userEmail, String userPassword){
    int userID = 1;
    String email = null;
    String real_password = null;
    int result = 0;

    try {
      Scanner myReader = new Scanner(userCsvFile);
      while (myReader.hasNextLine()) { //as long as you can keep reading the file, grab the details
        String line = myReader.nextLine();
        String[] detailsArray = line.split(",");
        try{
          userID = Integer.parseInt(detailsArray[0]);
        } catch(NumberFormatException e){
          e.printStackTrace();
          break;
        }
        email = detailsArray[1];
        real_password = detailsArray[2];
        if (userEmail.equals(email)){
          if (real_password.equals(userPassword)) {
            result = 1;
            break;
          }
          else {
            result = -1;
          }
        }
        else {
          result = -1;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("LINE 103: USER1.CSV FILE NOT FOUND!!!");
    }
    return result;
  }

  public void printScreen(){
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the log in page :)            ");
    System.out.println("*******************************************************");
  }


  public String nextOption() throws IOException{
    System.out.printf("\nInvalid username or password, please select from the following:\n");
    System.out.println("1. CONTINUE LOGGING IN");
    System.out.println("2. CANCEL");
//    ConsoleReader consoleReader = new ConsoleReader();
    String textinput = null;
//    textinput = consoleReader.readLine();
    Scanner scan = new Scanner(System.in);
    textinput = scan.nextLine();
    return textinput;
  }
}
//class PasswordField {
//  /**
//   *@param prompt The prompt to display to the user
//   *@return The password as entered by the user
//   */
//  public static String readPassword (String prompt) {
//    EraserThread et = new EraserThread(prompt);
//    Thread mask = new Thread(et);
//    mask.start();
//    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//    String password = "";
//    try {
//      password = in.readLine();
//    } catch (IOException ioe) {
//      ioe.printStackTrace();
//    }
//    // stop masking
//    et.stopMasking();
//    // return the password entered by the user
//    return password;
//  }
//}
//
//
//class EraserThread implements Runnable {
//  private boolean stop;
//  /**
//   *@param The prompt displayed to the user
//   */
//  public EraserThread(String prompt) {
//    System.out.print(prompt);
//  }
//  /**
//   * Begin masking...display asterisks (*)
//   */
//  public void run () {
//    stop = true;
//    while (stop) {
//      System.out.print("\010*");
//      try {
//        Thread.currentThread().sleep(1);
//      } catch(InterruptedException ie) {
//        ie.printStackTrace();
//      }
//    }
//  }
//  /**
//   * Instruct the thread to stop masking
//   */
//  public void stopMasking() {
//    this.stop = false;
//  }
//}
//
//class PasswordMasking {
//  public static String PasswordHide() {
//    Console con = System.console();
//    if (con != null) {
//      char[] pwd = con.readPassword("Please enter your password: ");
//      return new String(pwd);
//    }
//    return "Couldn't get Console instance, maybe you're running this from within an IDE?";
//  }
//}
//
//class PasswordMasking {
//  public static String PasswordHide() throws Exception{
//    ConsoleReader console = new ConsoleReader();
//    String password = console.readLine(new Character('*'));
//    return password;
//  }
//}
