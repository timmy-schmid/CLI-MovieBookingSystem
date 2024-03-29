package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;

class LoginTest {

  Login login;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOutput = System.out;
  private MovieSystem movSystem = new MovieSystem();

  @BeforeAll public static void setPath() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach
  public void setUp() {
    Login.setUserFile("newUserDetailsTest.csv");
    login = new Login(movSystem);

    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void tearDown(){
    login = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test
  public void LoginNotNull(){
    Login login = new Login(movSystem);
    assertNotNull(login);
  }

   @Test
   public void testValidUsername(){
     String username = "hhh@gmail.com";
     String password = "Asdf1234!*";
     // int result = login.checkIfUserExists(username, password);
     int result = login.checkIfUserExists2(username, password);
     assertEquals(result, 1); //1 = user exists  (match found)
   }

   @Test
   public void testInvalidUsername(){
     String username = "dann@gmail.com";
     String password = "Asdf1234!*";
     int result = login.checkIfUserExists2(username, password);
     assertEquals(result, -1); // no match found
   }

   @Test
   public void testValidPassword(){
     String username = "harrypotter@gmail.com";
     String password = "Wutttt123Asdf!*";
     int result = login.checkIfUserExists2(username, password);
     assertEquals(result, -1);
   }

   @Test
   public void testInvalidPassword(){
     String username = "harrypotter@gmail.com";
     String password = "Asdf1235!";
     int result = login.checkIfUserExists2(username, password);
     assertEquals(result, -1);
   }

  @Test
  public void testPrintScreen(){
    Login login = new Login(movSystem);
    String expected = "\033[H\033[2J\n*******************************************************\n" +
        "            Welcome to the log in page :)            \n" +
        "*******************************************************\n";
    login.printScreen();
    assertEquals(outContent.toString(), expected);
  }

  @Test
  public void testNextOption() throws Exception{
    Login login = new Login(movSystem);
    String inputMessage = "1";
    String expected = "\nInvalid username or password, please select from the following:\n" +
        "1. CONTINUE LOGGING IN\n" +
        "2. CANCEL\n";
    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    login.nextOption();
    assertEquals(outContent.toString(), expected);
  }

//  @Test
//  public void testUsernameAndPasswordInput() throws Exception{
//    Login login = new Login();
//    String inputMessage = "harrypotter@gmail.com\n" + "Asdf1235!*";
//    String expected = "Welcome harrypotter@gmail.com!";
//    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
//    System.setIn(in);
//    login.retrieveUserInputDetails();
//    assertEquals(outContent.toString(), expected);
//  }

}
