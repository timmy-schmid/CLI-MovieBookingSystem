package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

class RegistrationTest {
  Registration reg;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;
  private MovieSystem mSystem;
  private static String USER_TESTFILE = "newUserDetailsTest.csv";
  private static File testFile;
  private File userFile;

  @BeforeAll static void setPath() {
    DataController.setBasePath("src/test/resources/");
    try {
      testFile = DataController.accessCSVFile(USER_TESTFILE);
    } catch (FileNotFoundException e) {
      assertEquals(true,false);
    }
  }

  @BeforeEach
  public void setUp() {
    mSystem = new MovieSystem();
    reg = new Registration(mSystem);
    try {
      userFile = DataController.accessCSVFile(USER_TESTFILE);
    } catch (FileNotFoundException e) {
      assertEquals(true,false);
    }    
    
    //set up streams
    Registration.setUserFile(USER_TESTFILE);
    System.setOut(new PrintStream(outContent));
  }
  @AfterEach
  public void tearDown(){ 
    reg = null;
    mSystem = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test void RegNotNull(){
      assertNotNull(reg);
  }

  @Test void testInvalidUsername(){ //missing @ + .com
    String input = "lala@hotmail";
    assertFalse(User.validateUser(input));
  }

  @Test void testInvalidUsername1(){ //""- no input
    String input = "";
    assertFalse(User.validateUser(input));
  }
  @Test void testInvalidUsername2(){ //""- no letters before@
    String input = " @gmail.com";
    assertFalse(User.validateUser(input));
  }
  @Test void testInvalidUsername3(){ //""- no letters before@
    String input = " @.com";
    assertFalse(User.validateUser(input));
  }
  @Test void testInvalidUsername4(){ //""- no letters before@
    String input = "@gmail.com";
    assertFalse(User.validateUser(input));
  }

  @Test void testInvalidUsername5(){
    String input = "hola@bob.com";
    assertFalse(User.validateUser(input));
  }

  @Test void testValidUsername(){
    String input = "hola@gmail.com";
    assertTrue(User.validateUser(input));
  }

  @Test void testValidUsername2(){ //alpha-numeric
    String input = "hoLLie241@hotmail.com";
    assertTrue(User.validateUser(input));
  }

  @Test void testInvalidPassword(){ //too short
    String input = "abc234X!";
    assertFalse(User.isValidPassword(input));
  }

  @Test void testInvalidPassword2(){ //no >= 1 cap
    String input = "abc234xx#!";
    assertFalse(User.isValidPassword(input));
  }

  @Test void testInvalidPassword3(){
    String input = "";
    assertFalse(User.isValidPassword(input));
  }

  @Test void testValidPassword(){
    String input = "Abcd1244#1";
    assertTrue(User.isValidPassword(input));
  }

  @Test void testValidPhoneNumber(){
    String input = "0412279198";
    assertTrue(User.isValidPhoneNumber(input));
  }

   @Test void testValidGiftCardNumber(){
    String input = "11111111111116GC";
    assertTrue(User.isValidGiftCardNumber(input));
  }

  // @Test void testUserFileNotFound(){
  //   reg.setUserFile("src/main/datasets/UNKNOWN.csv");
  //   int result = reg.checkIfUserExists3("username@gmail.com", "1000000000");
  //   // int result = reg.checkIfUserExists("username@gmail.com");
  //   assert(result == -2);
  // }
  //testing writing to file works

  @Test void testValidReadingFile(){ //user not found in csv file 
    int result = -1;
    try {
      result = User.doesUserExistInCSV(USER_TESTFILE,"username@gmail.com", "04111211113");
    } catch (FileNotFoundException e) {
      assertEquals(true,false);
    }
    assert(result == 0);
  }

  @Test void nullUserFile(){
    Registration.setUserFile(null);
    assertNull(Registration.getUserFile());
  }
  
  @Test void testValidReadingFile2(){ //user already exists
    int result = -1;
    try {
      result = User.doesUserExistInCSV(USER_TESTFILE,"anna@yahoo.com", "0412345881");
    } catch (FileNotFoundException e) {
      assertEquals(true,false);
    }
    assert(result == 1);
  }

  //testing writing to file
  @Test void testWriteToFileWorks() throws IOException{ //should still validate inside function or just outside?
    
    String nickname = "benji";
    String username = "benjilala@hotmail.com";
    String pwd = "Blahblahblah3";
    String phoneNum = "0404040123";

    int newId = User.getLastUserIDFromCSV(USER_TESTFILE);
    Customer c = new Customer(newId, nickname, username,phoneNum, pwd);

    if (User.doesUserExistInCSV(USER_TESTFILE,username, phoneNum) != 0){ //if user doesn't exist
      c.writeNewUserToCSV(USER_TESTFILE);

    //retrieve last line and compare
      String currentLine = "";
      String lastLine = "";
      
      //after writing to file, read the last line and check it matches given details
      BufferedReader myReader = new BufferedReader(new FileReader(testFile));
      while ((currentLine = myReader.readLine()) != null){
        lastLine = currentLine;
      }
      myReader.close();
      assertEquals(lastLine, String.valueOf(newId) + ",benji,"+ username + ",0404040123,"+pwd+",F" + ",CUSTOMER");
    }
  }

  @Test void testCreateAccountFails() throws IOException{
    Customer newUser = reg.createCustomer(null, null, null, "NewPassword1");
    assertNull(newUser);
  }

  @Test void testCreateAccountFails2() throws IOException{
    Customer newUser = reg.createCustomer(null, "hello@gmail.com", "", "NewPassword1");
    assertNull(newUser);
  }

  @Test void testCreateAccountWorks() throws IOException{
    int result = User.doesUserExistInCSV(USER_TESTFILE,"newUser@gmail.com", "0404189234");

    if (result == 0){
      // User newUser = reg.createAccount("newUser@gmail.com", "NewPassword1");

      int newId = Customer.getLastUserIDFromCSV(USER_TESTFILE);
      Customer newUser = reg.createCustomer("newUser", "newUser@gmail.com", "0404189234",
      "NewPassword1,CUSTOMER");

      assertNotNull(newUser);

      BufferedReader myReader = new BufferedReader(new FileReader(testFile));
      String currentLine = "";
      String lastLine = "";
      while ((currentLine = myReader.readLine()) != null){
        lastLine = currentLine;
        // break;
      }
      myReader.close();
      assertEquals(lastLine, String.valueOf(newId) + ",newUser,newUser@gmail.com,0404189234,NewPassword1,F");
    } else {
      assert(result == 1);
    }
  }

  @Test void testCreateAccountWorks2() throws IOException {
    int result = User.doesUserExistInCSV(USER_TESTFILE,"newUser2@gmail.com", "0414189238");

    if (result == 1){ //exists alrdy
      String msg = "Email is taken already/exists in system. Please re-enter your details.\n";

        // ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
        // System.setIn(in);
        // reg.retrieveUserInputDetails();
      //assertEquals(outContent.toString(), msg);

      //assertEquals(outContent.toString(), msg);
    } else {
      int lastID = User.getLastUserIDFromCSV(USER_TESTFILE);
      User newUser = reg.createCustomer("newUser2", "newUser2@gmail.com", "0414189238",
      "NewPassword2,CUSTOMER");
      assertNotNull(newUser);

      BufferedReader myReader = new BufferedReader(new FileReader(testFile));
      String currentLine = "";
      String firstLine = "";
      while ((currentLine = myReader.readLine()) != null){
        firstLine = currentLine;
        // break;
      }
      myReader.close();
      assertEquals(firstLine, String.valueOf(lastID) + ",newUser2,newUser2@gmail.com,0414189238,NewPassword2,F");
    }
  }

  // @Test void testWriteToFileFails(){ //should still validate inside function or just outside?

  //   String username = "benjilala1@hotmail.com";
  //   String pwd = "Blahblahblah3";
  //   userFile = DataController.accessCSVFile("UNKNOWN.csv");    
  //   reg.setUserFile("src/test/resources/UNKNOWN.csv");

  //   reg.writeUserDetailsToFile3("benji", username, "0401234400", pwd);
  //   assertEquals(outContent.toString(), "FILE NOT FOUND ERROR: " + reg.getUserFile() +"!\n");
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
    assertFalse(User.validateUser(input)); //--Tim: Commenting out as it fails 
    assertFalse(User.isValidPassword(input)); //--Tim: Commenting out as it fails
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
    reg.retrieveUserInputDetails();

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
