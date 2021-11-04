package R18_G2_ASM2;

import java.util.*;
// import java.io.IOException;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
import java.io.*;

/*
CSV FORMAT: <userID, nickname, email, phoneNumber, password, giftCard, reedemableStatus, autoFillStatus,userType>

read newUserDetails.csv

TODO: timeout for idle activity (2 mins)
      cancel transaction ANYTIME: [cancel]
      implement card number to be hidden + validated against cards in credit_cards.json file      
*/

public class Transaction {
  private Customer customer;

  private File tempFile;
  private File tempFile2;
  private File userCsvFile;
  private File giftCardsFile;

  private final double price = 50;
  private final double giftCardTotalAmount = 50; //100?

  private static String USER_FILE_NAME = "newUserDetails.csv";
  private static String TEMP_FILE_NAME = "cardTemp.csv";
  private static String TEMP_FILE_2_NAME = "cardTemp2.csv";
  private static String GIFT_CARD_FILE_NAME = "giftCards.csv";

  long startTime = System.currentTimeMillis();
  long elapsedTime = 0L;
  int TWO_MINUTES = 2*60*1000;
  private String userGiftNumber;

  public Transaction(Customer customer){
    this.customer = customer;
    this.userGiftNumber = null;
    try {
      this.userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
      this.tempFile = DataController.accessCSVFile(TEMP_FILE_NAME);
      this.tempFile2 = DataController.accessCSVFile(TEMP_FILE_2_NAME);
      this.giftCardsFile = DataController.accessCSVFile(GIFT_CARD_FILE_NAME);
  
      //this.USER_FILE_NAME = this.userCsvFile.getAbsolutePath(); //str version
      //this.TEMP_FILE_NAME = this.tempFile.getAbsolutePath();
      //this.TEMP_FILE_2_NAME = this.tempFile2.getAbsolutePath();
      //this.GIFT_CARD_FILE_NAME = this.giftCardsFile.getAbsolutePath();
      } catch (FileNotFoundException e) {
        System.out.println("Unable to complete transaction: " + e.getMessage());
      }
  }

  public static void setUserFile(String name){
    USER_FILE_NAME = name;
  }

  public void setGiftCardsFile(File file) {
    this.giftCardsFile = file;
    this.GIFT_CARD_FILE_NAME = file.getAbsolutePath();
  }

  public Customer getCustomer(){
    return this.customer;
  }

  public void printScreen(){
    // System.out.print("\033[H\033[2J"); // clears screen
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the payment page :)            ");
    System.out.println("               Movie to book details               ");
    System.out.println("*******************************************************\n");
    System.out.printf("Number of tickets:");
    // System.out.println(this.getCustomer().getTicketMessage()); //saves tickets from previous cancellation of booking tickets??

