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

-- create a new file for gift cards (just the card number + status to compare against when user enters gift card number to pay)


TODO: timeout for idle activity (2 mins)
      cancel transaction ANYTIME: [cancel]
      implement card number to be hidden + validated against cards in credit_cards.json file
      
      PRINT booking movie details at top + no. of tickets + total amount
*/

public class Transaction {
  private User customer;

  private File tempFile;
  private File tempFile2;
  private File userCsvFile;
  private File giftCardsFile;

  private static String USER_FILE_NAME = "newUserDetails2.csv";
  private static String TEMP_FILE_NAME = "cardTemp.csv";
  private static String TEMP_FILE_2_NAME = "cardTemp2.csv";
  private static String GIFT_CARD_FILE_NAME = "giftCards.csv";

  public Transaction(User customer){
    this.customer = customer;

    userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
    tempFile = DataController.accessCSVFile(TEMP_FILE_NAME);
    tempFile2 = DataController.accessCSVFile(TEMP_FILE_2_NAME);
    giftCardsFile = DataController.accessCSVFile(GIFT_CARD_FILE_NAME);
  }

  public static void setUserFile(String name){
    USER_FILE_NAME = name;
  }

  public void setGiftCardsFile(String name) {
    GIFT_CARD_FILE_NAME = name;
  }

  public User getCustomer(){
    return this.customer;
  }

  //TODO: print booking movie details:
  // e.g. BOOK: WAR FOR THE PLANET OF THE APES | WED 27 OCT - 9:30 PM 4 - SILVER CLASS | 28/100
  // Number of tickets: (n)
  // Total Amount: $90.95 --> from Tim's transaction screen on miro

