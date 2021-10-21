package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.Scanner;
import jline.ConsoleReader;

public class Registration extends UserFields{
  /*
  This class: prints screen for when user clicks: 'To Register'
  and creates a new user account for them
  - a validation of email/password is done

  This class registers a new user to become a customer by creating a new account for them.

  --> looks at file user1.csv (say e.g. those people who made an account but haven't booked yet so no card details stored?)

  --> user.csv file (contains card details, remembers those who have booked tickets before - details saved in system)
  */
  private File userCsvFile;

  public Registration(){
    this.userCsvFile = new File("app/src/main/datasets/user1.csv");
  }

  public File getUserFile(){
    return this.userCsvFile;
  }

  public void setUserFile(File name){
    this.userCsvFile = name;
  }

  public User retrieveUserInputDetails() throws IOException { //return a USER object?
    this.printWelcome();
    Scanner scan = new Scanner(System.in);
    User currentUser = null;

    // System.out.println("PRESS Y TO CONTINUE REGISTERING"+
    // " OR PRESS N TO CANCEL AND GO BACK TO HOME PAGE~");
    // System.out.printf("Enter Y/N: ");
    System.out.println("1. ENTER Y TO CONTINUE REGISTERING\n"+
    "2. ENTER N TO CANCEL AND GO BACK TO HOME PAGE\n" +
    "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~");
    System.out.printf("\nEnter option: ");

    while (true){
      String option = scan.nextLine();
      if (option.toUpperCase().startsWith("N") == true){
        System.out.println("\n*******************************************************");
        System.out.println("REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..");
        System.out.println("*******************************************************");
        //call function from defaultScreen?
        // return;
        break;
      } else if (option.toUpperCase().startsWith("Y")) {
        // validate user details after retrieving input!!!
        System.out.println();
        String email = null;
        String password = null;

        boolean returnResult = false;
        boolean returnResult2 = false;
      
        while (true) { 
          System.out.printf("Please enter your email: "); //[re-enter]
          email = scan.nextLine();
          int result = this.checkIfUserExists(email);
          if (result == -1){
            System.out.println("Email is taken already/exists in system. Please enter another.");
          } else {
            boolean isValidEmail = this.validateUser(email);
            if (isValidEmail == true){
              returnResult = true;
              break;
            } 
          }
          System.out.println();
        }
        ConsoleReader consoleReader = new ConsoleReader();
        while (true){
          
          System.out.printf("\nPlease enter your password: ");
          // password = scan.nextLine();
          password = new jline.ConsoleReader().readLine(new Character('*'));
          boolean isValidPwd = this.isValidPassword(password);
          if (isValidPwd == true){
            returnResult2 = true;
            break;
          } //else, continue to enter a valid pwd
        }

        //user doesn't exist in system and creates a new acc
        if (returnResult == true && returnResult2 == true) {
          currentUser = this.createAccount(email, password); //if 51-52 is satisfied    
          String resultOption = this.nextOption();
          if (resultOption == null){
            System.out.println("\nINVALID OPTION SELECTED~");
          }
          // System.out.printf("\nUSER PREFERENCE: [%s]\n", resultOption);
          break;
        //else: keep entering a new password
        } 
      } else if (option.toUpperCase().startsWith("M")) {
        //redirect to login page!
        Login login = new Login();
        login.retrieveUserInputDetails();
        break; // or return to default page

      } else { //user input not y/n
        System.out.printf("\nInvalid input provided, please enter option again: ", option);
        // System.out.println("*******************************************************");
        // System.out.println("REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..");
        // System.out.println("*******************************************************");
        // return;
      }
      System.out.println();
    }
    return currentUser;
  }

