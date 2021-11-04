package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateGiftCardsScreenTest {
  private UpdateGiftCardsScreen gcs;
  private File giftCardsFile;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;    

  MovieSystem system;
  @BeforeAll static void setPath() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach
  public void setUp() throws IOException {
    system = new MovieSystem();
    gcs = new UpdateGiftCardsScreen(system);
    giftCardsFile = DataController.accessCSVFile("giftCardsTest.csv");    
    
    //set up streams
    gcs.setGiftCardsFile(giftCardsFile);
    gcs.setGiftCardsFileName("giftCardsTest.csv");
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void tearDown(){ 
    system = null;
    gcs = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test 
  public void testNotNull(){
    assertNotNull(gcs);
  }

  @Test 
  public void canPrintOptions(){
    String msg = "Please choose from the following options:\n\n" + 
                 "1. Add a new gift card number\n" +
                 "2. Update existing gift card status\n" + 
                 "3. Cancel and go back to staff page\n\n";
    String msg2 = "User Input:";
    String expectedOut = msg + msg2;   
    gcs.printOptions();
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test 
  public void canPrintNextOption(){
    String msg = "Please choose from the following options:\n\n";
    
    String msg2 = "User Input:";
    String expectedOut = "\n"+msg+
                        "1. Continue\n" +
                        "2. End and go back to staff page\n\n" +
                        msg2;
    gcs.nextOption();
    assertEquals(outContent.toString(), expectedOut);
  }


  @Test 
  public void canChooseValidOption(){ //go back to staff page
    String printHeader = "*********************************************"+ "*********************************************\n" +
    "                      Welcome to the update gift cards screen page~\n" +
    "*********************************************"+ "*********************************************\n";
    
    String options = "Please choose from the following options:\n\n" +  //printOptions()
                    "1. Add a new gift card number\n" +
                    "2. Update existing gift card status\n" + 
                    "3. Cancel and go back to staff page\n\n";
    
    String inputMessage = "3";
    
    String endMsg = "*********************************************"+
    "*********************************************\n" +
    "                     REDIRECTING YOU BACK TO STAFF PAGE~ in 3..2..1..\n" +
    "************************************************"+ "******************************************\n";
    
    String expectedOut = printHeader + options + "User Input:" + endMsg;

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    gcs.print();
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test 
  public void canChooseInvalidOption(){ //go back to staff page
    String printHeader = "*********************************************"+ "*********************************************\n" +
    "                      Welcome to the update gift cards screen page~\n" +
    "*********************************************"+ "*********************************************\n";
    
    String options = "Please choose from the following options:\n\n" +  //printOptions()
                    "1. Add a new gift card number\n" +
                    "2. Update existing gift card status\n" + 
                    "3. Cancel and go back to staff page\n\n" +
                    "User Input:";
    
    String inputMessage = "lala\n3";
    
    String endMsg = "*********************************************"+
    "*********************************************\n" +
    "                     REDIRECTING YOU BACK TO STAFF PAGE~ in 3..2..1..\n" +
    "************************************************"+ "******************************************\n";
    
    String expectedOut = printHeader + options + "Please select a valid command: " + endMsg;

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    gcs.print();
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test 
  public void cantAddNewGiftCard(){
    String userInputGNumber = "041256lala"; //invalid
    int result = gcs.addNewGiftCard(userInputGNumber);
    assert(result  == -1);

    assertEquals(outContent.toString(), "Please enter a 14 digit number followed by GC suffix: ");
  }

  @Test 
  public void cantAddNewGiftCard2(){
    String userInputGNumber = "11111111111111GC"; //already exists
    int result = gcs.addNewGiftCard(userInputGNumber);
    assert(result  == -1);

    assertEquals(outContent.toString(), "The number you have entered already exists. Please enter another valid gift card number: ");
  }

  @Test 
  public void canAddNewGiftCard() throws IOException {
    String userInputGNumber = "11111111111119GC";
    BufferedReader myReader = new BufferedReader(new FileReader(gcs.getGiftCardFile()));
    int result2 = 1; 
    String currentLine = "";
    while ((currentLine = myReader.readLine()) != null){
      if (currentLine.split(",")[0].equals(userInputGNumber)){
        result2 = -1; //number already exists
      }
    }
    myReader.close();
    int result = gcs.addNewGiftCard(userInputGNumber);
    assertEquals(result, result2);
  }

  @Test
  public void canAddMultipleCards() throws IOException {

    //check if numbers are already in csv file (when running tests)
    BufferedReader myReader = new BufferedReader(new FileReader(gcs.getGiftCardFile()));
    int result2 = 1; 
    String currentLine = "";
    while ((currentLine = myReader.readLine()) != null){
      if (currentLine.split(",")[0].equals("11111111111120GC") ||
        currentLine.split(",")[0].equals("11111111111121GC")) {
        result2 = -1; //number already exists
      }
    }
    myReader.close();


    if (result2 != -1) {
      String printHeader = "*********************************************"+ "*********************************************\n" +
      "                      Welcome to the update gift cards screen page~\n" +
      "*********************************************"+ "*********************************************\n";
      
      String options = "Please choose from the following options:\n\n" +  //printOptions()
                      "1. Add a new gift card number\n" +
                      "2. Update existing gift card status\n" + 
                      "3. Cancel and go back to staff page\n\n";
      
      String msg = "Please enter the gift card number to add: ";
      String choseOption = "\nPlease choose from the following options:\n\n" + //nextOption()
                          "1. Continue\n" +
                          "2. End and go back to staff page\n\n" +
                          "User Input:";

      String endMsg = "*********************************************"+
      "*********************************************\n" +
      "                     REDIRECTING YOU BACK TO STAFF PAGE~ in 3..2..1..\n" +
      "************************************************"+ "******************************************\n";
      String inputMessage = "1\n11111111111120GC\n1\n11111111111121GC\n2";


      String expectedOut = printHeader + options + "User Input:" + msg + choseOption + msg + choseOption + endMsg;


      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
      System.setIn(in);
      gcs.print();
      assertEquals(outContent.toString(), expectedOut);
    } else {
      assert(result2 == -1); //the 2 numbers above already exist
    }
  }

  @Test 
  public void canUpdateGiftCard() throws IOException { //1 card test
    //check if numbers are already in csv file (when running tests)
    BufferedReader myReader = new BufferedReader(new FileReader(gcs.getGiftCardFile()));
    int result = 1; 
    String currentLine = "";
    String LINE = "";
    while ((currentLine = myReader.readLine()) != null){
      if (currentLine.split(",")[0].equals("11111111111120GC")){
        if (currentLine.split(",")[1].equals("T")) {
          result = -1;
        } else {
          result = -2;
        }
        LINE = currentLine;
      }
    }
    myReader.close();


    String printHeader = "*********************************************"+ "*********************************************\n" +
    "                      Welcome to the update gift cards screen page~\n" +
    "*********************************************"+ "*********************************************\n";
    
    String options = "Please choose from the following options:\n\n" +  //printOptions()
                    "1. Add a new gift card number\n" +
                    "2. Update existing gift card status\n" + 
                    "3. Cancel and go back to staff page\n\n";
    
    String msg = "Please enter the gift card number to update status of: ";
    String choseOption = "\nPlease choose from the following options:\n\n" + //nextOption()
                        "1. Continue\n" +
                        "2. End and go back to staff page\n\n" +
                        "User Input:";

    // String msg2 = "The number you have entered is already not redeemable, do you want to make it reedemable again? (Y/N): ";
    String endMsg = "*********************************************"+
    "*********************************************\n" +
    "                     REDIRECTING YOU BACK TO STAFF PAGE~ in 3..2..1..\n" +
    "************************************************"+ "******************************************\n";

    if (result == -1) {
      String inputMessage = "2\n11111111111120GC\n2"; //update T-->F-->T

      String expectedOut = printHeader + options + "User Input:" + msg + choseOption + endMsg;


      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
      System.setIn(in);
      gcs.print();
      assertEquals(outContent.toString(), expectedOut);
    } else { 
      assertEquals(LINE, "11111111111120GC,F");
    }
  }
}
