package R18_G2_ASM2;
import java.io.*;

import java.util.Scanner;
public class Login {
  /*
  This class: prints screen for when user clicks: 'Log in' and direct them to type their username
  and password
  */
  private File userCsvFile;
  private static String USER_FILE_NAME = "newUserDetails.csv";
  private MovieSystem mSystem;
  private User user;

  public Login(MovieSystem mSystem) {

    try {
      userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
    } catch (FileNotFoundException e) {
      System.out.println("Unable to access login db: " + e.getMessage());
    }
  
    if (userCsvFile != null) {
      //System.out.println(userCsvFile.getPath());
    }
    this.mSystem = mSystem;
  }

  public static void setUserFile(String name){
    USER_FILE_NAME = name;
  }

  public void run() {

    try {
      retrieveUserInputDetails();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void retrieveUserInputDetails() throws IOException{
  // public void retrieveUserInputDetails() throws IOException{
    this.printScreen();

    //validate user details after retrieving input!!!
    String username = null;
    String password = null;
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.printf("Please enter your username: ");
//      username = consoleReader.readLine();
      username = sc.nextLine();
      Console con = System.console();
      if (con != null) {
        char[] pwd = con.readPassword("Please enter your password: ");
        password = new String(pwd);
        //System.out.printf("PASSWORD LINE 100: [%s]\n", password);
      } else {
        System.out.printf("Please enter your password: ");
        password = sc.nextLine();
      }

      int result = this.checkIfUserExists2(username, password);

      if (result == 1){ //Direct to appropriate screen
        mSystem.setUser(this.user);
        if (user.getUserType() == UserType.CUSTOMER) {
          HomeScreen home = new HomeScreen(mSystem);
          home.run();
        } else {
          StaffScreen staff = new StaffScreen(mSystem);
          staff.run();
        }
      } else if (result == -1) {
        int temp = 0;
        while (temp == 0) {
          String textinput = this.nextOption();
          if (textinput.equals("1")) {
            temp = 1;
          } else if (textinput.equals("2")) {
            temp = 2;
          } else {
            System.out.println("Invalid input, please choose agian!");
          }
        }
        if (temp == 1) {
          continue;
        } else if (temp == 2) { //Back to previous page
          break;
        }
      }
    }
  }

  //sprint 2 version: edit (added more fields to csv file)
  //create a user object + return it??
  public int checkIfUserExists2(String userEmail, String userPassword){
    int userID = 1;
    String email = null;
    String realPassword = null;
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
        email = detailsArray[2];
        realPassword = detailsArray[4];
        if (userEmail.equals(email)){
          if (realPassword.equals(userPassword)) {
            result = 1;       
            if (detailsArray[6].equals("CUSTOMER")) {
              Customer cust  = new Customer(Integer.parseInt(detailsArray[0]), detailsArray[1], detailsArray[2], detailsArray[3],detailsArray[4]);
              if (detailsArray[5].equals("T")){
                cust.setAutoFillStatus(true);
              }
              this.user = cust;
            } else if (detailsArray[6].equals("MANAGER")) {
              this.user = new Manager(Integer.parseInt(detailsArray[0]), detailsArray[1], detailsArray[2], detailsArray[3],detailsArray[4]);             
            } else if (detailsArray[6].equals("STAFF")) {
              this.user = new Staff(Integer.parseInt(detailsArray[0]), detailsArray[1], detailsArray[2], detailsArray[3],detailsArray[4]);             
            } else {
              result = -1;
            }
            break;
          } else {
            result = -1;
          }
        } else {
          result = -1;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("LINE 163: "+USER_FILE_NAME+"  FILE NOT FOUND!!!");
    }
    return result;
  }

  public void printScreen(){
    System.out.println("\033[H\033[2J\n*******************************************************");
    System.out.println("            Welcome to the log in page :)            ");
    System.out.println("*******************************************************");
  }


  public String nextOption() throws IOException{
    System.out.printf("\nInvalid username or password, please select from the following:\n");
    System.out.println("1. CONTINUE LOGGING IN");
    System.out.println("2. CANCEL");

    String textinput = null;
    Scanner scan = new Scanner(System.in);
    textinput = scan.nextLine();
    return textinput;
  }
}