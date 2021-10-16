package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Registration {
  

  /*
  This class: prints screen for when user clicks: 'To Register'
  and creates a new user account for them
  - a validation of username/password is done

  This class registers a new user to become a customer by creating a new account for them.

  --> looks at file user1.csv (say e.g. those people who made an account but haven't booked yet so no card details stored?)

  --> user.csv file (contains card details, remembers those who have booked tickets before - details saved in system)
  */
  private File userCsvFile;

  public Registration(){
    this.userCsvFile = new File("src/main/datasets/user1.csv");
  }

  public void retrieveUserInputDetails(){
    this.printWelcome();

    Scanner scan = new Scanner(System.in);
    //validate user details after retrieving input!!!
    String email = null;
    String password = null;
    while (true) { 

      System.out.printf("Please enter your username/email: "); //[re-enter]
      email = scan.nextLine();
      System.out.printf("\nPlease enter your password: ");
      password = scan.nextLine();

      int result = this.checkIfUserExists(email, password);
      if (result == -1){
        System.out.println("Email already exists in system//already used. Please enter another.");
      } else if (result == -2){
        System.out.println("Password is already taken, please enter another one.");
      
      } else { //user doesn't exist in system and creates a new acc
        boolean isValidEmail = this.validateUser(email);
        boolean isValidPwd = this.isValidPassword(password);
        if (isValidPwd == true && isValidEmail == true){
          this.createAccount(email, password); //if 51-52 is satisfied    
          String result1 = this.nextOption();
          // if (result== null){}
          System.out.printf("USER PREFERENCE: [%s]\n", result1);
          break;
        } 
        //else: keep entering a new password
        System.out.println();
      }
    }
  }

  //compare against existing emails in database
  public int checkIfUserExists(String userEmail, String userPassword){
    int userID = 1;
    // String name = null;
    String email = null;
    // String phoneNumber = null;
    // int creditCardNum = -1;
    // String type = null;
    String password = null;
    int result = 1;

    try {
      Scanner myReader = new Scanner(userCsvFile);
      while (myReader.hasNextLine()) { //as long as you can keep reading the file, grab the details
        String line = myReader.nextLine();
        String[] detailsArray = line.split(",");
        //go through all column data in file
        try{
          userID = Integer.parseInt(detailsArray[0]);
        } catch(NumberFormatException e){
          e.printStackTrace();
          break;
        }
        email = detailsArray[1];
        // name = detailsArray[1];
        // email = detailsArray[2];
        if (userEmail == email){ //basically validateUserName()
          // System.out.println("Email already exists in system//already used.");
          result = -1;
          break;
        } 
        // phoneNumber = detailsArray[3];
        // try {  
        //   creditCardNum = Integer.parseInt(detailsArray[4]);
        // } catch(NumberFormatException e){
        //   e.printStackTrace();
        //   break;
        // }
        // type = detailsArray[5];
        // password = detailsArray[6];

        password = detailsArray[2];
        if (userPassword == password){ 
          // System.out.println("Password is already taken, please enter another one.");
          result = -2;
          break;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("LINE 103: USER1.CSV FILE NOT FOUND!!!");
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
    String usernameRegex = "^.*\\w@.*.com$";
    Pattern pattern = Pattern.compile(usernameRegex);
    if (pattern.matcher(email).matches()){
      return true;
    } else {
      System.out.println("Your email/username did not satisfy acceptance criteria.");
      return false;
    }
  }
  //Valid Password password [at least > n characters, mixture of letters, numbers, min 10 chars, at least 1 capital]
  public boolean isValidPassword(String password){
    // boolean found = false;
    // for(int i = 0;i < password.length(); i++){
    //   if (Character.isUpperCase(password.charAt(i))){
    //     found = true;
    //     break;
    //   }
    // }
    //now, use regex to ensure it contains a mixture of letters + numbers + symbols (--> optional?, allow whitespace or NAH?)    
    //  String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+-=]).{10,}$";
    
    //\\w === [a-zA-Z_0-9]
    String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).{10,}$";
    Pattern pattern = Pattern.compile(passwordRegex);
    if (pattern.matcher(password).matches()){
      return true;
    } else {
      System.out.println("Your password did not satisfy acceptance criteria.");
      return false;
    }
  }

  //An option to remember my login details for next time [gui maybe]
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
      System.out.println("INE 158: USER1.CSV FILE NOT FOUND!!!");
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
      
      System.out.println("~~~~~~~~~~~THANK YOU FOR SIGNING IN~~~~~~~~~~~~~~~");
      System.out.printf("\nPlease select from the following: \n");
      System.out.println("1. TOUR BUTTON for navigating the page");
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