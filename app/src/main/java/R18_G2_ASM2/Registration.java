package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.Scanner;
import java.io.*;

public class Registration extends UserFields{
  /*
  This class: prints screen for when user clicks: 'To Register'
  and creates a new user account for them
  - a validation of email/password is done

  This class registers a new user to become a customer by creating a new account for them.

  --> looks at file user1.csv (say e.g. those people who made an account but haven't booked yet so no card details stored?)

  --> user.csv file (contains card details, remembers those who have booked tickets before - details saved in system)

  VERSION update: removed additional options after signing in and only directed to home page.

  1. when a user first registers, they are prompted to enter their email, password + card number + phone number
  */
  private String userCsvFile;

  public Registration(){
    // this.userCsvFile = "app/src/main/datasets/user1.csv";
    // this.userCsvFile = "app/src/main/datasets/newUserDetails.csv";
    this.userCsvFile = "/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT-2-NEW/R18_G2_ASM2/app/src/main/datasets/newUserDetails.csv";
  }

  public String getUserFile(){
    return this.userCsvFile;
  }

  public void setUserFile(String name){
    this.userCsvFile = name;
  }

  // --> SPRINT 1 version
  public User retrieveUserInputDetails() throws IOException { //return a USER object?
    this.printWelcome();
    Scanner scan = new Scanner(System.in);
    User currentUser = null;
;
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
          } else if (result == -2){
            System.out.printf("FILE NOT FOUND ERROR: %s FILE NOT FOUND!", this.userCsvFile);
            break;
          }
          else {
            boolean isValidEmail = this.validateUser(email);
            if (isValidEmail == true){
              returnResult = true;
              break;
            } 
          }
          System.out.println();
        }
        Scanner scan2 = new Scanner(System.in);
        while (true){
          Console con = System.console();
          if (con != null) {
            char[] pwd = con.readPassword("\nPlease enter your password: ");
            password = new String(pwd);
          }
          boolean isValidPwd = this.isValidPassword(password);
          if (isValidPwd == true){
            returnResult2 = true;
            break;
          } //else, continue to enter a valid pwd
        }

        //user doesn't exist in system and creates a new acc
        if (returnResult == true && returnResult2 == true) {
          currentUser = this.createAccount(email, password); //if 51-52 
        
          String resultOption = this.nextOption();
          if (resultOption == null){
            System.out.println("\nINVALID OPTION SELECTED~");
          }
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
      }
      System.out.println();
    }
    return currentUser;
  }

  // --> SPRINT 2 version: add extra questions for user to fill out

  // ask: do you have gift card?
  // if yes: enter your number
  // if no: default as 16 zeroes?
  public User retrieveUserInputDetails2() throws IOException { //return a USER object?
    this.printWelcome();
    Scanner scan = new Scanner(System.in);
    User currentUser = null;
;
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
        break;

      } else if (option.toUpperCase().startsWith("Y")) {
        // validate user details after retrieving input!!!
        System.out.println();
        String email = null;
        String nickname = null;
        String password = null;

        String phoneNumber = null;
        String cardNumber = null;
        String giftCardNumber = null; //replace w DEFAULT = '0000000000000000' ? if user doesn't have giftcard num

        boolean returnResult = false;
        boolean returnResult2 = false;
      
        while (true) { 
          System.out.printf("Please enter a nickname: "); //[re-enter]
          nickname = scan.nextLine();

          System.out.printf("Please enter your email: "); 
          email = scan.nextLine();

          System.out.printf("Please enter your phone number: "); 
          phoneNumber = scan.nextLine();

          System.out.printf("Please enter your card number: "); 
          cardNumber = scan.nextLine();

          System.out.printf("Please enter your gift card number: "); 
          giftCardNumber= scan.nextLine();

          int result = this.checkIfUserExists2(email, phoneNumber, cardNumber, giftCardNumber);
          
          if (result == 1) { //all fields are valid
            boolean isValidEmail = this.validateUser(email);
            if (isValidEmail){
              returnResult = true;
              break;
            } 
          } else if (result == -1){ //entered value (that should be unique) already exists in db

          }
          System.out.println();
        }
        Scanner scan2 = new Scanner(System.in);
        while (true){
          Console con = System.console();
          if (con != null) {
            char[] pwd = con.readPassword("\nPlease enter your password: ");
            password = new String(pwd);
          }
          boolean isValidPwd = this.isValidPassword(password);
          if (isValidPwd) {
            returnResult2 = true;
            break;
          } //else, continue to enter a valid pwd
        }

        //user doesn't exist in system and creates a new acc
        if (returnResult == true && returnResult2 == true) {
          // currentUser = this.createAccount(email, password);
          currentUser = this.createAccount2(nickname, email, phoneNumber, cardNumber, giftCardNumber, password);
        
          String resultOption = this.nextOption();
          if (resultOption == null){
            System.out.println("\nINVALID OPTION SELECTED~");
          }
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

    //check file follows right format...
    try {
      File f = new File(this.userCsvFile);
      Scanner myReader = new Scanner(f);
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
      myReader.close();
    } catch (FileNotFoundException e) {
      // System.exit(0);
      return -2;
    }
    return result;
  }


  //return: result: int
  public int checkIfUserExists2(String userEmail, String userPhoneNumber, String userCardNumber, String userGiftCardNumber){
    int userID = 1;
    String email = null;
    String phoneNumber = null;
    String cardNumber = null;
    String giftCardNumber = null;

    int result = 1;
    String message = "";
    String type = "";

    //check file follows right format...
    try {
      File f = new File(this.userCsvFile);
      Scanner myReader = new Scanner(f);
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
        //does card number have to be unique??

        // cardNumber = detailsArray[3];
        // if(userCardNumber.equals(cardNumber)){
        //   result = -1;
        //   type = "card number";
        //   break;
        // }
        giftCardNumber= detailsArray[6];
        if(userGiftCardNumber.equals(giftCardNumber)){
          result = -1;
          type = "Gift card number";
          break;
        }
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.printf("FILE NOT FOUND ERROR: %s FILE NOT FOUND!", this.userCsvFile);
      return -2;
    }
    if (result == -1) {
      System.out.printf("%s is taken already/exists in system. Please re-enter your details.\n", type);
      return result;
    }
    return result;
  }
  public void printWelcome(){
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the registration page :)            ");
    System.out.println("                   Sign up now FOR FREE!                  ");

    System.out.println("*******************************************************\n");
  }

  //sprint 1 version
  public int writeUserDetailsToFile(String email, String password){
    int id = -1;
    try {
      BufferedReader myReader = new BufferedReader(new FileReader(new File(this.userCsvFile)));
      
      String currentLine = "";
      String lastLine = "";
      
      //if file exists and theres data inside
      int line = 0;
      while ((currentLine = myReader.readLine()) != null){
        if (currentLine.trim().length() > 0) {
          lastLine = currentLine;
          line+=1;
        }
      }

      myReader.close();
      //extract last number ID from row, then add 1.
      FileWriter myWriter = new FileWriter(new File(this.userCsvFile), true); //for appending to existing file
      try{
        id = Integer.parseInt(lastLine.split(",")[0]);
        
        myWriter.write("\n"+String.valueOf(id+1)+","+email+","+password);
        id+=1;

      } catch(NumberFormatException e){
        e.printStackTrace();
      }
      // }
      myWriter.close();
    } catch (FileNotFoundException e){
      //if reading file doesn't exist, write to file path
      // System.out.printf("FILE NOT FOUND ERROR: %s FILE NOT FOUND!", this.userCsvFile);
     
      try {
        FileWriter myWriter = new FileWriter(new File(this.userCsvFile)); //for appending to existing file
        myWriter.write(String.valueOf(1)+","+email+","+password);
        myWriter.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return id;
  }

  //after all validations are done, create a new user obj that is a customer account + save/write details to user.csv
  public User createAccount(String email, String password){
    if (email == null || password == null || email.equals("") || password .equals("")){
      return null;
    }
    int ID = this.writeUserDetailsToFile(email, password);
    return new User(ID, email, password);  //creates a new user object
  }

   //VERSION 2: add more details to csv file
   public User createAccount2(String nickname, String email, String phoneNumber, String cardNumber, String giftCardNumber, String password){
    if (email == null || password == null || email.equals("") || phoneNumber == null || phoneNumber.equals("") || cardNumber == null || cardNumber.equals("") || giftCardNumber == null || giftCardNumber.equals("")
    || password == null || password .equals("")){
      return null;
    }
    int ID = this.writeUserDetailsToFile2(nickname, email, phoneNumber, cardNumber, giftCardNumber, password);
    return new User(ID, nickname, email, phoneNumber, password, new Card(nickname, cardNumber), new GiftCard(giftCardNumber, true));  //creates a new user object
  }

  //sprint 2 version
  public int writeUserDetailsToFile2(String nickname, String email, String phoneNumber, String cardNumber, String giftCardNumber, String password){
    int id = -1;
    try {
      BufferedReader myReader = new BufferedReader(new FileReader(new File(this.userCsvFile)));
      
      String currentLine = "";
      String lastLine = "";
      
      //if file exists and theres data inside
      int line = 0;
      while ((currentLine = myReader.readLine()) != null){
        if (currentLine.trim().length() > 0) {
          lastLine = currentLine;
          line+=1;
        }
      }

      myReader.close();
      //extract last number ID from row, then add 1.
      FileWriter myWriter = new FileWriter(new File(this.userCsvFile), true); //for appending to existing file
      try{
        id = Integer.parseInt(lastLine.split(",")[0]);
        
        myWriter.write("\n"+String.valueOf(id+1)+","+nickname+","+email+","+phoneNumber+","+cardNumber+","+password+","+giftCardNumber+","+"1,0");
        id+=1;

      } catch(NumberFormatException e){
        e.printStackTrace();
      }
      // }
      myWriter.close();
    } catch (FileNotFoundException e){
      //if reading file doesn't exist, write to file path     
      try {
        FileWriter myWriter = new FileWriter(new File(this.userCsvFile)); //for appending to existing file
        myWriter.write("\n"+String.valueOf(1)+","+nickname+","+email+","+phoneNumber+","+cardNumber+","+password+","+giftCardNumber+","+"1,0");
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