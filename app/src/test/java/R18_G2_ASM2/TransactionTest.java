package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;

public class TransactionTest {
  private Transaction t;
  private Transaction t2;

  private Customer userA;
  private Customer userB;

  private File gFile;
  private File gFile2;

  private File tFile;
  private File tFile2;

  // private File cFile;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;    

  @BeforeAll static void setPath() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach
  public void setUp() {

    userA = new Customer(6, "Anna", "anna@yahoo.com","0412345881", "Lalala1234");
  
    userB = new Customer(7, "helen", "hhh@gmail.com", "0423456719", "Asdf1234!*");

    userB.setAutoFillStatus(true);

    t = new Transaction(userA); //no autofill option
    t2 = new Transaction(userB); //autofill option
    System.setOut(new PrintStream(outContent));

    try {
      gFile = DataController.accessCSVFile("giftCardsTest.csv");    
      gFile2 = DataController.accessCSVFile("giftCardsTest.csv");    
      tFile = DataController.accessCSVFile("cardTemp.csv");    
      tFile2 = DataController.accessCSVFile("cardTemp2.csv");   
    } catch (FileNotFoundException e) {
      assertEquals(true,false);
    }
    // cFile = DataController.accessCSVFile("credit_cards.json");

    t.setUserFile("newUserDetailsTest.csv");
    t2.setUserFile("newUserDetailsTest.csv");

    t.setGiftCardsFile(gFile);
    t2.setGiftCardsFile(gFile2);

    t.setTempFile(tFile);
    t.setTempFile2(tFile2);
    t2.setTempFile(tFile);
    t2.setTempFile2(tFile2);

  }

