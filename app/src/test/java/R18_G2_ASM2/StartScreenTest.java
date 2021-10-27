package R18_G2_ASM2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class StartScreenTest {

  
  private static ByteArrayInputStream mockIn;
  private static ByteArrayOutputStream actualOut;
  private static HomeScreen mockHomeScreen;


  @BeforeAll static void setup() {
    mockHomeScreen = mock(HomeScreen.class);
    actualOut = new ByteArrayOutputStream();
    System.setOut(new PrintStream(actualOut));
  }
  
  @BeforeEach void resetSystem() {
    actualOut.reset(); 
  }
  
  @Test public void testConstructor() {


  }

  @Test public void testInvalidUserInputSimpleInvalidIntegers() {
    mockIn = new ByteArrayInputStream("0\n-1\n4\n1234567\n".getBytes());
    System.setIn(mockIn);

    StartScreen s = new StartScreen(mockHomeScreen);   

    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:" + //initial ask
                   "Invalid selection. Please try again.\n\n" + //0 input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //-1 input
                   "User Input:" +                  
                   "Invalid selection. Please try again.\n\n" + //4 input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //1234567 input
                   "User Input:");
  }

  @Test public void testInvalidUserInputSimpleInvalidChars() {
    mockIn = new ByteArrayInputStream("a\nb\n^\n\"\n".getBytes());
    System.setIn(mockIn);

    StartScreen s = new StartScreen(mockHomeScreen);   

    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:" + //initial ask
                   "Invalid selection. Please try again.\n\n" + //a input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //B input
                   "User Input:" +                  
                   "Invalid selection. Please try again.\n\n" + //^ input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //" input
                   "User Input:");
  }

  @Test public void testInvalidUserInputInvalidStrings() {
    mockIn = new ByteArrayInputStream("Quit\nquit\nQuitWithInt1\n1Quit".getBytes());
    System.setIn(mockIn);

    StartScreen s = new StartScreen(mockHomeScreen);   

    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:" + //initial ask
                   "Invalid selection. Please try again.\n\n" + //Quit input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //quit input
                   "User Input:" +                  
                   "Invalid selection. Please try again.\n\n" + //QuitWithInt1 input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //1Quit input
                   "User Input:");
  }

  @Test public void testInvalidUserInputInvalidComplexCases() {
    mockIn = new ByteArrayInputStream(" q\n 1\n no new line".getBytes());
    System.setIn(mockIn);

    StartScreen s = new StartScreen(mockHomeScreen);   

    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:" + //initial ask
                   "Invalid selection. Please try again.\n\n" + // (q with leading space)
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + // (1 with leading space)
                   "User Input:" +                  
                   "Invalid selection. Please try again.\n\n" + // no new line
                   "User Input:");     
  }

  @Test public void testValidInputBasicCases() {
    StartScreen s = new StartScreen(mockHomeScreen);

    mockIn = new ByteArrayInputStream("1\n".getBytes());
    System.setIn(mockIn);
    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:"); 
    actualOut.reset(); 
    
    s = new StartScreen(mockHomeScreen);
    mockIn = new ByteArrayInputStream("2\n".getBytes());
    System.setIn(mockIn);
    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:");
    actualOut.reset(); 

    mockIn = new ByteArrayInputStream("3\n".getBytes());
    System.setIn(mockIn);
    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:");
    actualOut.reset(); 

    mockIn = new ByteArrayInputStream("q\n".getBytes());
    System.setIn(mockIn);
    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:");
    actualOut.reset(); 

    mockIn = new ByteArrayInputStream("Q\n".getBytes());
    System.setIn(mockIn);
    s.askforInput();
    assertEquals(actualOut.toString(),"User Input:");   
  }






}
