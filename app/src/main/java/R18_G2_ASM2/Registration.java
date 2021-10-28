package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.Scanner;
import java.io.*;

public class Registration extends UserFields {
  /*
  This class: prints screen for when user clicks: 'To Register'
  and creates a new user account for them
  - a validation of email/password is done

  This class registers a new user to become a customer by creating a new account for them.

  1. when a user first registers, they are prompted to enter their email, password + phone number
  */
  private File userCsvFile;
  private static String USER_FILE_NAME = "newUserDetails.csv";
  HomeScreen home;

  private String userCsvFile2; //writing to...

  public Registration(HomeScreen home) {
    this.home = home;

    this.userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
    this.userCsvFile2 = this.userCsvFile.getAbsolutePath(); //str version -Tim this throws a NULLPointerError for me    
  }
  
  public static String getUserFile(){
    return USER_FILE_NAME;
  }

  public static void setUserFile(String name){
    USER_FILE_NAME = name;
  }

  public void run() {
    try {
      retrieveUserInputDetails3();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //sprint 2 --> new after meeting update:
  public void retrieveUserInputDetails3() throws IOException { //return a USER object?
    this.printWelcome();
    User currentUser = null;
  
    System.out.println("1. ENTER Y TO CONTINUE REGISTERING\n"+
    "2. ENTER N TO CANCEL AND GO BACK\n" +
    "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~");
    System.out.printf("\nEnter option: ");
    Scanner scan = new Scanner(System.in);
    while (true){
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
        boolean returnResult = false;
        boolean returnResult2 = false;
      
        //TODO: validate phoneNumber is 10 digit num
        while (true) { 
          System.out.printf("Please enter a nickname: "); //[re-enter]
          nickname = scan.nextLine();

          System.out.printf("\nPlease enter your email: "); 
          email = scan.nextLine();

          System.out.printf("\nPlease enter your phone number: "); 
          phoneNumber = scan.nextLine();

          int result = this.checkIfUserExists3(email, phoneNumber);
          
          if (result == 1) { //all fields are valid
            boolean isValidEmail = this.validateUser(email);
            if (isValidEmail){
              returnResult = true;
              break;
            } 
          }

          
          // } else if (result == -1){ //entered value (that should be unique) already exists in db
          //   // System.out.println("The supplied details contain info that already exists in our system. Please re-enter again: ");
          // }
          System.out.println();
        }

        Scanner scan2 = new Scanner(System.in);
        while (true){
          Console con = System.console();
          if (con != null) {
            char[] pwd = con.readPassword("\nPlease enter your password: ");
            password = new String(pwd);
          } else {
            System.out.print("\nPlease enter your password: ");
            password = scan2.nextLine();
          }
          boolean isValidPwd = this.isValidPassword(password);
          if (isValidPwd) {
            returnResult2 = true;
            break;
          } //else, continue to enter a valid pwd
        }

        //user doesn't exist in system and creates a new acc
        if (returnResult == true && returnResult2 == true) {
          currentUser = this.createAccount3(nickname, email, phoneNumber, password);
        
          /*
          String resultOption = this.nextOption();
          if (resultOption == null){
            System.out.println("\nINVALID OPTION SELECTED~");
          }*/
          home.setUser(currentUser);
          home.run();
          break;
        //else: keep entering a new password
        } 
      } else if (option.toUpperCase().startsWith("M")) {
        //redirect to login page!
        Login login = new Login(home);
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

  //sprint 2 --> new version after meeting update: 
  public int checkIfUserExists3(String userEmail, String userPhoneNumber){
      int userID = 1;
      String email = null;
      String phoneNumber = null;
  
      int result = 1;
      String type = "";
  
      //check file follows right format...
      try {
        // File f = new File();
        Scanner myReader = new Scanner(this.userCsvFile);
        while (myReader.hasNextLine()) { //as long as you can keep reading the file, grab the details
          String line = myReader.nextLine();
          String[] detailsArray = line.split(",");
          try{
            userID = Integer.parseInt(detailsArray[0]);
          } catch(NumberFormatException e){
            e.printStackTrace();
            break;
          }
          email = detailsArray[2];
          if(userEmail.equals(email)){
            result = -1;
            type = "Email";
            break;
          }
          phoneNumber = detailsArray[3];
          if(userPhoneNumber.equals(phoneNumber)){
            result = -1;
            type = "Phone number";
            break;
          }
        }
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println(USER_FILE_NAME + " was not found.");
        return -2;
      }
      if (result == -1) {
        System.out.printf("%s is taken already/exists in system. Please re-enter your details.\n", type);
        return result;
      }
      return result;
    }  
    
  public User createAccount3(String nickname, String email, String phoneNumber, String password){
    if (email == null || email.equals("") || phoneNumber == null || phoneNumber.equals("") || password == null || password .equals("")){
      return null;
    }
    
    int ID = this.writeUserDetailsToFile3(nickname, email, phoneNumber, password);

    User returnUser = new User(ID, nickname, email, phoneNumber, password);  //creates a new user object
    return returnUser;
  }

  //sprint 2 new version
  public int writeUserDetailsToFile3(String nickname, String email, String phoneNumber, String password){
    int id = -1;
    try {
      System.out.printf("LINE 228 IN REGISTRATION: ABOUT TO WRITE TO FILE: [%s]\n", this.userCsvFile2);
      Scanner myReader = new Scanner(this.userCsvFile);
      String currentLine = "";
      String lastLine = "";
      
      //if file exists and theres data inside
      while (myReader.hasNextLine()){
        currentLine = myReader.nextLine();
        lastLine = currentLine;
      }
      myReader.close();
      //extract last number ID from row, then add 1.
      //(can't write to USER_FILE_NAME for some reason)
      FileWriter myWriter = new FileWriter(this.userCsvFile2, true); //for appending to existing file 

      try{
      // FileWriter myWriter = new FileWriter(USER_FILE_NAME, true); //for appending to existing file

        id = Integer.parseInt(lastLine.split(",")[0]);
        myWriter.write("\n"+String.valueOf(id+1)+","+nickname+","+email+","+phoneNumber+","+password+",F");
        System.out.printf("LINE 233 IN REGISTRATION: WRITING TO FILE: [%s]\n", "\n"+String.valueOf(id+1)+","+nickname+","+email+","+phoneNumber+","+password+",F");
        id+=1;

      } catch(NumberFormatException e){
        e.printStackTrace();
      }
      myWriter.close();
      // System.out.println("LINE 266---------------------------------");
    } catch (FileNotFoundException e){
      //if reading file doesn't exist, write to file path     
      System.out.printf("FILE NOT FOUND ERROR: %s!\n", USER_FILE_NAME);

      try {
        FileWriter myWriter = new FileWriter(this.userCsvFile2); //for appending to existing file
        myWriter.write("\n"+String.valueOf(1)+","+nickname+","+email+","+phoneNumber+","+password+",F");

        myWriter.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();

      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return id;
  }

  //remove option of saving details...
  // edit: consider representing options with colour in terminal?
  public String nextOption(){
    System.out.printf("\nPlease select from the following: \n");
    System.out.println("1. CONTINUE LOGGING IN");
    System.out.println("2. CANCEL");
    Scanner scan = new Scanner(System.in);
    
    // int result = scan.nextInt();
    String result = scan.nextLine();

    if (result.equals("1")){
      System.out.println("*****************************************");
      System.out.println("       THANK YOU FOR SIGNING IN :)       ");
      System.out.println("*****************************************");
     
      //acts like what happens after you login successfully~
      System.out.println("PLEASE ENTER 1 TO GO TO DEFAULT HOME PAGE");
      String res = "CONTINUE";

      // int option = -1;
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