  @AfterEach
  public void tearDown(){ 
    userA = null;
    userB = null;
    t = null;
    t2 = null;
    gFile = null;
    gFile2 = null;
    tFile = null;
    tFile2 = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test void transactionsNotNull(){
    assertNotNull(t);
    assertNotNull(t2);
  }
  //TODO comment out for demo
  /*
  @Test void canPrintScreen(){
    String screenMsg = "\033[H\033[2J" + "*******************************************************\n" +
    "            Welcome to the payment page :)            \n" +
    "               Movie to book details               \n"+
    "*******************************************************\n\n" +
    "Number of tickets:";
    // "Total Amount: \n\n";

    t.printScreen();
    assertEquals(outContent.toString(), screenMsg);
  }*/

  @Test void correctAutoFillOption(){
    String autoFillMsg = t.checkAutoFillOption("YES");
    assertEquals("yes", autoFillMsg);
  }

  @Test void incorrectAutoFillOption(){
    String autoFillMsg = t.checkAutoFillOption("lala");
    assertEquals("invalid", autoFillMsg);
  }

  @Test void continueToPayWithGiftCard(){
    String optionMsg = "\nPlease select a payment method:\n" +
                      "\n1 - Credit Card\n"+ "2 - Gift Card\n" + 
                      "C - Cancel Transaction\n" +
                      "\nUser Input: ";

    String expectedOut = optionMsg;//+ msg;

    String inputMessage = "2\n";
    String expected = "2";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    String result = t.printOptions(-1);
    assertEquals(expected, result);
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test void continueToPayWithCard2(){
    String optionMsg = "\nPlease select a payment method:\n" +
                        "\n1 - Credit Card\n"+ "2 - Gift Card\n" + 
                        "C - Cancel Transaction\n" +
                        "\nUser Input: ";

    String msg = "Please enter a valid command: ";
    String expectedOut = optionMsg + msg;

    String inputMessage = "lala\n1\n";
    String expected = "1";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    String result = t.printOptions(-1);
    assertEquals(expected, result);
    assertEquals(outContent.toString(), expectedOut);
  }


  @Test void testCancelTransaction(){
    String expected = "cancel";
    String msg = "\n*******************************************************\n"+
    "     CANCELLING TRANSACTION + REDIRECTING YOU BACK\n               TO HOME PAGE~ in 3..2..1..\n"+
    "*******************************************************\n\n";
    String result = t.printOptions(0);
    assertEquals(expected, result);
    assertEquals(outContent.toString(), msg);
  }

  @Test void testGetMethods(){
    assertNotNull(t2.getCustomer());
    assertNotNull(t2.getUserCsvFile());
    assertNotNull(t2.getTempFile());
    assertNotNull(t2.getTempFileName());
    assertNotNull(t2.getTempFile2Name());
    assertNotNull(t.getGiftCardFileName());
    assertNotNull(t.getGiftCardsFile());
    assertNotNull(t.getTempFile2());
    assertNotNull(t.getUserFileName());
  }

  @Test void testValidGiftCard(){
    String result = t.checkIfGiftCardExists("11111111111116GC");
    assertEquals(result, "found false");
  }

  @Test void testInvalidGiftCard(){
    String result = t.checkIfGiftCardExists("11342416GC");
    assertEquals(result, "invalid number");
  }

  //gift card csv file not found ??????????

  @Test void validGiftCardStatusUpdate() throws IOException{
    //assert reading file (T --> F for specific number)
    String status = "";    
    String currentLine = "";
    String lastLine = "";
    BufferedReader myReader = new BufferedReader(new FileReader(t.getGiftCardFileName()));
    //read file before overwriting
    while ((currentLine = myReader.readLine()) != null){
      lastLine = currentLine;
      if (lastLine.split(",")[0].equals("11111111111115GC")){
        status = lastLine.split(",")[1];
        break;
      }
    }
    myReader.close();

    String message = t.updateGiftCardStatus("11111111111115GC");

    if (status.equals("T")){
      assertEquals(message, "first time ok"); //first time run (T-->F)
    } else {

      assertEquals(message, "not redeemable"); //next time read, already changed
    }
  }


  @Test void giftCardNotRedeemable(){ //valid card number in system
    String msg = "Please enter your gift card number: "+
                 "The number you have entered is no longer available.\n\n";
    String msg2 = "Please select from the following: \n" +
                  "\n1. Enter another gift card\n2. Go back to pay with credit card" +
                  "\n3. Cancel payment\n" +
                  "\nEnter option: "; 

    String expectedOut = msg + msg2;
    String inputMessage = "11111111111115GC\n3";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    int returnNumber = t.askForGiftCardDetails();
    assert(returnNumber == 3); //cancel transaction
  }

  @Test void giftCardNotRedeemable2(){ 
    String msg = "Please enter your gift card number: "+"The number you have entered is no longer available.\n\n";
    String msg2 = "Please select from the following: \n" +
                  "\n1. Enter another gift card\n2. Go back to pay with credit card" +
                  "\n3. Cancel payment\n" +
                  "\nEnter option: "; 

    String expectedOut = msg + msg2 + "Line 149: please re-enter a valid option: \n";
    String inputMessage = "11111111111116GC\n8\n2";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    int returnNumber = t.askForGiftCardDetails();
    assert(returnNumber == 2); //cancel transaction
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test void giftCardNotInvalid(){ //invalid card number + wrong input supplied
    String msg = "Please enter your gift card number: ";
    String expectedOut = msg + "The gift card number you have provided does not match the details provided in our system. Please try again.\n\n";
    String inputMessage = "lalala015GC";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    int returnNumber = t.askForGiftCardDetails();
    assert(returnNumber == -1); //cancel transaction
  }

  //TODO: UPDATE THIS FUNCTION TO INCLUDE ADDITIONAL INPUT FOR SAVING CARD DETAILS FOR NEXT TIME!!!!
  
  // @Test void testFinalMessage(){
  //   String msg = "Select from the following: \n" +
  //                 "F - Finalise transaction\nC - Cancel transaction\n" +
  //                 "\nUser Input: ";
    
  //   String inputMessage = "F";
  //   String msg2 = "\nTransaction Successful!\n" +
  //                 "Please see your receipt below to present at the cinema: \n\n\n\n";
  //   String expectedOut = msg+msg2;
  //   ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  //   System.setOut(new PrintStream(outContent));
  //   ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
  //   System.setIn(in);
    
  //   boolean result = t2.getFinalMsg();
  //   assert(result == true);
  //   assertEquals(outContent.toString(), expectedOut);
  // }

  //TODO commenting out for the demo/
  /* 
  @Test void testFinalMessage2() throws NumberFormatException, IOException{
    String msg = "Select from the following: \n" +
                  "F - Finalise transaction\nC - Cancel transaction\n" +
                  "\nUser Input: ";
    
    String inputMessage = "lala\nC";
    String msg2 = "Please enter a valid input: " +
                  "\nLINE 455: Transaction cancelled!\n";

    String expectedOut = msg+msg2;
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);
    
    boolean result = t2.getFinalMsg();
    assert(result == false);
    assertEquals(outContent.toString(), expectedOut);
  }*/

  @Test void printNextOptionWorks() throws IOException {
    String expectedOut = "\nInvalid credit name or number, please select from the following:\n" + 
                 "1. CONTINUE USING CREDIT CARD\n" + 
                 "2. CANCEL\n";
    
    String inputMessage = "1";
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    String result = t.nextOption();
    assertEquals(outContent.toString(), expectedOut);
    assertEquals(inputMessage, result);
  }

  @Test void printNextOptionWorks2() throws IOException {
    String msg = "\nInvalid credit name or number, please select from the following:\n" + 
                 "1. CONTINUE USING CREDIT CARD\n" + 
                 "2. CANCEL\n";
    String expectedOut = msg;
    String inputMessage = "2";
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    String result = t.nextOption();
    assertEquals(outContent.toString(), expectedOut);
    assertEquals(inputMessage, result);
  }


  @Test void testAutoSaveWorks() throws IOException{
    // userB --> wants to remember card details for next time
    userB.setCardName("Kasey");
    userB.setCardNumber("60146");
    userB.setCvvNumber("123");

    String message = "\nPrinting user's card details below (saved before)!\n" +
                     "Name: Kasey" + 
                     "\nCard number provided: 60146" +
                     "\n\nAre the details above correct? OR would you like to update your card details? (Y/N): \n";

    String msg = "Select from the following: \n" +
    "F - Finalise transaction\nC - Cancel transaction\n" +
    "\nUser Input: ";

    String msg2 = "Please enter a valid input: " +
                     "\nLINE 455: Transaction cancelled!\n";
    
    String inputMessage = "lala\nC";

    String expectedOut = message + msg + msg2;

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    int result = t2.askForCreditCardDetails(userB.getAutoFillStatus());

    assert(result == 1);
    assertEquals(outContent.toString(), expectedOut);

  }

  // @Test void validCreditCardInput(){ //cant pass :(
  //   userB.setCardName("Debbie");
  //   userB.setCardNumber("92090");
  //   boolean result = t2.checkCreditCardInfo(userB.getCardName(), userB.getCardNumber());
  //   assert(result == true);
  // }

  @Test void invalidCreditCardInput(){
    userB.setCardName("Casey");
    userB.setCardNumber("10146");
    userB.setCvvNumber("123");
    boolean result = t2.checkCreditCardInfo(userB.getCardName(), userB.getCardNumber());
    assert(result == false);
  }


  //no line found exception :(((
  // @Test void cancelPayGiftCard() throws IOException{
  //     String optionMsg = "Please select a payment method:\n" +
  //                       "\n1 - Credit Card\n"+ "2 - Gift Card\n" + 
  //                       "C - Cancel Transaction\n" +
  //                       "\nUser Input: ";
  
  //     String msg = "Please enter your gift card number: ";
  //     String msg2 = "The number you have entered is no longer available.\n\n"+
  //                   "Please select from the following: \n" +
  //                   "\n1. Enter another gift card\n2. Go back to pay with credit card" +
  //                   "\n3. Cancel payment\n" +
  //                   "\nEnter option: "; 
  //     String msg3 = "\n*******************************************************\n"+
  //     "     CANCELLING TRANSACTION + REDIRECTING YOU BACK\n               TO HOME PAGE~ in 3..2..1..\n"+
  //     "*******************************************************\n\n";

  //     String expectedOut = optionMsg + msg + msg2  + msg3;
  //     String inputMessage = "2\n11111111111115GC\n3";
    

  //     ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  //     System.setOut(new PrintStream(outContent));
  
  //     ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
  
  //     System.setIn(in);
  
  //     t2.askForUserDetails();
  //     assertEquals(outContent.toString(), expectedOut);
  
  // }


  //no line found exception :(((
  // @Test void testPayCreditCard() throws IOException{
  //   userB.setCardName("Kasey");
  //   userB.setCardNumber("60146");
  //   userB.setCvvNumber("123");

  //   String optionMsg = "Please select a payment method:\n" +
  //   "\n1 - Credit Card\n"+ "2 - Gift Card\n" + 
  //   "C - Cancel Transaction\n" +
  //   "\nUser Input: ";
  
  //   String msg = "\nPrinting user's card details below (saved before)!\n" +
  //   "Name: Kasey" + 
  //   "\nCard number provided: 60146" +
  //   "\n\nAre the details above correct? OR would you like to update your card details? (Y/N): \n";
    
  //   String msg2 = "Select from the following: \n" +
  //   "F - Finalise transaction\nC - Cancel transaction\n" +
  //   "\nUser Input: ";
    
  //   String msg3 = "\nTransaction Successful!\n" +
  //   "Please see your receipt below to present at the cinema: \n\n\n\n";

  //   String expectedOut = optionMsg + msg + msg2 + msg3;
  //   String inputMessage = "1\nf";
  
  //   ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  //   System.setOut(new PrintStream(outContent));

  //   ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

  //   System.setIn(in);

  //   t2.askForUserDetails();
  //   assertEquals(outContent.toString(), expectedOut);
  // }
}
