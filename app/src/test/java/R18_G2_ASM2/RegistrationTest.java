package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;

import java.math.BigInteger;

class RegistrationTest {
  Registration reg;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;    

  @BeforeEach
  public void setUp() {
    reg = new Registration();
    // reg.setUserFile(new File("src/test/resources/userTest.csv"));

    reg.setUserFile("src/test/resources/userTest.csv");

    //set up streams
    System.setOut(new PrintStream(outContent));
  }
  @AfterEach
  public void tearDown(){ 
    reg = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test void RegNotNull(){
      assertNotNull(reg);
  }

  @Test void testInvalidUsername(){ //missing @ + .com
    String input = "lala@hotmail";
    assertFalse(reg.validateUser(input));
  }

  @Test void testInvalidUsername1(){ //""- no input
    String input = "";
    assertFalse(reg.validateUser(input));
  }
  @Test void testInvalidUsername2(){ //""- no letters before@
    String input = " @gmail.com";
    assertFalse(reg.validateUser(input));
  }
  @Test void testInvalidUsername3(){ //""- no letters before@
    String input = " @.com";
    assertFalse(reg.validateUser(input));
  }
  @Test void testInvalidUsername4(){ //""- no letters before@
    String input = "@gmail.com";
    assertFalse(reg.validateUser(input));
  }

  @Test void testInvalidUsername5(){
    String input = "hola@bob.com";
    assertFalse(reg.validateUser(input));
  }

  @Test void testValidUsername(){
    String input = "hola@gmail.com";
    assertTrue(reg.validateUser(input));
  }

  @Test void testValidUsername2(){ //alpha-numeric
    String input = "hoLLie241@hotmail.com";
    assertTrue(reg.validateUser(input));
  }

  @Test void testInvalidPassword(){ //too short
    String input = "abc234X!";
    assertFalse(reg.isValidPassword(input));
  }

  @Test void testInvalidPassword2(){ //no >= 1 cap
    String input = "abc234xx#!";
    assertFalse(reg.isValidPassword(input));
  }

  @Test void testInvalidPassword3(){
    String input = "";
    assertFalse(reg.isValidPassword(input));
  }

  @Test void testValidPassword(){
    String input = "Abcd1244#1";
    assertTrue(reg.isValidPassword(input));
  }

  @Test void testUserFileNotFound(){
    reg.setUserFile("src/main/datasets/UNKNOWN.csv");
    int result = reg.checkIfUserExists("username@gmail.com");
    assert(result == -2);
  }
  //testing writing to file works
  @Test void testValidReadingFile(){
    int result = reg.checkIfUserExists("username@gmail.com");
    assert(result == 1);
  }

  @Test void nullUserFile(){
    reg.setUserFile(null);
    assertNull(reg.getUserFile());
  }
  @Test void testValidReadingFile2(){ //user already exists
    int result = reg.checkIfUserExists("zendaya11@gmail.com");
    assert(result == -1);
  }

  //testing writing to file
  @Test void testWriteToFileWorks() throws IOException{ //should still validate inside function or just outside?
    String username = "benjilala@hotmail.com";
    String pwd = "Blahblahblah3";

    if (reg.checkIfUserExists(username) != -1){ //if user doesn't exist
      reg.writeUserDetailsToFile(username, pwd);

    //retrieve last line and compare
      String currentLine = "";
      String lastLine = "";
      
      //after writing to file, read the last line and check it matches given details
      BufferedReader myReader = new BufferedReader(new FileReader(reg.getUserFile()));
      while ((currentLine = myReader.readLine()) != null){
        lastLine = currentLine;
      }
      myReader.close();
      int id = id = Integer.parseInt(lastLine.split(",")[0]);
      assertEquals(lastLine, String.valueOf(id) + ","+ username + ","+pwd);
    }
  }
  @Test void testCreateAccountWorks() throws IOException{
    reg.setUserFile("src/test/resources/newUserTest.csv");
    int result = reg.checkIfUserExists("newUser@gmail.com");
    if (result == -1){ //exists alrdy
      return;
    } else {
      User newUser = reg.createAccount("newUser@gmail.com", "NewPassword1");
      assertNotNull(newUser);

      BufferedReader myReader = new BufferedReader(new FileReader(reg.getUserFile()));
      String currentLine = "";
      String firstLine = "";
      while ((currentLine = myReader.readLine()) != null){
        firstLine = currentLine;
        // break;
      }
      myReader.close();
      assertEquals(firstLine, String.valueOf(1) + ","+ "newUser@gmail.com" + ","+"NewPassword1");
    }
  }
  // @Test void testWriteToFileFails(){ //should still validate inside function or just outside?
  //   String username = "benjilala1@hotmail.com";
  //   String pwd = "Blahblahblah3";
  //   // reg.setUserFile(new File("src/main/datasets/UNKNOWN.csv"));
  //   reg.setUserFile("src/main/datasets/UNKNOWN.csv");

  //   reg.writeUserDetailsToFile(username, pwd);
  //   assertEquals(outContent.toString(), "FILE NOT FOUND ERROR: src/main/datasets/UNKNOWN.csv FILE NOT FOUND!");
  // }

  @Test void testCancelRegistration() throws IOException {
    String welcomeMsg = "\n*******************************************************\n" +
    "            Welcome to the registration page :)            \n" +
    "                   Sign up now FOR FREE!                  \n"+
    "*******************************************************\n";
    String optionMsg = "\n1. ENTER Y TO CONTINUE REGISTERING\n"+
    "2. ENTER N TO CANCEL AND GO BACK TO HOME PAGE\n" +
    "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~\n";
    // String yNOption = "Enter Y/N: ";
    String yNOption = "\nEnter option: ";

    String inputMessage = "No";
    String expectedOut = welcomeMsg + optionMsg + yNOption + "\n*******************************************************\n"+
    "REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1.."+
    "\n*******************************************************\n";

    // String expectedOut = welcomeMsg + optionMsg + yNOption + expected;
    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    reg.retrieveUserInputDetails();
    assertEquals(outContent.toString(), expectedOut);
  }

  //.NoSuchElementException: No line found --> ERROR MSG :((

  // @Test void testContinueRegistration(){ //go back to home page after
  //   String welcomeMsg = "\n*******************************************************\n" +
  //   "            Welcome to the registration page :)            \n" +
  //   "       Not a member with us yet? Sign up now FOR FREE!       \n" +
  //   "*******************************************************\n";
  //   String optionMsg = "\nPRESS Y TO CONTINUE REGISTERING"+
  //   " OR PRESS N TO CANCEL AND GO BACK TO HOME PAGE~\n";
  //   String yNOption = "Enter Y/N: ";

  //   String queries = "\nPlease enter your email: " +"\nPlease enter your password: ";
  //   String inputMessage = "Yes\n" + "barrytrotter@yahoo.com\n" +
  //                         "yerawizardBT3\n" + "1\n" +"2\n";
  //   ;

  //   String nextOption = "\nPlease select from the following: \n" +
  //                     "1. CONTINUE LOGGING IN\n"+
  //                     "2. CANCEL\n";


  //   String printMsg = "*****************************************\n" +
  //                     "       THANK YOU FOR SIGNING IN :)       \n"+
  //                     "*****************************************\n"+
  //                     "\nPlease select from the following: \n";

  //   String nextOption2 = "\n1. SETTINGS BUTTON for updating your details\n" + //what amber is working on
  //                         "2. DEFAULT HOME PAGE for filtering movies\n"+
  //                         "3. SIGN OUT BUTTON\n";

  //   String goHome = "\n*******************************************************\n" + 
  //   "Directing you to DEFAULT HOME page~ in 3..2..1..\n" + 
  //   "*******************************************************\n";

  //   String expectedOut = welcomeMsg + optionMsg + yNOption +queries + nextOption + printMsg + nextOption2 + goHome;

  //   ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
  //   System.setIn(in);
  //   reg.retrieveUserInputDetails();
  //   assertEquals(outContent.toString(), expectedOut);
  // }

  @Test void testNullUserFields(){
    String input = null;
    assertFalse(reg.validateUser(input)); //--Tim: Commenting out as it fails 
    assertFalse(reg.isValidPassword(input)); //--Tim: Commenting out as it fails
  }

  // public byte[] bigIntToByteArray(int num){
  //   return BigInteger.valueOf(num).toByteArray();
  // }

  // @Test void testValidNextOption(){ //testing 1. CONTINUE LOGGIN IN
  //   String optionMsg = "\nPlease select from the following: \n"+
  //   "1. CONTINUE LOGGING IN\n"+
  //   "2. CANCEL\n";

  //   String printMsg = "*****************************************\n" +
  //     "       THANK YOU FOR SIGNING IN :)       \n"+
  //     "*****************************************\n";

  //     String option1 = "\n*******************************************************\n" +
  //     "Directing you to SETTINGS page~ in 3..2..1..\n"+
  //     "*******************************************************\n";
  //   String expectedOut = optionMsg + printMsg +option1;
  //   // int inputMessage = 1;
  //   String inputMessage = "1";
  //   // ByteArrayInputStream in = new ByteArrayInputStream(bigIntToByteArray(inputMessage));
  //   ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

  //   System.setIn(in);
  //   reg.nextOption();
  //   assertEquals(outContent.toString(), expectedOut);
  // }

  // public static class IntegerAsker {
  //   Scanner scan;
  //   PrintStream out;

  //   public IntegerAsker(InputStream input, PrintStream output){
  //     scan = new Scanner(input);
  //   }

  //   public int askInput(String msg){
  //     out.println(msg);
  //     return scan.nextInt();
  //   }
  // }

  // @Test void getIntegerFromNextOption(){
  //   IntegerAsker intAsker = mock(IntegerAsker.class);
  //   when(intAsker.ask("String"))
  // }
  
  @Test void testFailCancelRegistration() throws IOException { //first wrong input then correct input
    String welcomeMsg = "\n*******************************************************\n" +
    "            Welcome to the registration page :)            \n" +
    "                   Sign up now FOR FREE!                  \n"+
    "*******************************************************\n";
    String optionMsg = "\n1. ENTER Y TO CONTINUE REGISTERING\n"+
    "2. ENTER N TO CANCEL AND GO BACK TO HOME PAGE\n" +
    "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~\n";
    // String yNOption = "Enter Y/N: ";
    String yNOption = "\nEnter option: ";

    String invalidInput = "\nInvalid input provided, please enter option again: \n";
        
    String inputMessage = "zzd\n" + "No";
    String expectedOut = welcomeMsg + optionMsg + yNOption + invalidInput + "\n*******************************************************\n"+
    "REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1.."+
    "\n*******************************************************\n";

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    reg.retrieveUserInputDetails();
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test void printValidWelcomeScreen(){
    // String welcomeMsg = "\n*******************************************************\n" +
    // "            Welcome to the registration page :)            \n" +
    // "       Not a member with us yet? Sign up now FOR FREE!       \n" +
    // "*******************************************************\n\n";
    String welcomeMsg = "\n*******************************************************\n" +
    "            Welcome to the registration page :)            \n" +
    "                   Sign up now FOR FREE!                  "+
    "\n*******************************************************\n\n";
    reg.printWelcome();
    assertEquals(outContent.toString(), welcomeMsg);
  }

  // @Test void chooseLoginOption() throws IOException{
  //   String welcomeMsg = "\n*******************************************************\n" +
  //   "            Welcome to the registration page :)            \n" +
  //   "                   Sign up now FOR FREE!                  \n"+
  //   "*******************************************************\n";
  //   String optionMsg = "\n1. ENTER Y TO CONTINUE REGISTERING\n"+
  //   "2. ENTER N TO CANCEL AND GO BACK TO HOME PAGE\n" +
  //   "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~\n";
    
  //   String yNOption = "\nEnter option: ";
    
  //   String username = "billy@yahoo.com\n";
  //   String password = "Helloworld123\n";
  //   String inputMessage = "Mem\n" + username; //+ password;
  //   String printLogin = "\n*******************************************************\n"+
  //   "            Welcome to the log in page :)            \n"+
  //   "*******************************************************\n";

  //   String prompt1 = "\nPlease enter your username: ";
  //   String prompt2 = "\nPlease enter your password: ";
  //   String welcomeB = "\nWelcome back " + username + "!\n";

  //   String expectedOut = welcomeMsg + optionMsg + yNOption + printLogin + prompt1 + prompt2 + welcomeB;
  //   ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
  //   System.setIn(in);
  //   reg.retrieveUserInputDetails();
  //   assertEquals(outContent.toString(), expectedOut);
  // }
}
