package R18_G2_ASM2;

import java.util.*;
import java.io.*;
import java.rmi.server.UID;

/*
CSV FORMAT: <userID, nickname, email, phoneNumber, password, autoFillStatus, userType>

CSV FORMAT: <giftCardNumber, reedemableStatus>

JSON FORMAT: <cardName, cardNumber>

read newUserDetails.csv
read credit_cards.json
read giftCards.csv

This class proceeds transaction using gift card or credit card or both when a customer has booked seats for a movie. Any cancellation of transaction would be written to report for the manager.
*/

public class Transaction {
  private Customer customer;

  private File tempFile;
  private File tempFile2;
  private File userCsvFile;
  private File giftCardsFile;

  private final double price = 25;
  private final double giftCardTotalAmount = 50;

  private static String USER_FILE_NAME = "newUserDetails.csv";
  private static String TEMP_FILE_NAME = "cardTemp.csv";
  private static String TEMP_FILE_2_NAME = "cardTemp2.csv";
  private static String GIFT_CARD_FILE_NAME = "giftCards.csv";

  long startTime = System.currentTimeMillis();
  long elapsedTime = 0L;
  int TWO_MINUTES = 2*60*1000;

  private int UID = 1;

  private String userGiftNumber;

  public Transaction(Customer customer){
    this.customer = customer;
    this.userGiftNumber = null;
    try {
      this.userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
      this.tempFile = DataController.accessCSVFile(TEMP_FILE_NAME);
      this.tempFile2 = DataController.accessCSVFile(TEMP_FILE_2_NAME);
      this.giftCardsFile = DataController.accessCSVFile(GIFT_CARD_FILE_NAME);
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
    System.out.println("*******************************************************\n");
    System.out.println("Total amount: $"+ String.format("%.2f", price*this.getCustomer().getTotalPrice()));
  }
  
  public void run() throws IOException { 
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

  public String printOptions(int num){ 
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
          this.getFinalMsg("gift", this.userGiftNumber);
          break;
       } else if (returnResult == 2){
          // pay remaining amount with credit card
          this.askForCreditCardDetails(this.getCustomer().getAutoFillStatus());
          break;
       } else if (returnResult == 3){
          this.printOptions(0);
          break;
       }
     }
   }
  }

  // if user selects credit card, search for autoFill status in newUserDetails.csv and modify default autoFillStatus false to true 
  public void updateAutoFillStatus(){
    try {
      File f = this.getUserCsvFile();
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile);

      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        //find matching customer result
        String[] detailsArray = line.split(",");
        if(detailsArray[2].equals(this.getCustomer().getEmail())
        && detailsArray[5].equals("F")){
         //update as 'T'
          myWriter.write(detailsArray[0] + "," + detailsArray[1] +
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

  public File getUserCsvFile() {
    return userCsvFile;
  }

  public void setTempFile(File file) {
    this.tempFile = file;
    this.TEMP_FILE_NAME = file.getAbsolutePath();
  }

  public void setTempFile2(File file) {
    this.tempFile2= file;
    this.TEMP_FILE_2_NAME = file.getAbsolutePath();
  }

  public int askForGiftCardDetails(){
    Scanner scan = new Scanner(System.in);

    if (isElapsed()) {
      return -1;
    }
    System.out.printf("Please enter your gift card number: ");
    String num = scan.nextLine();
    this.userGiftNumber = num;
    String msg = User.checkIfGiftCardExists(giftCardsFile, num);
    if (msg.equals("found true")){
      //if it is redeemable but not enough money ask to pay with credit card remaining
      if ((price*this.getCustomer().getTotalPrice()) <= this.giftCardTotalAmount){
        return 0;
        
      } else {       
        System.out.println("The price of the tickets exceed amount stored in gift card.\n");
        System.out.println("Please select from the following: ");
        System.out.printf("\n1. Enter another gift card\n2. Go back to pay with credit card"+
        "\n3. Cancel payment\n"+"\nEnter option: ");
        while (true) {
          if(isElapsed()) {
            return -1;
          }
          String option = scan.nextLine();
          if (option.equals("1")){
            return 1;
          } else if (option.equals("2")){
            return 2;
          } else if (option.equals("3")){
            TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.CANCEL);
            return 3;
          } else {
            System.out.printf("Please re-enter a valid option: ");
          }
        }
        // return 2;
      }
    } else if (msg.equals("found false")){
      System.out.println("The number you have entered is no longer available.\n");
      System.out.println("Please select from the following: ");
      System.out.printf("\n1. Enter another gift card\n2. Go back to pay with credit card"+
      "\n3. Cancel payment\n"+"\nEnter option: ");
      while (true) {
        if(isElapsed()) {
          return -1;
        }
        String option = scan.nextLine();
        if (option.equals("1")){
          return 1;
        } else if (option.equals("2")){
          return 2;
        } else if (option.equals("3")){
          TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.CANCEL);
          return 3;
        } else {
          System.out.printf("Please re-enter a valid option: ");
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
          break;
          //Direct to next page!!!
        } else if (result == false){
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
      if (temp != 2){ //dont display final msg if user decides to cancel transaction
        this.setUserCardDetails(this.getCustomer(), name, number, true);
        this.getFinalMsg("credit", this.userGiftNumber);
      }
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
              this.updateAutoFillStatus();
            } else {
              this.setUserCardDetails(this.getCustomer(), null, null, false); //revert back to original
            }
          }
        } else if (cardType.equals("gift")){
          User.updateGiftCardStatus(GIFT_CARD_FILE_NAME, tempFile2, userInputNumber);
        }
        // generate report for staff/manager
        this.getCustomer().completeTransaction();
        TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.SUCCESS);

        System.out.println("\nTransaction Successful!");
        this.printReceipt();
        UID+=1;
        return true;

      } else if (option.toLowerCase().equals("c")){
        TransactionSummary.writeToTransactionSummaryReport(customer, TransactionType.CANCEL);
        System.out.println("\nTransaction cancelled!");
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

  public void printReceipt(){
    System.out.println("Please see your receipt below to present at the cinema.\n");
    System.out.printf("YOUR TRANSACTION UID IS: %d\n\n", UID);
    System.out.println(this.getCustomer().getTicketMessage());
    System.out.println("Total amount: $"+ String.format("%.2f", price*this.getCustomer().getTotalPrice()));
  }
}