    // System.out.println("Total amount: "+this.getCustomer().getTotalPrice()*price);
  }
  
  // get total amount of all tickets from booking ticket/list of tickets? to compare against gift card num/credit card

  // TODO: autogenerate a unique transaction ID for each user

    //--> user's tickets details --> 

  public void run() throws IOException { //card details fill out
    this.printScreen();
    this.askForUserDetails();

    if(isElapsed()) {
      TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.TIMEOUT);
      System.out.println("The transaction has expired. If you would like to rebook, please try again.");
    }
  }

  public boolean isElapsed() {
    elapsedTime = (new Date()).getTime() - startTime;
    //System.out.println("Current Elapsed Time:" + elapsedTime / 1000);
    if (elapsedTime > TWO_MINUTES) {
      return true;
    } else {
      return false;
    }
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

  public String printOptions(int num){ //print prompt to save if user hasn't changed status yet else don't show
    String returnMsg = "";
    if (num == 0){ //direct straight to cancelling
      System.out.println("\n*******************************************************");
      System.out.println("     CANCELLING TRANSACTION + REDIRECTING YOU BACK\n               TO HOME PAGE~ in 3..2..1..");
      System.out.println("*******************************************************\n");
      return "cancel";
    } 

    System.out.println("\nPlease select a payment method:");
    System.out.println("\n1 - Credit Card\n"+ "2 - Gift Card\n" + "C - Cancel Transaction"); 
    System.out.printf("\nUser Input: ");

    Scanner scan = new Scanner(System.in);
    String option = null;

    while (true){

      if (isElapsed()) {
        returnMsg = "cancel";
        break;
      }
      option = scan.nextLine();
      if (option.equals("1")){
        returnMsg = "1";
        break;
      } else if (option.equals("2")){
        returnMsg = "2";
        break;

      } else if (option.toLowerCase().equals("c")){ //go back to booking page or home page??
        returnMsg = "cancel";
        this.getCustomer().resetSeatMap();
        TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.CANCEL);
        break;
      } else {
        System.out.printf("Please enter a valid command: ");
      }
    }
    return returnMsg;
  }
  
  public void askForUserDetails() throws IOException {
   String msg = this.printOptions(-1);
   Scanner scan = new Scanner(System.in);
   if (msg.equals("1")){ //credit card
     // if autofill option of user isn't true, prompt user to enter card details otherwise prints the details 
      this.askForCreditCardDetails(this.getCustomer().getAutoFillStatus());
   } else if (msg.equals("2")){ //gift card
     while (true){
      if(isElapsed()) {
        return;
      }
       int returnResult = this.askForGiftCardDetails();
       if (returnResult == 0){
          // System.out.println("LINE 152::::::::: TRANSACTION@");
          this.getFinalMsg("gift", this.userGiftNumber);
          break;
       } else if (returnResult == 2){
          System.out.println("LINE 157: pay remaining amount with credit card");
          this.askForCreditCardDetails(this.getCustomer().getAutoFillStatus());
          break;
       } else if (returnResult == 3){
          this.printOptions(0);
          break;
       }
     }
   }
  }

  //first check if they select gift card --> then check if valid, if not return immediately + print msg. Otherwise, continue to check autoFill status
  // if user selects credit card, skip gift card number/status + search for autoFill status

  public void updateAutoFillStatus(){ //search for user in newUserDetails.csv file, modify default autoFillStatus false to true 
    try {
      File f = this.userCsvFile;
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile);

      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        //find matching customer result
        String[] detailsArray = line.split(",");
        if(detailsArray[2].equals(this.getCustomer().getEmail())
        && detailsArray[5].equals("F")){ //update as 'T'
         //update as 'T'
          myWriter.write(detailsArray[0]+","+detailsArray[1] +
          "," + detailsArray[2] + "," + detailsArray[3] + "," + 
          detailsArray[4] + ",T," + detailsArray[6]);
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

  //only update the file IF USER FINALISES TRANSACTION!!!!!
  // TODO::::::::: REMOVE LAST LINE NEW LINE!!!!!! --> otherwise stuffs up if staff member wants to add new gift card number

  public String updateGiftCardStatus(String userInputGNumber){ //overwrites existing gift cards in file by changing the reedemble status of the gift card so it can no longer be used for next time
    String msg = "not redeemable";
    System.out.printf("LINE 238: USERGIFTCARDFILE = %s\n", this.getGiftCardFileName());
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
          // System.out.println("MATCH FOUND LINE 217!!!!!!");
          if (detailsArray[1].equals("T")){
            myWriter.write(line.substring(0, line.length()-1) +"F\n"); //set as no longer reedemable
            msg = "first time ok";
          } else if (detailsArray[1].equals("F")){
            myWriter.write(line+"\n");
          } 
        } else {
          myWriter.write(line+"\n");
        }
      }
      myReader.close();
      myWriter.close();
      tempFile2.renameTo(this.giftCardsFile); //replace temp file with og file

    } catch (IOException e) {
      e.printStackTrace();
    }
    // if(msg.equals("not redeemable")){
    //   System.out.println("LINE 254: OH NO! The gift card number you have entered is no longer redeemable. Please enter another or go back to pay with credit card.");
    // }
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
          if (detailsArray[1].equals("T")){
            msg = "found true"; //if can be used (check if tickets amount <= 100)
          } else {
            msg = "found false"; 
          }
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return msg;
  }

  public String getUserFileName() { //static
    return this.USER_FILE_NAME;
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

  public String getGiftCardFileName() { //static
    return this.GIFT_CARD_FILE_NAME;
  }

  public String getTempFile2Name() { //static
    return this.TEMP_FILE_2_NAME;
  }

  public void setTempFile(File file) {
    this.tempFile = file;
    this.TEMP_FILE_NAME = file.getAbsolutePath();
  }

  public void setTempFile2(File file) {
    this.tempFile2= file;
    this.TEMP_FILE_2_NAME = file.getAbsolutePath();
  }

  public String getTempFileName() { //static
    return this.TEMP_FILE_NAME;
  }

  //TODO: only update file if user completes transaction
  public int askForGiftCardDetails(){

    Scanner scan = new Scanner(System.in);

    if(isElapsed()) {
      return -1;
    }
    System.out.printf("Please enter your gift card number: ");
    String num = scan.nextLine();
    this.userGiftNumber = num;
    String msg = this.checkIfGiftCardExists(num);
    if (msg.equals("found true")){
      // String returnMsg = this.updateGiftCardStatus(num);
      //if it is redeemable but not enough money ask to pay with credit card remaining
      if (this.getCustomer().getTotalPrice() <= this.giftCardTotalAmount){
        //continue to pay with gift card 
        return 0;
      } else {
        return 2;
      }
      // if (returnMsg.equals("not redeemable")){
      //   System.out.println("The number you have entered is no longer available.\n");
      //   System.out.println("Please select from the following: ");
      //   System.out.printf("\n1. Enter another gift card\n2. 
    } else if (msg.equals("found false")){
      System.out.println("The number you have entered is no longer available.\n");
      System.out.println("Please select from the following: ");
      System.out.printf("\n1. Enter another gift card\n2. Go back to pay with credit card"+
      "\n3. Cancel payment\n"+"\nEnter option: ");
      while (true) {
        if(isElapsed()) {
          return -1;
        }
        int option = scan.nextInt();
        if (option == 1){
          return 1;
        } else if (option == 2){
          return 2;
        } else if (option == 3){
          TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.CANCEL);
          return 3;
        } else {
          System.out.println("Line 149: please re-enter a valid option: ");
        }
      }
    } 
    //invalid number
    System.out.println("The gift card number you have provided does not match the details provided in our system. Please try again.\n");
    return -1; 
  }

  public int askForCreditCardDetails(boolean userStatus) throws IOException {
    Scanner scan = new Scanner(System.in);
    int temp = 0;

    if (userStatus == true){ //saved before
      System.out.println("\nPrinting user's card details below (saved before)!");

      System.out.printf("Name: %s\n", this.getCustomer().getCardName());
      System.out.printf("Card number provided: %s\n", this.getCustomer().getCardNumber());
      System.out.println("\nAre the details above correct? OR would you like to update your card details? (Y/N): ");
      this.getFinalMsg("credit", this.userGiftNumber);
      return 1;
    } else if (userStatus == false){
      String name = null;
      String number = null;
      Scanner sc = new Scanner(System.in);
      while (true) {

        if (isElapsed()) {
          return 0;
        }
        System.out.printf("Please enter your credit card name: ");
        name = sc.nextLine();
        Console con = System.console();
        if (con != null) { //hides card number on screen
          char[] num = con.readPassword("Please enter your credit card number: ");
          number = new String(num);
          // System.out.printf("NUMBER LINE 100: [%s]\n", number);
        } else {
          if (isElapsed()) {
            return 0;
          }
          System.out.printf("Please enter your credit card number: ");
          number = sc.nextLine();
        }
        //validates if card number exists in credit_cards.json file
        boolean result = this.checkCreditCardInfo(name, number);
        if (result == true){
          // System.out.println("Match found! Proceeding to next stage!");
          break;
  //        home.setUser(user);
  //        home.run();
          //Direct to next page!!!
        } else if (result == false){
          // System.out.println("No Match found!");
          while (temp == 0) {
            String textinput = this.nextOption(); //no match found, so new prompt
            if (textinput.equals("1")) {
              temp = 1;
            } else if (textinput.equals("2")) {
              System.out.println("Back to default page!");
              temp = 2;
            } else {
              System.out.println("Invalid input, please choose again!");
            }
          }
          if (temp == 1) {
            continue;
          } else if (temp == 2) {
            break;
          }
        }
      }
      this.setUserCardDetails(this.getCustomer(), name, number, true);
      this.getFinalMsg("credit", this.userGiftNumber);
    }
    return 0;
  }

  public void setUserCardDetails(Customer user, String name, String cardNumber, boolean newStatus){
    user.setAutoFillStatus(newStatus);
    user.setCardName(name);
    user.setCardNumber(cardNumber);
  }

  public String nextOption() {
    System.out.printf("\nInvalid credit name or number, please select from the following:\n");
    System.out.println("1. CONTINUE USING CREDIT CARD");
    System.out.println("2. CANCEL");

    String textInput = null;
    Scanner scan = new Scanner(System.in);
    textInput = scan.nextLine(); 
    return textInput;
  }

  // public boolean getFinalMsg( ) {
  public boolean getFinalMsg(String cardType, String userInputNumber) {

    Scanner scan = new Scanner(System.in);
    System.out.println("Select from the following: ");
    System.out.println("F - Finalise transaction\nC - Cancel transaction");
    System.out.printf("\nUser Input: ");

    while (true) {

      if (isElapsed()) {
        return false;
      }

      String option = scan.nextLine();
      if (option.toLowerCase().equals("f")){
        if (cardType.equals("credit")){
          if (this.getCustomer().getAutoFillStatus() == false){
            System.out.printf("\nDo you want to save your card details to your account for next time? (Y/N): ");
            if (isElapsed()) {
              return false;
            }
            String option2 = scan.nextLine();
            String result = this.checkAutoFillOption(option2);
            if (result.equals("yes")){
              //search for user in newUserDetails.csv file, modify default false to true
              System.out.println("ABOUT TO UPDATE USER DETAILS IN FILE LINE 121 ~~~~~~~~~~~~~~");
              this.updateAutoFillStatus();
              System.out.printf("LINE 487: just checking: user's details are: card name = [%s], card num = [%s]\n", this.getCustomer().getCardName(), this.getCustomer().getCardNumber());
            } else {
              this.setUserCardDetails(this.getCustomer(), null, null, false); //revert back to og
            }
          }
        } else if (cardType.equals("gift")){
          this.updateGiftCardStatus(userInputNumber);
        }
        this.getCustomer().completeTransaction();
        TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.SUCCESS);
        // this.getCustomer().cancelTransaction();
        System.out.println("\nTransaction Successful!");
        System.out.println("Please see your receipt below to present at the cinema: \n\n\n");
        // this.printReceipt();
        //movie name, time, cinema + seats
        this.getCustomer().completeTransaction();
        return true;
      } else if (option.toLowerCase().equals("c")){
        TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.CANCEL);
        System.out.println("\nLINE 455: Transaction cancelled!");
        return false;
      } else {
        System.out.printf("Please enter a valid input: ");
      }
    }
  }

  public boolean checkCreditCardInfo(String name, String number){
    ParseJson parseJson = new ParseJson();
    try {
      parseJson.retrieveCardDetails(); //need this line to check if card exists in list of cards ??
    } catch (Exception e) { e.printStackTrace();}
    if (parseJson.matchCard(name, number)) {
      return true;
    } else {
      return false;
    }
  }

  // public void printReceipt(){
  //   System.out.println(this.getCustomer().getTicketMessage());
  //   //booking ticket
  // }
}