package R18_G2_ASM2;

import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.regex.Pattern;


public class UpdateGiftCardsScreen extends Screen{
  private static User user;
  
  HomeScreen home;
  private static File giftCardsFile;
  private static String GIFT_CARD_FILE_NAME = "giftCards.csv";

  private static File tempFile2;
  private static String TEMP_FILE_2_NAME = "cardTemp2.csv";


  //when staff functions page shows up and option 1 inputted --> direct to this page 
  public UpdateGiftCardsScreen(HomeScreen home, User user) {
    user = user;
    this.home = home;
    tempFile2 = DataController.accessCSVFile(TEMP_FILE_2_NAME);
    this.giftCardsFile = DataController.accessCSVFile(GIFT_CARD_FILE_NAME);
    this.GIFT_CARD_FILE_NAME = giftCardsFile.getAbsolutePath();
    
    this.TEMP_FILE_2_NAME = tempFile2.getAbsolutePath();

  }

  public UpdateGiftCardsScreen() {
    this.home = home;
    tempFile2 = DataController.accessCSVFile(TEMP_FILE_2_NAME);
    giftCardsFile = DataController.accessCSVFile(GIFT_CARD_FILE_NAME);
    GIFT_CARD_FILE_NAME = giftCardsFile.getAbsolutePath();
    TEMP_FILE_2_NAME = tempFile2.getAbsolutePath();

  }

  public void setGiftCardsFile(File file) {
    giftCardsFile = file;
    GIFT_CARD_FILE_NAME = file.getAbsolutePath();
  }

  @Override
  public void print() {
    this.clearScreen();
    // if (user != null) {
    //   System.out.printf("Welcome %s to the update gift cards page. \n\n", user.getNickname());
    // }
    this.title = "Welcome to the update gift cards screen page~";
    this.printHeader();
    this.printOptions();
  }

  @Override
  public void chooseOption(){
    Scanner scan = new Scanner(System.in);
    String res = scan.nextLine();
    switch(res){ //selectedOption
      case "1":
        System.out.printf("Please enter the gift card number to add: ");
        while (true) {
          String gNum = scan.nextLine();
          int num = this.addNewGiftCard(gNum);
          if (num == 1){
            this.nextOption();
            String nextOption = scan.nextLine();
            if(nextOption.equals("2")){
              break;
            } else {
              System.out.printf("Please enter the gift card number to add: ");
            }
          }
        }
      case "2": //loop later
        System.out.printf("Please enter the gift card number to update status of: ");
        while (true) {
          String gNum2 = scan.nextLine();
          int num2 = this.updateGiftCardStatus(gNum2);
          if (num2 == 1){
            this.nextOption();
            String nextOption = scan.nextLine();
            if(nextOption.equals("2")){
              break;
            }
          }
          // System.out.printf("Please enter the gift card number to update status of: ");
        }
          // break;
      case "3":
        this.title = "REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..";
        this.printHeader();
        break;
      
      default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
    }
  }

  public void printOptions(){
   
    this.printOptionsText();
    System.out.println("1. Add a new gift card number\n" +
                        "2. Update existing gift card status\n" + 
                        "3. Cancel and go back to home page\n");

    this.printUserInputText();
    String text = null;

    this.chooseOption();
  }

  public void nextOption(){
    System.out.println();
    this.printOptionsText();
    System.out.println("1. Continue\n" +
                      "2  End and go back to home page\n");
    this.printUserInputText();
  }

  //from abstract userfields class...
  public boolean isValidGiftCardNumber(String number){ //did the user enter a correct gift card number that satisfies acceptance criteria?
    if (number == null){
      return false;
    }
    String gnumberRegex = "^\\d{14}GC$$";
    Pattern pattern = Pattern.compile(gnumberRegex);
  
    if (pattern.matcher(number).matches()){
      return true;
    } else {
      System.out.println("Your gift card number did not satisfy acceptance criteria.");
      return false;
    }
  }

  public int addNewGiftCard(String userInputGNumber){ //maybe move this to another class where you can access read/write functions to avoid redundancy 
    
    if (this.isValidGiftCardNumber(userInputGNumber) == false) {
      System.out.printf("\n\nPlease re-enter a valid gift card number: ");
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
      System.out.printf("FILE NOT FOUND ERROR: %s!\n", giftCardsFile);
    }
    return 1;
  }

  //same function from transaction class

  // TODO: check if number entered doesn't exist in file!!!!!!----->>>>



  public  int updateGiftCardStatus(String userInputGNumber){ //overwrites existing gift cards in file by changing the reedemble status of the gift card so it can no longer be used for next time    
    if (this.isValidGiftCardNumber(userInputGNumber) == false) {
      System.out.printf("Please re-enter a valid gift card number: ");
      return -1;
    }
    // System.out.printf("LINE 238: USERGIFTCARDFILE = %s\n", this.getGiftCardFileName());
    try {
      File f = giftCardsFile;
      Scanner myReader = new Scanner(f);
      FileWriter myWriter = new FileWriter(tempFile2);
      // //find matching customer result

      Scanner scan = new Scanner(System.in);

      // String currentLine = "";
      // String lastLine = "";
      // while (myReader.hasNextLine()){
      //   currentLine = myReader.nextLine();
      //   lastLine = currentLine;
      // }
      BufferedReader in = new BufferedReader(new FileReader(GIFT_CARD_FILE_NAME));
      String currentLine = "";
      String lastLine = "";
      String line1 = null;
      while ((line1 = in.readLine()) != null) {
        lastLine = line1;
      }
      while (myReader.hasNextLine()){
        String line = myReader.nextLine();
        String[] detailsArray = line.split(",");
        //change reedemable to not reedemable
        if (userInputGNumber.equals(detailsArray[0])){  //match found
          // if(lastLine.split(",")[0].equals(userInputGNumber)){ //last line in file is what to update (don't add newline)
          //   myWriter.write(line.substring(0, line.length()-1) +"F"); //set as no longer reedemable
          // } else {
          if (detailsArray[1].equals("T")){
            
            if (lastLine.equals(line)){ //set as no longer reedemable
              myWriter.write(line.substring(0, line.length()-1) +"F");
            } else { 
              myWriter.write(line.substring(0, line.length()-1) +"F\n");
            }
          } else if (detailsArray[1].equals("F")){ //check if last line??????
            System.out.printf("The number you have entered is already not redeemable, do you want to make it reedemable again? (Y/N): ");
            String input = scan.nextLine();
            if(input.toLowerCase().equals("y")){
              if (lastLine.equals(line)){
                myWriter.write(line.substring(0, line.length()-1) +"T");
              } else {
                myWriter.write(line.substring(0, line.length()-1) +"T\n");
              }
            } else { //no change
              if (lastLine.equals(line)){
                myWriter.write(line);
              } else {
                myWriter.write(line+"\n");
              }
            }
          }
        } else {
          if (lastLine.equals(line)){
            myWriter.write(line);
          } else {
            myWriter.write(line+"\n");
          }
        }
      }
      myReader.close();
      myWriter.close();

      tempFile2.renameTo(giftCardsFile); //replace temp file with og file

    } catch (IOException e) {
      e.printStackTrace();
    }
    return 1;
  }
}