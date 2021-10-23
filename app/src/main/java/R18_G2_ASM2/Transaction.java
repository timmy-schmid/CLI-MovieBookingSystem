package R18_G2_ASM2;

import java.util.*;


/*
CSV FORMAT: <userID, nickname, email, phoneNumber, creditCardNumber, password, giftCard, reedemableStatus, autoFillStatus>

read newUserDetails.csv
- 1 = true, 0 = false
*/

public class Transaction {
  private Card userCreditcard;
  private GiftCard userGiftCard;
  
  private User customer;
  public Transaction(User customer){
    this.customer = customer;
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

  public void printOptions(boolean userAutoFillStatus){ //print prompt to save if user hasn't changed status yet else don't show

    System.out.println("Would you like to pay with credit card or gift card?\n");

    System.out.println("ENTER 1 TO PAY WITH CREDIT CARD\n"+ "ENTER 2 TO PAY WITH GIFT CARD\n" +
    "ENTER 3 TO CANCEL TRANSACTION~"); //or TO CANCEL TRANSACTION, PRESS [C]
    System.out.printf("\nEnter option: ");

    Scanner scan = new Scanner(System.in);
    String option = null;
    boolean continueToPay = false;
    while (true){
      option = scan.nextLine();
      System.out.println("\n*******************************************************");
      
      if (option.equals("1")){
        System.out.println("PROCEEDING TO PAY WITH CREDIT CARD~ in 3..2..1..");
        continueToPay = true;
        break;
      } else if (option.equals("2")){
        System.out.println("PROCEEDING TO PAY WITH GIFT CARD~ in 3..2..1..");
        continueToPay = true;
        break;
      } else if (option.equals("3")){ //or go back to booking page??
        System.out.println("CANCELLING TRANSACTION + REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..\nSEE YOU NEXT TIME! :)");
        break;
      } else { //invalid command entered
        System.out.printf("OH NO, please enter a valid command");
      }
      System.out.println("\n*******************************************************");
    }
    System.out.println("*******************************************************\n");
    if (continueToPay == true && userAutoFillStatus == false){
      this.askAutoFillCardDetails();

    } else if (continueToPay == true && userAutoFillStatus == true){ //print user card details on screen, prompt user for validation?


    }

  }
  public void askAutoFillCardDetails(){
    Scanner scan = new Scanner(System.in);
    String option = null;
    String result = null;
    while (true){
      System.out.printf("Would you like to save your card details for next time? (Y/N): ");
      option = scan.nextLine();
      result = this.checkAutoFillOption(option);

      if (!result.equals("invalid")){ //either yes/no
        //update user autoFillStatus
        this.getCustomer().setAutoFillStatus(true);
        break;
      }
    }
    System.out.printf("LINE 97: USER INPUT = [%s]\n", result);
  }
}
