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

class LoginTest {
  Login login;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOutput = System.out;

  @BeforeEach
  public void setUp() {
    login = new Login();
    login.setUserFile(new File("app/src/test/resources/userTest.csv"));

    //set up streams
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
    Login login = new Login();
    assertNotNull(login);
  }

  @Test
  public void testValidUsername(){
    Login login = new Login();
    String username = "dannie@gmail.com";
    String password = "Asdf1234!*";
    int result = login.checkIfUserExists(username, password);
    assertEquals(result, 1);
  }

  @Test
  public void testInvalidUsername(){
    Login login = new Login();
    String username = "dann@gmail.com";
    String password = "Asdf1234!*";
    int result = login.checkIfUserExists(username, password);
    assertEquals(result, -1);
  }

  @Test
  public void testValidPassword(){
    Login login = new Login();
    String username = "harrypotter@gmail.com";
    String password = "Asdf1235!*";
    int result = login.checkIfUserExists(username, password);
    assertEquals(result, 1);
  }

  @Test
  public void testInvalidPassword(){
    Login login = new Login();
    String username = "harrypotter@gmail.com";
    String password = "Asdf1235!";
    int result = login.checkIfUserExists(username, password);
    assertEquals(result, -1);
  }

  @Test
  public void testPrintScreen(){
    Login login = new Login();
    String expected = "\n*******************************************************\n" +
        "            Welcome to the log in page :)            \n" +
        "*******************************************************\n";
    login.printScreen();
    assertEquals(outContent.toString(), expected);
  }

  @Test
  public void testNextOption() throws Exception{
    Login login = new Login();
    String inputMessage = "1";
    String expected = "\nInvalid username or password, please select from the following:\n" +
        "1. CONTINUE LOGGING IN\n" +
        "2. FORGOT MY LOGIN DETAILS\n" +
        "3. CANCEL\n";
    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    login.nextOption();
    assertEquals(outContent.toString(), expected);
  }
}
