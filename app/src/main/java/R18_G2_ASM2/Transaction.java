package R18_G2_ASM2;

import java.util.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

/*
CSV FORMAT: <userID, nickname, email, phoneNumber, creditCardNumber, password, giftCard, reedemableStatus, autoFillStatus>

read newUserDetails.csv
--> 1 = true = T, 0 = false = F
*/

public class Transaction {
  private Card userCreditcard;
  private GiftCard userGiftCard;
  private User customer;

  private File tempFile;
  private String userCsvFile;
  public Transaction(User customer){
    this.customer = customer;
    // this.userCsvFile = "app/src/main/datasets/newUserDetails.csv";
    this.userCsvFile = "/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT-2-NEW/R18_G2_ASM2/app/src/main/datasets/newUserDetails.csv";
    this.tempFile = new File("/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT-2-NEW/R18_G2_ASM2/app/src/main/datasets/cardTemp.csv");
  }

  public User getCustomer(){
    return this.customer;
  }

  public void printScreen(){ //hi-fi ui of user transaction screen
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the payment page :)            ");
    System.out.println("           Please enter your details below!                  ");
    System.out.println("*******************************************************\n");

  }

  //run()
  // get total amount of all tickets from booking ticket/list of tickets? to compare against gift card num/credit card
  public void proceedPayment(){ //card details fill out
    this.printScreen();
    this.printOptions();

  }

  //if user decides to select 1. <Remember my card details> 2. <Dont remember card details>. 
  public String checkAutoFillOption(String option){
    String autoFill = "no";
    if (option.toLowerCase().equals("yes") ||
      option.toLowerCase().equals("y")){
      autoFill = "yes";
    } else if (option.toLowerCase().equals("no") ||
    option.toLowerCase().equals("n")){
      autoFill = "no";
    } else { //e.g. if option is null 
      // System.out.println("Please enter a valid command: ");
      autoFill = "invalid";
    }
    return autoFill;
  }

  public void printOptions(){ //boolean userAutoFillStatus){ //print prompt to save if user hasn't changed status yet else don't show
    System.out.println("Please enter an option below~ ");
    System.out.println("\nENTER 1 TO PAY WITH CARD~\n"+ "ENTER 2 TO CANCEL TRANSACTION~"); //or TO CANCEL TRANSACTION, PRESS [C]
    System.out.printf("\nEnter option: ");

    Scanner scan = new Scanner(System.in);
    String option = null;
    boolean continueToPay = false;
    while (true){
      option = scan.nextLine();
      System.out.println("\n*******************************************************");
      
      if (option.equals("1")){
        System.out.println("PROCEEDING TO PAY WITH CARD~ in 3..2..1..");
        continueToPay = true;
        break;
      } else if (option.equals("2")){ //or go back to booking page??
        System.out.println("     CANCELLING TRANSACTION + REDIRECTING YOU BACK\n               TO HOME PAGE~ in 3..2..1..");
        break;
      } else { //invalid command entered
        System.out.printf("OH NO, please enter a valid command");
      }
      System.out.println("\n*******************************************************");
    }
    System.out.println("*******************************************************\n");

    if (continueToPay == true){

      // first check if auto fill (if true: print card details below, then ask to continue or smthin)
      // if autofill option of user isn't true, prompt user to enter card details
      int res = this.askAutoFillCardDetails();
      // if(this.getCustomer().getAutoFillStatus() == true){
      //   //dont ask to fill again
      //   res = 1;
      // }
      
      if (res == 1){ //user wants to print details (autofill)
        System.out.println("printing user's card details below (saved before)!");
        System.out.printf("Name: %s\n", this.getCustomer().getCreditCard().getName());
        System.out.printf("Card number provided: %s\n", this.getCustomer().getCreditCard().getCardNumber());
        System.out.println("\nCOMPLETE TRANSACTION (Y/N): ");
      
      } else if (res == 2){ //user wants to enter their gift card num
        //or save automatically bc entered when registering?
        System.out.printf("Please enter your gift card number: ");
        String num = scan.nextLine();
        if (num.equals(this.getCustomer().getGiftCard().getCardNumber())){
          //TODO: check if gift card status is redeemable or not
          //if it is, but not enough money ask to pay with credit card remaining
          // if it isn't: go back to pay with card
          System.out.println("\nCOMPLETE TRANSACTION (Y/N): ");
        } else {
          System.out.println("The gift card number you have provided does not match the details provided in our system. Please try again");
        }


      } else if (res == -1){ //user wants to pay w credit card but no auto fill, prompt for input 

        //TODO: validate credit card number is same as whats provided when registering

        System.out.printf("Please enter your card number: ");
        String cNum = scan.nextLine();
        // System.out.printf("Please enter your csv number: ");
        // String csvNum = scan.nextLine();
        System.out.println("COMPLETE TRANSACTION (Y/N): ");
      }
    }
    System.out.println("*******************************************************");
  }
  
  //return: returnNum: int (either continue to ask to enter details or print details if auto filled already)
  public int askAutoFillCardDetails(){
    Scanner scan = new Scanner(System.in);
    String option = null;
    String cardOption = null;
    String result = null;

    int returnNum= -1;

    String start = "W";
    while (true){
      System.out.printf("%shich card would you like to use? (credit/gift): ", start);
      //since gift card --> 1 time use, only ask below msg if credit card selected?
      cardOption = scan.nextLine();

      if (cardOption.equals("credit") && this.getCustomer().getAutoFillStatus() == true) {      
        returnNum = 1;
        break;

      } else if (cardOption.equals("credit")){
        System.out.printf("\nWould you like to save your card details for next time? (Y/N): ");
      
        option = scan.nextLine();      
        result = this.checkAutoFillOption(option);

        if (result.equals("yes")){
          //search for user in newUserDetails.csv file, modify default false to true
          System.out.println("ABOUT TO UPDATE USER DETAILS IN FILE LINE 121 ~~~~~~~~~~~~~~");
          this.updateUserFile();
          this.getCustomer().setAutoFillStatus(true);
          returnNum = 1;
          break;
        } else if (result.equals("no")) {
          break;
        }
      } else if (cardOption.equals("gift")){
        returnNum = 2;
        break;
      }
      start = "\nPlease re-enter: W";
    }
    System.out.printf("\nLINE 169: USER INPUT = [%s]\n", cardOption);
    System.out.printf("\nLINE 170: returnNum = [%s]\n", returnNum);

    return returnNum;
  }

  //reads newUserDetails.csv file to find user + find if autoFill status = true or false.
  // return: autoFillStatus
  //first check if they select gift card --> then check if valid, if not return immediately + print msg. Otherwise, continue to check autoFill status
  // if user selects credit card, skip gift card number/status + search for autoFill status

  //TODO: modify file for giftcard (if not redeemable)
  public void updateUserFile(){ //search for user in newUserDetails.csv file, modify default false to true
    try {
      File f = new File(this.userCsvFile);
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile);

      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        //find matching customer result
        // System.out.printf("line = \n", line);
        if(line.split(",")[2].equals(this.getCustomer().getEmail())
        && line.split(",")[8].equals("F")){ //update as 'T'
         //update as 'T'
          myWriter.write(line.substring(0, line.length()-1) +"T\n");
        } else {
          myWriter.write(line+"\n");
        }
      }
      myReader.close();
      myWriter.close();
      tempFile.renameTo(new File(this.userCsvFile)); //replace temp file with og file

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  // public boolean checkUserStatus(User customer, String cardOption, String yesNoOption){ 
  //   boolean returnResult = false;
  //   boolean autoFillStatus = false;
  //   boolean reedemableStatus = false;

  //   try {
  //     File f = new File(this.userCsvFile);
  //     Scanner myReader = new Scanner(f);
  //     while (myReader.hasNextLine()) { //as long as you can keep reading the file, grab the details
  //       String line = myReader.nextLine();
  //       String[] detailsArray = line.split(",");
  //       //retrieve last 
  //       if (cardOption.equals("credit")){
  //         if (yesNoOption == "no"){ //don't autofill for next time
  //           break;
            
  //         } else if (detailsArray[8].equals("F") && yesNoOption == "yes") { //haven't autofilled + user wants to autofill details
  //           autoFillStatus = true;
  //           this.getCustomer().setAutoFillStatus(true);
  //         }
  //       } else if (cardOption.equals("gift")){
  //       }
  //     }
  //   }
  // }
}
