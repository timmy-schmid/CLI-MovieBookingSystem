package R18_G2_ASM2;

import java.io.IOException;

import java.util.Scanner;
import java.io.*;

public class Registration {
  /*
  This class: prints screen for when user clicks: 'To Register'
  and registers a new user to become a customer by creating a new account for them.
  
  - a validation of all user fields is done
  */

  private static String USER_FILE_NAME = "newUserDetails.csv";
  private MovieSystem mSystem;

  public Registration(MovieSystem mSystem) {
    this.mSystem = mSystem;
  }
  
  public static String getUserFile(){
    return USER_FILE_NAME;
  }

  public static void setUserFile(String name){
    USER_FILE_NAME = name;
  }

  public void run() {
    try {
      retrieveUserInputDetails();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void retrieveUserInputDetails() throws IOException {
    this.printWelcome();  
    System.out.println("1. ENTER Y TO CONTINUE REGISTERING\n"+
    "2. ENTER N TO CANCEL AND GO BACK\n" +
    "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~");
    System.out.printf("\nEnter option: ");

    Scanner scan = new Scanner(System.in);
    while (scan.hasNextLine()) {
      String option = scan.nextLine();
      if (option.toUpperCase().startsWith("N") == true){
        System.out.println("\n*******************************************************");
        System.out.println("REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..");
        System.out.println("*******************************************************");
        break;

      } else if (option.toUpperCase().startsWith("Y")) {
        // validate user details after retrieving input!!!
        System.out.println();
        String email = null;
        String nickname = null;
        String password = null;
        String phoneNumber = null;

        while (true) { 
          System.out.printf("Please enter a nickname: "); //[re-enter]
          nickname = scan.nextLine();

          System.out.printf("\nPlease enter your email: "); 
          email = scan.nextLine();
          if (User.validateUser(email) == true){
            System.out.printf("\nPlease enter your phone number: "); 
            phoneNumber = scan.nextLine();
            if (User.isValidPhoneNumber(phoneNumber) == true){
              //Validates if user exists and correct email + phone format.
              int exists = User.doesUserExistInCSV(USER_FILE_NAME, email, phoneNumber);

              if (exists == 1) {
                System.out.println("email is taken already/exists in system.");
              } else if (exists == 2) {
                System.out.println("phone number is taken already/exists in system.");
              } else if (exists == 0) {
                break;
              } else {
                System.out.printf("Critical Error: could not check if user exists in the system. Aborting.\n");  
                return;      
              }
            }
          }
          System.out.println("\nPlease re-enter your details again.\n");
        }

        while (true) {
          Console con = System.console();
          if (con != null) { //Password masking
            char[] pwd = con.readPassword("\nPlease enter your password: ");
            password = new String(pwd);
          } else { //No masking if console not avaliable
            System.out.print("\nPlease enter your password: ");
            if (scan.hasNextLine()) {
              password = scan.nextLine();
            }
          }
          //validate pwd
          if (User.isValidPassword(password)) {
            break;
          }
        }
        //user doesn't exist in system and creates a new acc
        mSystem.setUser(this.createCustomer(nickname, email, phoneNumber, password));
        HomeScreen home = new HomeScreen(mSystem);
        home.run();
        break;
        
      } else if (option.toUpperCase().startsWith("M")) {
        //redirect to login page!
        Login login = new Login(mSystem);
        login.retrieveUserInputDetails();
        break; // or return to default page

      } else { //user input not y/n
        System.out.printf("\nInvalid input provided, please enter option again: ", option);
      }
      System.out.println();
    }
  }
 
  public void printWelcome(){
    System.out.print("\033[H\033[2J"); // clears screen
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the registration page :)            ");
    System.out.println("                   Sign up now FOR FREE!                  ");

    System.out.println("*******************************************************\n");
  }
    
  public Customer createCustomer(String nickname, String email, String phoneNumber, String password){
    
    Customer newCustomer = null;

    if (nickname == null || email == null || phoneNumber == null || phoneNumber.equals("") || password == null || password .equals("")){
      return null;
    }
    try {
      newCustomer = new Customer(User.getLastUserIDFromCSV(USER_FILE_NAME), nickname, email, phoneNumber, password);
      newCustomer.writeNewUserToCSV(USER_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Unable to create new User: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println("Unable to create new User: " + e.getMessage());
    }
    return newCustomer;
  }

  public String nextOption(){
    System.out.printf("\nPlease select from the following: \n");
    System.out.println("1. CONTINUE LOGGING IN");
    System.out.println("2. CANCEL");
    Scanner scan = new Scanner(System.in);
    
    String result = scan.nextLine();

    if (result.equals("1")){
      System.out.println("*****************************************");
      System.out.println("       THANK YOU FOR SIGNING IN :)       ");
      System.out.println("*****************************************");
     
      System.out.println("PLEASE ENTER 1 TO GO TO DEFAULT HOME PAGE");
      String res = "CONTINUE";

      String option = null;
      while (true){
        option = scan.nextLine();
        System.out.println("\n*******************************************************");
        if (option.equals("1")){  
          System.out.println("Directing you to DEFAULT HOME page~ in 3..2..1..");
          break;
        }  else {
          System.out.printf("OH NO, please enter a valid command");
        }
        System.out.println("\n*******************************************************");
      }
      System.out.println("*******************************************************");
      return res;

    } else if (result.equals("2")){
      System.out.println("*******************************************************");
      System.out.println("REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..\nSEE YOU NEXT TIME! :)");
      System.out.println("*******************************************************");
      return "CANCEL"; //go back to home/default page
    } else {
      return null;
    }
  }
}