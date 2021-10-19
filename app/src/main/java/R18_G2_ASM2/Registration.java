package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.Scanner;
import java.util.regex.Pattern;

import R18_G2_ASM2.Login.PasswordField;


public class Registration {
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
    this.userCsvFile = new File("src/main/datasets/user1.csv");
  }

  public File getUserFile(){
    return this.userCsvFile;
  }

  public void setUserFile(File name){
    this.userCsvFile = name;
  }

  public void retrieveUserInputDetails(){
    this.printWelcome();
    Scanner scan = new Scanner(System.in);

    System.out.println("PRESS Y TO CONTINUE REGISTERING"+
    " OR PRESS N TO CANCEL AND GO BACK TO HOME PAGE~");
    System.out.printf("Enter Y/N: ");
    String option = scan.nextLine();
    // System.out.println("OPTION LINE 39 -------------> " + option);
    if (option.toUpperCase().startsWith("N") == true){
      System.out.println("\n*******************************************************");
      System.out.println("REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..");
      System.out.println("*******************************************************");
      return;
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
      
      Login log = new Login();
      PasswordField pwdField =  log.new PasswordField();
      while (true){
        pwdField.readPassword("\nPlease enter your password: ");
        // System.out.printf("\nPlease enter your password: ");
        password = scan.nextLine();
        boolean isValidPwd = this.isValidPassword(password);
        if (isValidPwd == true){
          returnResult2 = true;
          break;
        } //else, continue to enter a valid pwd
      }

      //user doesn't exist in system and creates a new acc
      if (returnResult == true && returnResult2 == true) {
        this.createAccount(email, password); //if 51-52 is satisfied    
        String resultOption = this.nextOption();
        if (resultOption == null){
          System.out.println("\nINVALID OPTION SELECTED~");
        }
        System.out.printf("\nUSER PREFERENCE: [%s]\n", resultOption);
      //else: keep entering a new password
      } else { //user input not y/n
        System.out.printf("Invalid input [%s], please select Y/N\n", option);
        return;
      }
    }
  }

  //compare against existing emails in database
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
    System.out.println("       Not a member with us yet? Sign up now FOR FREE!       ");
    System.out.println("*******************************************************\n");

  }

  //alphanumeric only 
  public boolean validateUser(String email){ //or email
    //should contain: @ + .com
    // String emailRegex = "^.*\\w@(.*)\\.com$";
    String emailRegex = "^.*\\w@(gmail|hotmail|yahoo)\\.com$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (pattern.matcher(email).matches()){
      return true;
    } else {
      System.out.println("Your email did not satisfy acceptance criteria.");
      return false;
    }
  }
  //Valid Password password [at least > n characters, mixture of letters, numbers, min 10 chars, at least 1 capital]
  public boolean isValidPassword(String password){
    //now, use regex to ensure it contains a mixture of letters + numbers + symbols (--> optional?, allow whitespace or NAH?)    
    
    //\\w === [a-zA-Z_0-9]
    // String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).{10,}$";
    String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$";
    Pattern pattern = Pattern.compile(passwordRegex);
    if (pattern.matcher(password).matches()){
      return true;
    } else {
      System.out.println("Your password did not satisfy acceptance criteria.");
      return false;
    }
  }

  //An option to remember my login details for next time
  // public void saveDetailsForNextTime(){
  // }

  public int writeUserDetailsToFile(String email, String password){
    int id = -1;
    try {
      // Scanner myReader = new Scanner(this.userCsvFile);
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

  //after all validations are done, create a new customer/user obj account + save details to user.csv
  //--> Once an account is created the [new] details must be written in a file/db.
  public void createAccount(String email, String password){
    int ID = this.writeUserDetailsToFile(email, password);
    User newCustomer = new User(ID, email, password);  //creates a new user object
    //now write to file
  }

  public String nextOption(){
    System.out.printf("\nPlease select from the following: \n");
    System.out.println("1. CONTINUE LOGGING IN but don't save my details for next time");
    System.out.println("2. CONTINUE LOGGING IN and save my details for next time");
    System.out.println("3. CANCEL");
    Scanner scan = new Scanner(System.in);
    int result = scan.nextInt();

    if (result == 1 || result == 2){
      
      System.out.println("\n~~~~~~~~~~~THANK YOU FOR SIGNING IN :) ~~~~~~~~~~~~~~~");
      System.out.printf("\nPlease select from the following: \n");
      System.out.println("1. TOUR BUTTON for navigating the page"); //probs not necesssary for text based interface
      System.out.println("2. HELP BUTTON for contacting staff");
      System.out.println("3. DEFAULT HOME PAGE for filtering movies");

      return "CONTINUE";
      // Scanner scan2 = new Scanner(System.in);
      // int option = scan2.nextInt();
      // if (option == 3){ //go to default page??
      // }
      // return "CONTINUE and save my details for next time";
    } else if (result == 3){
      return "CANCEL"; //go back to home page
    } else {
      return null;
    }
  }
}