  //compare against existing emails in database to see if email for registering is taken already or not
  public int checkIfUserExists(String userEmail){
    int userID = 1;
    String email = null;
    String password = null;
    int result = 1;

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
        if(userEmail.equals(email)){
          result = -1;
          break;
        } 
      }
    } catch (FileNotFoundException e) {
      System.out.printf("FILE NOT FOUND ERROR: %s FILE NOT FOUND!", this.userCsvFile);
    }
    return result;
  }

  public void printWelcome(){
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the registration page :)            ");
    // System.out.println("       Not a member with us yet? Sign up now FOR FREE!       ");
    System.out.println("                   Sign up now FOR FREE!                  ");

    System.out.println("*******************************************************\n");
  }
  
  // public void printStars(int starsCount){
  //   for (int i=0; i < stars; i++){
  //     System.out.printf("*");
  //   }
  //   System.out.println();
  // }
  // public void printMessages(String message, int lineNUmbers, int stars){
  //   System.out.println("\n*******************************************************");
  //   this.printStars();
  //   System.out.println("            Welcome to the registration page :)            ");
  //   System.out.println("       Not a member with us yet? Sign up now FOR FREE!       ");
  //   this.printStars();
  // }

  public int writeUserDetailsToFile(String email, String password){
    int id = -1;
    try {
      BufferedReader myReader = new BufferedReader(new FileReader(this.userCsvFile));
      FileWriter myWriter = new FileWriter(this.userCsvFile, true); //for appending to existing file
      
      String currentLine = "";
      String lastLine = "";
      //extract last number ID from row, then add 1.
      while ((currentLine = myReader.readLine()) != null){
        lastLine = currentLine;
      }
      myReader.close();
      try{
        id = Integer.parseInt(lastLine.split(",")[0]);
        myWriter.write("\n"+String.valueOf(id+1)+","+email+","+password);
        id+=1;

      } catch(NumberFormatException e){
        e.printStackTrace();
      }
      myWriter.close();
    } catch (FileNotFoundException e){
      System.out.printf("FILE NOT FOUND ERROR: %s FILE NOT FOUND!", this.userCsvFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return id;
  }

  //after all validations are done, create a new user obj that is a customer account + save/write details to user.csv
  public User createAccount(String email, String password){
    int ID = this.writeUserDetailsToFile(email, password);
    return new User(ID, email, password);  //creates a new user object
  }

  //remove option of saving details...
  // edit: consider representing options with colour in terminal?
  public String nextOption(){
    System.out.printf("\nPlease select from the following: \n");
    System.out.println("1. CONTINUE LOGGING IN");
    System.out.println("2. CANCEL");
    Scanner scan = new Scanner(System.in);
    int result = scan.nextInt();

    if (result == 1){
      System.out.println("*****************************************");
      System.out.println("       THANK YOU FOR SIGNING IN :)       ");
      System.out.println("*****************************************");
      System.out.printf("\nPlease select from the following: \n");
      // System.out.println("1. TOUR BUTTON for navigating the page"); //probs not necesssary for text based interface
      // System.out.println("2. HELP BUTTON for contacting staff");
      
      //acts like what happens after you login successfully~
      System.out.println("\n1. SETTINGS BUTTON for updating your details"); //what amber is working on
      System.out.println("2. DEFAULT HOME PAGE for filtering movies");
      System.out.println("3. SIGN OUT BUTTON");
      String res = "CONTINUE";

      int option = -1;
      while (true){
        option = scan.nextInt();
        System.out.println("\n*******************************************************");
        if (option == 1){
          System.out.println("Directing you to SETTINGS page~ in 3..2..1..");
          break;
        } else if (option == 2){
          System.out.println("Directing you to DEFAULT HOME page~ in 3..2..1..");
          break;
        } else if (option == 3){
          System.out.println("SIGNING OUT~ See you next time!~");
          res = "CANCEL";
          break;
        } else {
          System.out.printf("OH NO, please enter a valid command");
        }
        System.out.println("\n*******************************************************");
      }
      System.out.println("*******************************************************");
      return res;

    } else if (result == 2){
      System.out.println("*******************************************************");
      System.out.println("REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..");
      System.out.println("*******************************************************");
      return "CANCEL"; //go back to home/default page
    } else {
      return null;
    }
  }
}