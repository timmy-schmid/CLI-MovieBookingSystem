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

class RegistrationTest {
  Registration reg;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;
  private HomeScreen home; 

  @BeforeAll static void setPath() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach
  public void setUp() {
    home = new HomeScreen(null);
    reg = new Registration(home);
    Registration.setUserFile("newUserDetailsTest2.csv");
    // reg.setUserFile("src/test/resources/newUserDetailsTest2.csv");

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

  @Test void testValidPhoneNumber(){
    String input = "0412279198";
    assertTrue(reg.checkValidPhoneNumber(input));
  }

   @Test void testValidGiftCardNumber(){
    String input = "11111111111116GC";
    assertTrue(reg.checkGiftCardNumber(input));
  }

  // @Test void testUserFileNotFound(){
  //   reg.setUserFile("src/main/datasets/UNKNOWN.csv");
  //   int result = reg.checkIfUserExists3("username@gmail.com", "1000000000");
  //   // int result = reg.checkIfUserExists("username@gmail.com");
  //   assert(result == -2);
  // }
  //testing writing to file works
  @Test void testValidReadingFile(){ //user not found in csv file 
    int result = reg.checkIfUserExists3("username@gmail.com", "04111211113");
    assert(result == 1);
  }

  @Test void nullUserFile(){
    Registration reg = new Registration(home);
    reg.setUserFile(null);
    assertNull(Registration.getUserFile());
  }
  @Test void testValidReadingFile2(){ //user already exists
    int result = reg.checkIfUserExists3("anna@yahoo.com", "0412345881");
    assert(result == -1);
  }

  //testing writing to file
  @Test void testWriteToFileWorks() throws IOException{ //should still validate inside function or just outside?
    String username = "benjilala@hotmail.com";
    String pwd = "Blahblahblah3";

    if (reg.checkIfUserExists3(username, "0404040123") != -1){ //if user doesn't exist
      reg.writeUserDetailsToFile3("benji", username,"0404040123", pwd);

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
      assertEquals(lastLine, String.valueOf(id) + ",benji,"+ username + ",0404040123,"+pwd+",F");
    }
  }
  @Test void testCreateAccountFails() throws IOException{
    User newUser = reg.createAccount3(null, null, null, "NewPassword1");
    assertNull(newUser);
  }

  @Test void testCreateAccountFails2() throws IOException{
    User newUser = reg.createAccount3(null, "hello@gmail.com", "", "NewPassword1");
    assertNull(newUser);
  }

  @Test void testCreateAccountWorks() throws IOException{
    int result = reg.checkIfUserExists3("newUser@gmail.com", "0404189234");

    if (result == 1){
      // User newUser = reg.createAccount("newUser@gmail.com", "NewPassword1");
      User newUser = reg.createAccount3("newUser", "newUser@gmail.com", "0404189234",
      "NewPassword1");

      assertNotNull(newUser);

      BufferedReader myReader = new BufferedReader(new FileReader(reg.getUserFile()));
      String currentLine = "";
      String lastLine = "";
      while ((currentLine = myReader.readLine()) != null){
        lastLine = currentLine;
        // break;
      }
      myReader.close();
        int id = id = Integer.parseInt(lastLine.split(",")[0]);

      // assertEquals(lastLine, String.valueOf(5) + ","+ "newUser@gmail.com" + ","+"NewPassword1");
      assertEquals(lastLine, String.valueOf(id) + ",newUser,newUser@gmail.com,0404189234,NewPassword1,F");

    } else {
      assert(result == -1);
    }
  }

  // @Test void testCreateAccountWorks2() throws IOException{
  //   reg.setUserFile("src/test/resources/newUserTest.csv");
  //   int result = reg.checkIfUserExists("newUser@gmail.com");
  //   if (result == -1){ //exists alrdy
  //     return;
  //   } else {
  //     User newUser = reg.createAccount("newUser@gmail.com", "NewPassword1");
  //     assertNotNull(newUser);

  //     BufferedReader myReader = new BufferedReader(new FileReader(reg.getUserFile()));
  //     String currentLine = "";
  //     String firstLine = "";
  //     while ((currentLine = myReader.readLine()) != null){
  //       firstLine = currentLine;
  //       // break;
  //     }
  //     myReader.close();
  //     assertEquals(firstLine, String.valueOf(1) + ","+ "newUser@gmail.com" + ","+"NewPassword1");
  //   }
  // @Test void testWriteToFileFails(){ //should still validate inside function or just outside?
  //   String username = "benjilala1@hotmail.com";
  //   String pwd = "Blahblahblah3";
  //   reg.setUserFile("src/test/resources/UNKNOWN.csv");

  //   reg.writeUserDetailsToFile3("benji", username, "0401234400", pwd);
  //   assertEquals(outContent.toString(), "FILE NOT FOUND ERROR: " + reg.getUserCsvFile() +"!\n");
  // }

  @Test void testCancelRegistration() throws IOException {
    String welcomeMsg = "\033[H\033[2J\n*******************************************************\n" +
    "            Welcome to the registration page :)            \n" +
    "                   Sign up now FOR FREE!                  \n"+
    "*******************************************************\n";
    String optionMsg = "\n1. ENTER Y TO CONTINUE REGISTERING\n"+
    "2. ENTER N TO CANCEL AND GO BACK\n" +
    "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~\n";
    String Option = "\nEnter option: ";

    String inputMessage = "No";
    String expectedOut = welcomeMsg + optionMsg + Option + "\n*******************************************************\n"+
    "REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1.."+
    "\n*******************************************************\n";

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    reg.retrieveUserInputDetails3();

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

  @Test void testValidNextOption(){ //testing 1. CONTINUE LOGGIN IN
    String optionMsg = "\nPlease select from the following: \n"+
    "1. CONTINUE LOGGING IN\n"+
    "2. CANCEL\n";

    String printMsg = "*****************************************\n" +
      "       THANK YOU FOR SIGNING IN :)       \n"+
      "*****************************************\n" + 
      "PLEASE ENTER 1 TO GO TO DEFAULT HOME PAGE\n";

      String option1 = "\n*******************************************************\n" +
      "Directing you to DEFAULT HOME page~ in 3..2..1..\n"+
      "*******************************************************\n";
    String expectedOut = optionMsg + printMsg +option1;
    String inputMessage = "1\n1";
   
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);
    reg.nextOption();
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test void testValidNextOption2(){ //testing 2. CANCEL
    String optionMsg = "\nPlease select from the following: \n"+
    "1. CONTINUE LOGGING IN\n"+
    "2. CANCEL\n";

      String printMsg = "*******************************************************\n" +
      "REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1..\nSEE YOU NEXT TIME! :)\n"+
      "*******************************************************\n";
    String expectedOut = optionMsg+printMsg;
    String inputMessage = "2\n";
   
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);
    reg.nextOption();
    assertEquals(outContent.toString(), expectedOut);
  }

  @Test void testNotValidNextOption(){ //testing 1. CONTINUE LOGGIN IN, then incorrect command, then valid
    String optionMsg = "\nPlease select from the following: \n"+
    "1. CONTINUE LOGGING IN\n"+
    "2. CANCEL\n";

    String printMsg = "*****************************************\n" +
      "       THANK YOU FOR SIGNING IN :)       \n"+
      "*****************************************\n" + 
      "PLEASE ENTER 1 TO GO TO DEFAULT HOME PAGE\n";

      String option1 = "\n*******************************************************\n"+ "OH NO, please enter a valid command" +
      "\n*******************************************************\n"+"\n*******************************************************\n" +
      "Directing you to DEFAULT HOME page~ in 3..2..1..\n"+
      "*******************************************************\n";
    String expectedOut = optionMsg + printMsg +option1;
    String inputMessage = "1\n3\n1";
   
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

    System.setIn(in);
    reg.nextOption();
    assertEquals(outContent.toString(), expectedOut);
  }
  
  @Test void testFailCancelRegistration() throws IOException { //first wrong input then correct input
    String welcomeMsg = "\033[H\033[2J\n*******************************************************\n" +
    "            Welcome to the registration page :)            \n" +
    "                   Sign up now FOR FREE!                  \n"+
    "*******************************************************\n";
    String optionMsg = "\n1. ENTER Y TO CONTINUE REGISTERING\n"+
    "2. ENTER N TO CANCEL AND GO BACK\n" +
    "3. ALREADY A MEMBER WITH US? ENTER M TO LOGIN~\n";
    String yNOption = "\nEnter option: ";

    String invalidInput = "\nInvalid input provided, please enter option again: \n";
        
    String inputMessage = "zzd\n" + "No";
    String expectedOut = welcomeMsg + optionMsg + yNOption + invalidInput + "\n*******************************************************\n"+
    "REDIRECTING YOU BACK TO HOME PAGE~ in 3..2..1.."+
    "\n*******************************************************\n";

    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    reg.retrieveUserInputDetails3();

    assertEquals(outContent.toString(), expectedOut);
  }

  @Test void printValidWelcomeScreen(){
    String welcomeMsg = "\033[H\033[2J\n*******************************************************\n" +
    "            Welcome to the registration page :)            \n" +
    "                   Sign up now FOR FREE!                  "+
    "\n*******************************************************\n\n";
    reg.printWelcome();
    assertEquals(outContent.toString(), welcomeMsg);
  }

  //test fails because password cant be captured here: nosuchelementexception 
  // @Test void testContinueRegistration() throws IOException { //go back to home page after
  //   String welcomeMsg = "\n*******************************************************\n" +
  //   "            Welcome to the registration page :)            \n" +
  //   "       Not a member with us yet? Sign up now FOR FREE!       \n" +
  //   "*******************************************************\n";
  //   String optionMsg = "\nPRESS Y TO CONTINUE REGISTERING"+
  //   " OR PRESS N TO CANCEL AND GO BACK TO HOME PAGE~\n";
  //   String yNOption = "Enter Y/N: ";

  //   String queries = "\nPlease enter a nickname: "+"\nPlease enter your email: " +"\nPlease enter your phone number: "+"\nPlease enter your password: ";
  //   String inputMessage = "Yes\n" + "barry\n"+"barrytrotter@yahoo.com\n" +
  //                         "0452211865\n"+"yerawizardBT3\n" + "1\n" +"1\n";
  //   ;

  //   String nextOption = "\nPlease select from the following: \n" +
  //                     "1. CONTINUE LOGGING IN\n"+
  //                     "2. CANCEL\n";


  //   String printMsg = "*****************************************\n" +
  //                     "       THANK YOU FOR SIGNING IN :)       \n"+
  //                     "*****************************************\n"+
  //                     "\nPLEASE ENTER 1 TO GO TO DEFAULT HOME PAGE\n";

  //   // String nextOption2 = "\n1. SETTINGS BUTTON for updating your details\n" + //what amber is working on
  //   //                       "2. DEFAULT HOME PAGE for filtering movies\n"+
  //   //                       "3. SIGN OUT BUTTON\n";

  //   String goHome = "\n*******************************************************\n" + 
  //   "Directing you to DEFAULT HOME page~ in 3..2..1..\n" + 
  //   "*******************************************************\n";

  //   String expectedOut = welcomeMsg + optionMsg + yNOption +queries + nextOption + printMsg + goHome;

  //   ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
  //   System.setIn(in);
  //   reg.retrieveUserInputDetails3();
  //   assertEquals(outContent.toString(), expectedOut);
  // }


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
  //   String password = "Helloworld123";
  //   String inputMessage = "Mem\n" + username + password;
  //   String printLogin = "\n*******************************************************\n"+
  //   "            Welcome to the log in page :)            \n"+
  //   "*******************************************************\n";

  //   String prompt1 = "\nPlease enter your username: ";
  //   String prompt2 = "\nPlease enter your password: ";
  //   String welcomeB = "\nWelcome back " + username + "!\n";

  //   String expectedOut = welcomeMsg + optionMsg + yNOption + printLogin + prompt1 + prompt2 + welcomeB;

  //   ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  //   System.setOut(new PrintStream(outContent));

  //   ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());

  //   System.setIn(in);
  //   reg.retrieveUserInputDetails();
  //   assertEquals(outContent.toString(), expectedOut);
  // }
}