  public void printScreen(){ //hi-fi ui of user transaction screen
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the payment page :)            ");
    System.out.println("           Please enter your details below!                  ");
    System.out.println("*******************************************************\n");

  }

  //run()
  // get total amount of all tickets from booking ticket/list of tickets? to compare against gift card num/credit card

  // TODO: autogenerate a unique transaction ID for each user
  public void proceedPayment(){ //card details fill out
    this.printScreen();
    this.askForUserDetails();
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
      autoFill = "invalid";
    }
    return autoFill;
  }

  //return a user object??

  public boolean printOptions(int num){ //boolean userAutoFillStatus){ //print prompt to save if user hasn't changed status yet else don't show
    if (num == 0){ //direct straight to cancelling
      System.out.println("\n*******************************************************");
      System.out.println("     CANCELLING TRANSACTION + REDIRECTING YOU BACK\n               TO HOME PAGE~ in 3..2..1..");
      System.out.println("*******************************************************\n");
      return false;
    } 

    System.out.println("Please enter an option below~");
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

    return continueToPay;
  }
  
  public void askForUserDetails(){
    boolean continueToPay = this.printOptions(-1);
    Scanner scan = new Scanner(System.in);
    if (continueToPay == true){
      // if autofill option of user isn't true, prompt user to enter card details
      int res = this.askAutoFillCardDetails();
      
      if (res == -1 && this.getCustomer().getAutoFillStatus() == true){
        this.askForCreditCardDetails(null, null, true);
      } else if (res == -1 && this.getCustomer().getAutoFillStatus() == false){ //user wants to pay w credit card but no auto fill, prompt for input 

        System.out.printf("\nPlease enter your card number: ");
        String cNumber = scan.nextLine();
        System.out.printf("Please enter your csv number: ");
        String csvNumber = scan.nextLine();
         // TODOOOOOOOOO: validate cNumber with robin's reading credit_cards.json function...

        this.askForCreditCardDetails(cNumber, csvNumber, false);       
      } else {
        while (true){
          if (res == 2){
            int returnResult = this.askForGiftCardDetails();
            if (returnResult == 0){
              break;
            }
            if (returnResult == 2){
              System.out.println("LINE 132:::::: pay remaining with credit card");
              break;
            } else if (returnResult == 3){
              this.printOptions(0);
              break;
            }
          }
        }
      }
    }
    System.out.println("*******************************************************\n\n");
  }

  //return: returnNum: int (either continue to ask to enter details or print details if auto filled already)
  public int askAutoFillCardDetails(){
    Scanner scan = new Scanner(System.in);
    String cardOption = null;

    int returnNum = -1;

    String start = "W";
    while (true){
      System.out.printf("%shich card would you like to use? (credit/gift): ", start);
      //since gift card --> 1 time use, only ask below msg if credit card selected?
      cardOption = scan.nextLine();
      if (cardOption.equals("credit")){
        break;
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

  //first check if they select gift card --> then check if valid, if not return immediately + print msg. Otherwise, continue to check autoFill status
  // if user selects credit card, skip gift card number/status + search for autoFill status

  public void updateAutoFillStatus(){ //search for user in newUserDetails.csv file, modify default autoFillStatus false to true 
    try {
//      File f = new File(this.userCsvFile); //this.userCsvFile
      File f = this.userCsvFile;
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile);

      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        //find matching customer result
        if(line.split(",")[2].equals(this.getCustomer().getEmail())
        && line.split(",")[5].equals("F")){ //update as 'T'
         //update as 'T'
          myWriter.write(line.substring(0, line.length()-1) +"T");
        } else {
          myWriter.write(line+"\n");
        }
      }
      myReader.close();
      myWriter.close();

      tempFile.renameTo(this.userCsvFile);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //checks if status is reedemble, if it is, update to become false
  // if it isn't, return value saying you cant use this gift card number anymore

  public String updateGiftCardStatus(String userInputGNumber){ //overwrites existing gift cards in file by changing the reedemble status of the gift card so it can no longer be used for next time
    String msg = "not redeemable";;
    try {
      File f = this.giftCardsFile;
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile2);
      //find matching customer result
      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        String[] detailsArray = line.split(",");
        //change reedemable to not reedemable
        if (userInputGNumber.equals(detailsArray[0])){  //match found
          if (detailsArray[1].equals("T")){
            myWriter.write(line.substring(0, line.length()-1) +"F\n"); //set as no longer reedemable
            msg = "first time ok";
          } else if (detailsArray[1].equals("F")){
            myWriter.write(line+"\n");
          } 
        } else {
          myWriter.write(line+"\n");
          // msg = "invalid number";
        }
      }
      myReader.close();
      myWriter.close();
      tempFile2.renameTo(this.giftCardsFile); //replace temp file with og file

    } catch (IOException e) {
      e.printStackTrace();
    }

    if(msg.equals("not redeemable")){
      System.out.println("LINE 254: OH NO! The gift card number you have entered is no longer redeemable. Please enter another or go back to pay with credit card.");
    }

    System.out.printf("LINE271::::::::::: msg = [%s]\n", msg);
    return msg;
  }

  public String checkIfGiftCardExists(String userInputGNumber){ //overwrites existing gift cards in file by changing the reedemble status of the gift card so it can no longer be used for next time
    String msg = "invalid number";
    try {
      File f = this.giftCardsFile;
      Scanner myReader = new Scanner(f);
      
      //find matching customer result
      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        String[] detailsArray = line.split(",");
        //change reedemable to not reedemable
        if (userInputGNumber.equals(detailsArray[0])){  //match found
          msg = "found";
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return msg;
  }

  public static String getUserFileName() {
    return USER_FILE_NAME;
  }

  public File getUserCsvFile() {
    return userCsvFile;
  }

  public File getGiftCardsFile() {
    return giftCardsFile;
  }

  public File getTempFile() {
    return tempFile;
  }

  public File getTempFile2() {
    return tempFile2;
  }

  public static String getGiftCardFileName() {
    return GIFT_CARD_FILE_NAME;
  }

  public static String getTempFile2Name() {
    return TEMP_FILE_2_NAME;
  }

  public void setTempFileName(String name) {
    TEMP_FILE_NAME = name;
  }

  public void setTempFile2Name(String name) {
    TEMP_FILE_2_NAME = name;
  }


  public static String getTempFileName() {
    return TEMP_FILE_NAME;
  }

  public int askForGiftCardDetails(){

    Scanner scan = new Scanner(System.in);
    System.out.printf("Please enter your gift card number: ");
    String num = scan.nextLine();

    String msg = this.checkIfGiftCardExists(num);
    if (msg.equals("found")){
      String returnMsg = this.updateGiftCardStatus(num);
      //if it is redeemable but not enough money ask to pay with credit card remaining
      if (returnMsg.equals("not redeemable")){
        System.out.println("Please select from the following: ");
        System.out.printf("1. Enter another gift card\n2. Go back to pay with credit card"+
        "\n3. Cancel payment\n"+"\nEnter option: ");
        while (true) {
          int option = scan.nextInt();
          if (option == 1){
            return 1;
          } else if (option == 2){
            return 2;
          } else if (option == 3){
            return 3;
          } else {
            System.out.println("Line 149: please re-enter a valid option: ");
          }
        }
      } else if (returnMsg.equals("first time ok")){
        System.out.println("\nTRANSACTION COMPLETE (Y/N): ");
        return 0; //ok
      }
    } 
    //invalid number
    //re-loop this
    System.out.println("The gift card number you have provided does not match the details provided in our system. Please try again.\n");
    return -1;
    
  }

  public int askForCreditCardDetails(String cardNumber, String csvNumber,boolean userStatus){
    Scanner scan = new Scanner(System.in);
    if (userStatus == true){ //saved before
      System.out.println("\nPrinting user's card details below (saved before)!");
      System.out.printf("Name: %s\n", this.getCustomer().getNickname());
      System.out.printf("Card number provided: %s\n", this.getCustomer().getCardNumber());
      System.out.println("Are the details above correct? OR would you like to update your card details? (Y/N): ");
      System.out.println("\nTRANSACTION COMPLETE");
      return 1;
    } else if (userStatus == false){
      System.out.printf("\nWould you like to save your card details for next time? (Y/N): ");
      String option2 = scan.nextLine();
      String result = this.checkAutoFillOption(option2);
      if (result.equals("yes")){
        //search for user in newUserDetails.csv file, modify default false to true
        System.out.println("ABOUT TO UPDATE USER DETAILS IN FILE LINE 121 ~~~~~~~~~~~~~~");
        this.updateAutoFillStatus();
        this.getCustomer().setAutoFillStatus(true);
        this.getCustomer().setCardNumber(cardNumber);

        System.out.println("TRANSACTION COMPLETE :)");
        return 1;
      }
    }
    return 0;
  }
}