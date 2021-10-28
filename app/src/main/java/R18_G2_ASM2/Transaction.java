package R18_G2_ASM2;

import java.util.*;
import java.io.*;


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

  private final double price = 50; //50 before

  private static String USER_FILE_NAME = "newUserDetails.csv";
  private static String TEMP_FILE_NAME = "cardTemp.csv";
  private static String TEMP_FILE_2_NAME = "cardTemp2.csv";
  private static String GIFT_CARD_FILE_NAME = "giftCards.csv";

  public Transaction(User customer){
    this.customer = customer;

    this.userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
    this.tempFile = DataController.accessCSVFile(TEMP_FILE_NAME);
    this.tempFile2 = DataController.accessCSVFile(TEMP_FILE_2_NAME);
    this.giftCardsFile = DataController.accessCSVFile(GIFT_CARD_FILE_NAME);

    this.USER_FILE_NAME = this.userCsvFile.getAbsolutePath(); //str version
    this.TEMP_FILE_NAME = this.tempFile.getAbsolutePath();
    this.TEMP_FILE_2_NAME = this.tempFile2.getAbsolutePath();
    this.GIFT_CARD_FILE_NAME = this.giftCardsFile.getAbsolutePath();

  }

  public static void setUserFile(String name){
    USER_FILE_NAME = name;
  }

  public void setGiftCardsFile(File file) {
    this.giftCardsFile = file;
    this.GIFT_CARD_FILE_NAME = file.getAbsolutePath();
  }

  public User getCustomer(){
    return this.customer;
  }

  public void printScreen(){ //hi-fi ui of user transaction screen
    System.out.println("\n*******************************************************");
    System.out.println("            Welcome to the payment page :)            ");
    System.out.println("               Movie to book details               ");
    System.out.println("*******************************************************\n");
    System.out.printf("Number of tickets:");
    // System.out.println(this.getCustomer().getTicketMessage()); //saves tickets from previous cancellation of booking tickets??

    // System.out.println("Total amount: "+this.getCustomer().getTotalPrice()*price); //idk why price is wrong...
  }
  
  // get total amount of all tickets from booking ticket/list of tickets? to compare against gift card num/credit card

  // TODO: autogenerate a unique transaction ID for each user

    //--> user's tickets details --> 
  public void run() throws IOException { //card details fill out
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

  public String printOptions(int num){ //boolean userAutoFillStatus){ //print prompt to save if user hasn't changed status yet else don't show
    String returnMsg = "";
    if (num == 0){ //direct straight to cancelling
      System.out.println("\n*******************************************************");
      System.out.println("     CANCELLING TRANSACTION + REDIRECTING YOU BACK\n               TO HOME PAGE~ in 3..2..1..");
      System.out.println("*******************************************************\n");
      return "cancel";
    } 

    System.out.println("Please select a payment method:");
    System.out.println("\n1 - Credit Card\n"+ "2 - Gift Card\n" + "C - Cancel Transaction"); //or TO CANCEL TRANSACTION, PRESS [C]
    System.out.printf("\nUser Input: ");

    Scanner scan = new Scanner(System.in);
    String option = null;
    while (true){
      option = scan.nextLine();
      
      if (option.equals("1")){
        returnMsg = "1";
        break;
      } else if (option.equals("2")){
        returnMsg = "2";
        break;

      } else if (option.toLowerCase().equals("c")){ //or go back to booking page??
        returnMsg = "cancel";
        break;
      } else { //invalid command entered
        System.out.printf("Please enter a valid command: ");
      }
    }
    return returnMsg;
  }
  
  public void askForUserDetails() throws IOException {
    String msg = this.printOptions(-1);
    Scanner scan = new Scanner(System.in);
    if (msg.equals("1")){ //credit card
      // if autofill option of user isn't true, prompt user to enter card details
      if (this.getCustomer().getAutoFillStatus() == true){
        this.askForCreditCardDetails(null, null, true);
      } else { //user wants to pay w credit card but no auto fill, prompt for input 

        System.out.printf("\nPlease enter your card number: ");
        String cNumber = scan.nextLine();
        System.out.printf("Please enter your csv number: ");
        String csvNumber = scan.nextLine();

        this.askForCreditCardDetails(cNumber, csvNumber, false);       
      }
    } else if (msg.equals("2")){ //gift card
      while (true){
        int returnResult = this.askForGiftCardDetails();
        if (returnResult == 0){
          this.getFinalMsg();
          break;
        } else if (returnResult == 2){
          System.out.println("LINE 171: pay remaining with credit card");
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
    String msg = "not redeemable";
    // System.out.printf("LINE 238: USERGIFTCARDFILE = %s\n", this.getGiftCardFileName());
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

    // if(msg.equals("not redeemable")){
    //   System.out.println("LINE 254: OH NO! The gift card number you have entered is no longer redeemable. Please enter another or go back to pay with credit card.");
    // }

    // System.out.printf("LINE271::::::::::: msg = [%s]\n", msg);
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

  public int askForGiftCardDetails(){

    Scanner scan = new Scanner(System.in);
    System.out.printf("Please enter your gift card number: ");
    String num = scan.nextLine();

    String msg = this.checkIfGiftCardExists(num);
    if (msg.equals("found")){
      String returnMsg = this.updateGiftCardStatus(num);
      //if it is redeemable but not enough money ask to pay with credit card remaining
      if (returnMsg.equals("not redeemable")){
        System.out.println("The number you have entered is no longer available.\n");
        System.out.println("Please select from the following: ");
        System.out.printf("\n1. Enter another gift card\n2. Go back to pay with credit card"+
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
        return 0; //ok
      }
    } 
    //invalid number
    System.out.println("The gift card number you have provided does not match the details provided in our system. Please try again.\n");
    return -1;
    
  }

  public int askForCreditCardDetails(String cardNumber, String csvNumber,boolean userStatus) throws IOException {
    Scanner scan = new Scanner(System.in);
    if (userStatus == true){ //saved before
      System.out.println("\nPrinting user's card details below (saved before)!");
      System.out.printf("Name: %s\n", this.getCustomer().getCardName());
      System.out.printf("Card number provided: %s\n", this.getCustomer().getCardNumber());
      System.out.println("Are the details above correct? OR would you like to update your card details? (Y/N): ");
      this.getFinalMsg();
      return 1;
    } else if (userStatus == false){
      String name = null;
      String number = null;
      Scanner sc = new Scanner(System.in);
      while (true) {
        System.out.printf("Please enter your credit card name: ");
        name = sc.nextLine();
        Console con = System.console();
        if (con != null) {
          char[] num = con.readPassword("Please enter your credit card number: ");
          number = new String(num);
          System.out.printf("NUMBER LINE 100: [%s]\n", number);
        } else {
          System.out.printf("Please enter your credit card number: ");
          number = sc.nextLine();
        }
        boolean result = this.checkCreditCardInfo(name, number);
        if (result == true){
          System.out.println("Match found! Proceeding to next stage!");
//        home.setUser(user);
//        home.run();
          //Direct to next page!!!
        } else if (result == false){
          System.out.println("Not Match!");
          int temp = 0;
          while (temp == 0) {
            String textinput = this.nextOption();
            if (textinput.equals("1")) {
              temp = 1;
            } else if (textinput.equals("2")) {
              System.out.println("Back to default page--default screen");
              temp = 2;
            } else {
              System.out.println("Invalid input, please choose agian!");
            }
          }
          if (temp == 1) {
            continue;
          } else if (temp == 2) {
            break;
          }
        }
      }
      System.out.printf("\nDo you want to save your card details to your account? (Y/N): ");
      String option2 = scan.nextLine();
      String result = this.checkAutoFillOption(option2);
      if (result.equals("yes")){
        //search for user in newUserDetails.csv file, modify default false to true
        System.out.println("ABOUT TO UPDATE USER DETAILS IN FILE LINE 121 ~~~~~~~~~~~~~~");
        this.updateAutoFillStatus();
        this.getCustomer().setAutoFillStatus(true);
        this.getCustomer().setCardName(name);
        this.getCustomer().setCardNumber(cardNumber);
        this.getFinalMsg();
        return 1;
      } else {
        this.getFinalMsg();
      }
    }
    return 0;
  }

  public String nextOption() throws IOException{
    System.out.printf("\nInvalid credit name or number, please select from the following:\n");
    System.out.println("1. CONTINUE USING CREDIT CARD");
    System.out.println("2. CANCEL");

//    ConsoleReader consoleReader = new ConsoleReader();
    String textinput = null;
//    textinput = consoleReader.readLine();
    Scanner scan = new Scanner(System.in);
    textinput = scan.nextLine();
    return textinput;
  }

  public boolean getFinalMsg(){
    Scanner scan = new Scanner(System.in);
    System.out.println("Select from the following: ");
    System.out.println("F - Finalise transaction\nC - Cancel transaction");
    System.out.printf("\nUser Input: ");

    while (true) {
      String option = scan.nextLine();
      if (option.equals("F")){
        System.out.println("\nTransaction Successful!");
        System.out.println("Please see your receipt below to present at the cinema: \n\n\n");
        // this.printReceipt();
        //movie name, time, cinema + seats
        return true;
      } else if (option.equals("C")){
        System.out.println("\nLINE 422: Transaction cancelled!");
        return false;
      } else {
        System.out.printf("Please enter a valid input: ");
      }
    }
  }

  public boolean checkCreditCardInfo(String name, String number) {
    ParseJson parseJson = new ParseJson();
    if (parseJson.matchCard(name, number)) {
      return true;
    } else {
      return false;
    }
  }

  public void printReceipt(){
    System.out.println(this.getCustomer().getTicketMessage());
    //booking ticket
  }
}