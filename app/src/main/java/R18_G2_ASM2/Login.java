package R18_G2_ASM2;
import java.io.*;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Login {
  /*
  This class: prints screen for when user clicks: 'Log in' and direct them to type their username
  and password
  */
  private File userCsvFile;

  public Login(){
    this.userCsvFile = new File("src/main/datasets/user1.csv");
  }

  public void retrieveUserInputDetails(){
    this.printScreen();
    Scanner scan = new Scanner(System.in);
    //validate user details after retrieving input!!!
    String username = null;
    String password = null;
    while (true) {
      System.out.printf("Please enter your username: ");
      username = scan.nextLine();
      password = PasswordField.readPassword("Please enter your password: ");

      int result = this.checkIfUserExists(username, password);
      if (result == 1){
        System.out.println("Welcome back " + username + "!");
        //Direct to next page!!!
      } else if (result == -1){
        System.out.println("Invalid username or password, please try again!");
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
        if (userEmail == email){
          if (real_password == userPassword) {
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
    System.out.println("\n---------------------------------------------------------");
    System.out.println("               Welcome to the log in page :)            ");
    System.out.println("---------------------------------------------------------\n");
  }


  public void nextOption(){
    System.out.printf("\nPlease select from the following: \n");
    System.out.println("1. CONTINUE LOGGING IN");
    System.out.println("2. FORGOT MY LOGIN DETAILS");
    System.out.println("3. CANCEL");
    Scanner scan = new Scanner(System.in);
    int result = scan.nextInt();
  }
}

class PasswordField {
  /**
   *@param prompt The prompt to display to the user
   *@return The password as entered by the user
   */
  public static String readPassword (String prompt) {
    EraserThread et = new EraserThread(prompt);
    Thread mask = new Thread(et);
    mask.start();
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String password = "";
    try {
      password = in.readLine();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    // stop masking
    et.stopMasking();
    // return the password entered by the user
    return password;
  }
}


class EraserThread implements Runnable {
  private boolean stop;
  /**
   *@param The prompt displayed to the user
   */
  public EraserThread(String prompt) {
    System.out.print(prompt);
  }
  /**
   * Begin masking...display asterisks (*)
   */
  public void run () {
    stop = true;
    while (stop) {
      System.out.print("\010*");
      try {
        Thread.currentThread().sleep(1);
      } catch(InterruptedException ie) {
        ie.printStackTrace();
      }
    }
  }
  /**
   * Instruct the thread to stop masking
   */
  public void stopMasking() {
    this.stop = false;
  }
}