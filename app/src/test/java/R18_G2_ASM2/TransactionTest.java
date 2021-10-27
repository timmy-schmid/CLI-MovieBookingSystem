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

  private User userA;
  private User userB;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;    

  @BeforeAll static void setPath() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach
  public void setUp() {
    User userA = new User(6, "Anna", "anna@yahoo.com","0412345881", "Lalala1234");
   
    User userB = new User(7, "helen", "hhh@gmail.com", "0423456719", "Asdf1234!*");

    userB.setAutoFillStatus(true);

    t = new Transaction(userA); //no autofill option
    t2 = new Transaction(userB); //autofill option
    System.setOut(new PrintStream(outContent));

    t.setUserFile("newUserDetailsTest2.csv");
    t2.setUserFile("newUserDetailsTest2.csv");

    t.setGiftCardsFile("giftCardsTest.csv");
    t2.setGiftCardsFile("giftCardsTest.csv");

    t.setTempFileName("temp.csv");
    t.setTempFile2Name("temp2.csv");
    t2.setTempFileName("temp.csv");
    t2.setTempFile2Name("temp2.csv");
  }
  @AfterEach
  public void tearDown(){ 
    userA = null;
    userB = null;
    t = null;
    t2 = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test void transactionsNotNull(){
    assertNotNull(t);
    assertNotNull(t2);
  }

  @Test void canPrintScreen(){
    String screenMsg = "\n*******************************************************\n" +
    "            Welcome to the payment page :)            \n" +
    "           Movie to book details           \n"+
    "*******************************************************\n\n" +
    "Number of tickets: \n" + 
    "Total Amount: \n\n";

    t.printScreen();
    assertEquals(outContent.toString(), screenMsg);
  }

  @Test void correctAutoFillOption(){
    String autoFillMsg = t.checkAutoFillOption("YES");
    assertEquals("yes", autoFillMsg);
  }

  @Test void incorrectAutoFillOption(){
    String autoFillMsg = t.checkAutoFillOption("lala");
    assertEquals("invalid", autoFillMsg);
  }

  @Test void continueToPayWithCard(){
    String optionMsg = "Please select a payment method:\n" +
                      "\n1 - Credit Card\n"+ "2 - Gift Card\n" + 
                      "C - Cancel Transaction\n" +
                      "\nUser Input: ";

    // String msg = "\n*******************************************************\n"+
    // "PROCEEDING TO PAY WITH CARD~ in 3..2..1..\n" + 
    // "*******************************************************\n\n";

    String expectedOut = optionMsg;//+ msg;

    String inputMessage = "1\n";
    String expected = "1";

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);

    String result = t.printOptions(-1);
    assertEquals(expected, result);
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test void continueToPayWithCard2(){
    String optionMsg = "Please select a payment method:\n" +
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
    assertEquals(result, "found");
  }

  @Test void testInvalidGiftCard(){
  String result = t.checkIfGiftCardExists("11342416GC");
    assertEquals(result, "invalid number");
  }

  // @Test void testInvalidGiftCardDetails(){
  //   String msg = "Please enter your gift card number: " +
  //               ""
  // }
  

  //gift card csv file not found ??????????

  // @Test void validGiftCardStatusUpdate() throws IOException{
  //   //assert reading file (T --> F for specific number)
  //   String status = "";

  //   String message = t.updateGiftCardStatus("GC1111111111111115");
  //   assertEquals(message, "first time ok");
  //   String currentLine = "";
  //   String lastLine = "";
  //   BufferedReader myReader = new BufferedReader(new FileReader(t.getGiftCardFileName()));
  // //   System.out.println("t.giftcardssfilename = " + t.getGiftCardFileName());
  //   while ((currentLine = myReader.readLine()) != null){
  //     lastLine = currentLine;
  //     if (lastLine.split(",")[0].equals("GC1111111111111115")){
  //       status = lastLine.split(",")[1];
  //       break;
  //     }
  //   }
  //   myReader.close();
  //   // int id = id = Integer.parseInt(lastLine.split(",")[0]);
  //   // assertEquals(lastLine, String.valueOf(id) + ",benji,"+ username + ",0404040123,"+pwd+",F");
  //   assertEquals(status, "F");
  // //   assertEquals(outContent.toString(), "lala");
  // }
}
