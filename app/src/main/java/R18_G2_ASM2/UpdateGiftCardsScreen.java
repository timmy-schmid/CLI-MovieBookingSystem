package R18_G2_ASM2;

import java.util.*;
import java.io.*;


import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class UpdateGiftCardsScreen extends Screen{
  private static User user; //staff or manager
  
  // HomeScreen home;
  private static File giftCardsFile;
  private static String GIFT_CARD_FILE_NAME = "giftCards.csv";

  private static File tempFile2;
  private static String TEMP_FILE_2_NAME = "cardTemp2.csv";

  //when staff functions page shows up and option 1 inputted --> direct to this page 
  public UpdateGiftCardsScreen (MovieSystem mSystem){
    super(mSystem);
    user = user;
    try {
      tempFile2 = DataController.accessCSVFile(TEMP_FILE_2_NAME);
      giftCardsFile = DataController.accessCSVFile(GIFT_CARD_FILE_NAME);
      // GIFT_CARD_FILE_NAME = giftCardsFile.getAbsolutePath();
      // TEMP_FILE_2_NAME = tempFile2.getAbsolutePath();
    } catch (IOException e) {e.printStackTrace();}
  }

  public User getUser(){
    return user;
  }
  public File getGiftCardFile(){
    return giftCardsFile;
  }
  @Override
  protected void setOptions() {

    if (mSystem.getUser().getUserType() == UserType.STAFF) {
      maxInputInt = 7;
    } else if (mSystem.getUser().getUserType() == UserType.MANAGER)  {
      maxInputInt = 9;
    } else {
      throw new IllegalArgumentException("Invalid user type for this screen");
    }
    options.add("q"); options.add("Q");
  }

  public void setGiftCardsFile (File file) {
    giftCardsFile = file;
    // GIFT_CARD_FILE_NAME = file.getAbsolutePath();
  }

  public void setGiftCardsFileName (String filename) {
    GIFT_CARD_FILE_NAME = filename;
  }

  @Override
  public void print() {
    // this.clearScreen();
    this.title = "Welcome to the update gift cards screen page~";
    this.printHeader();
    this.printOptions();
    this.chooseOption();
  }

  @Override
  public void chooseOption(){
    Scanner scan = new Scanner(System.in);

    while (true){
      String res = scan.nextLine();
      if (res.equals("1")){
        System.out.printf("Please enter the gift card number to add: ");
        while (true) {
          String gNum = scan.nextLine();
          int num = this.addNewGiftCard(gNum);
          if (num == 1){ //valid gift card successfully entered
            this.nextOption();
            String nextOption = scan.nextLine();
            if(nextOption.equals("2")){
              this.title = "REDIRECTING YOU BACK TO STAFF PAGE~ in 3..2..1..";
              this.printHeader();
              break;
            } else {
              System.out.printf("Please enter the gift card number to add: ");
            }
          }
        }
        break;
      } else if (res.equals("2")){
          System.out.printf("Please enter the gift card number to update status of: ");
          while (true) {
            String gNum2 = scan.nextLine();
            if (User.checkIfGiftCardExists(giftCardsFile, gNum2).equals("invalid number")){ //cannot update status of number that doesn't exist in system
              System.out.printf("The number you have entered is invalid. Please enter another valid gift card number: ");
            } else {
              int num2 = User.updateGiftCardStatus(GIFT_CARD_FILE_NAME, tempFile2, gNum2);
              
              if (num2 == 1){
                while (true){
                  this.nextOption();
                  String nextOption = scan.nextLine();
                  if (nextOption.equals("2")){
                    this.title = "REDIRECTING YOU BACK TO STAFF PAGE~ in 3..2..1..";
                    this.printHeader();
                    return;
                  } else if (nextOption.equals("1")){
                    System.out.printf("Please enter the gift card number to update status of: ");
                    break;
                  } else {
                    System.out.println("Please enter a valid command.");
                  } 
                }
              }
            }
          }
        } else if (res.equals("3")){
        this.title = "REDIRECTING YOU BACK TO STAFF PAGE~ in 3..2..1..";
        this.printHeader();
        break;
      } else {
        System.out.printf("Please select a valid command: ");
      }
    }
  }
  public void printOptions(){
   
    this.printOptionsText();
    System.out.println("1. Add a new gift card number\n" +
                        "2. Update existing gift card status\n" + 
                        "3. Cancel and go back to staff page\n");

    this.printUserInputText();
  }

  public void nextOption(){
    System.out.println();
    this.printOptionsText();
    System.out.println("1. Continue\n" +
                      "2. End and go back to staff page\n");
    this.printUserInputText();
  }

  public int addNewGiftCard(String userInputGNumber){
    if (User.isValidGiftCardNumber(userInputGNumber) == false) {
      return -1;
    }
    try {
      Scanner myReader = new Scanner(giftCardsFile);
      String currentLine = "";
      
      while (myReader.hasNextLine()){ 
        //check if number already exists
        currentLine = myReader.nextLine();
        String[] line = currentLine.split(",");
        if(line[0].equals(userInputGNumber)){
          System.out.printf("The number you have entered already exists. Please enter another valid gift card number: ");
          return -1;
        }
      }
      myReader.close();
      try {
        FileWriter myWriter = new FileWriter(giftCardsFile, true); //for appending to existing file 
        myWriter.write("\n"+userInputGNumber+",T");
        myWriter.close();

      } catch(IOException e){
        e.printStackTrace();
      } 
    }  catch (FileNotFoundException e){
      System.out.printf("FILE NOT FOUND ERROR: %s!\n", giftCardsFile.getAbsolutePath());
      e.printStackTrace();
    }
    return 1;
  }
}