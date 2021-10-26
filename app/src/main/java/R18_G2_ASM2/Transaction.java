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
*/

public class Transaction {
  private Card userCreditcard;
  private GiftCard userGiftCard;
  private User customer;

  private File tempFile;
  private File tempFile2;
  private String userCsvFile;
  public Transaction(User customer){
    this.customer = customer;
    // this.userCsvFile = "app/src/main/datasets/newUserDetails.csv";
    this.userCsvFile = "/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT-2-NEW/R18_G2_ASM2/app/src/main/datasets/newUserDetails.csv";
    this.tempFile = new File("/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT-2-NEW/R18_G2_ASM2/app/src/main/datasets/cardTemp.csv");

    this.tempFile2 = new File("/Users/annasu/Downloads/USYD2021/SEMESTER_2/SOFT2412/ASSIGNMENT-2-NEW/R18_G2_ASM2/app/src/main/datasets/cardTemp2.csv");

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
      autoFill = "invalid";
    }
    return autoFill;
  }

  //return a user object??

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
      
      //ask for gift vs credit lol
      int res = this.askAutoFillCardDetails();
      
      if (res == 1 && this.getCustomer().getAutoFillStatus() == true){
        System.out.println("printing user's card details below (saved before)!");
        System.out.printf("Name: %s\n", this.getCustomer().getCreditCard().getName());
        System.out.printf("Card number provided: %s\n", this.getCustomer().getCreditCard().getCardNumber());
        System.out.println("Are the details above correct? OR would you like to update your card details? (Y/N): ");
        //..............................................................
        System.out.println("\nTRANSACTION COMPLETE");

      } else {
        if (res == 2){ //user wants to enter their gift card num
          //or save automatically bc entered when registering?
          System.out.printf("Please enter your gift card number: ");
          String num = scan.nextLine();
          if (num.equals(this.getCustomer().getGiftCard().getCardNumber())){
            //if it is redeemable but not enough money ask to pay with credit card remaining
            // if it isn't: go back to pay with card
            int returnNum = this.updateGiftCardStatus();
            if (returnNum == 1){
              System.out.println("line 116: now need to go back to prev page to pay with credit card...");
            } else {
              System.out.println("\nTRANSACTION COMPLETE (Y/N): "); //status updated (T--> F)
            }          
          } else {
            //re-loop this
            System.out.println("The gift card number you have provided does not match the details provided in our system. Please try again");
          }
        } else if (res == -1){ //user wants to pay w credit card but no auto fill, prompt for input 

          //TODO: validate credit card number is same as whats provided when registering
          System.out.printf("\nPlease enter your card number: ");
          String cNum = scan.nextLine();
          // System.out.printf("Please enter your csv number: ");
          // String csvNum = scan.nextLine();

          //first time assume user hasn't save card details but a prompt pops up after they entered details
          System.out.printf("\nWould you like to save your card details for next time? (Y/N): ");
          String option2 = scan.nextLine();
          String result = this.checkAutoFillOption(option2);
          if (result.equals("yes")){
            //search for user in newUserDetails.csv file, modify default false to true
            System.out.println("ABOUT TO UPDATE USER DETAILS IN FILE LINE 121 ~~~~~~~~~~~~~~");
            this.updateAutoFillStatus();
            this.getCustomer().setAutoFillStatus(true);

            System.out.println("TRANSACTION COMPLETE :)");
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
      File f = new File(this.userCsvFile);
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile);

      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        //find matching customer result
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

  //checks if status is reedemble, if it is, update to become false
  // if it isn't, return value saying you cant use this gift card number anymore

  //same as updateAutofillStatus()
  public int updateGiftCardStatus(){ //overwrites existing gift cards in file by changing the reedemble status of the gift card so it can no longer be used for next time
    int returnNum = -1;
    // System.out.println("LINE 221: inside updateGiftCardStatus() function ----------->");
    try {
      File f = new File(this.userCsvFile);
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile2);

      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        //find matching customer result
        String[] detailsArray = line.split(",");
        if (this.getCustomer().getGiftCard().getCardNumber().equals(detailsArray[6])){
          
          if(detailsArray[7].equals("T") && this.getCustomer().getAutoFillStatus() == true) {
            myWriter.write(line.substring(0, line.length()-3) +"F,T\n"); //set as no longer reedemable
          } else if(detailsArray[7].equals("T") && this.getCustomer().getAutoFillStatus() == false){
            myWriter.write(line.substring(0, line.length()-3) +"F,F\n"); //set as no longer reedemable
          }
          else if(detailsArray[7].equals("F")){
            myWriter.write(line+"\n");
            returnNum = 1;
          }
        } else {
          myWriter.write(line+"\n");
        }
      }
      myReader.close();
      myWriter.close();
      tempFile2.renameTo(new File(this.userCsvFile)); //replace temp file with og file

    } catch (IOException e) {
      e.printStackTrace();
    }

    if (returnNum == 1){
      System.out.println("LINE 254: OH NO! The gift card number you have entered is no longer redeemable. Please enter another or go back to pay with credit card.");

    }
    return returnNum;
  }
}