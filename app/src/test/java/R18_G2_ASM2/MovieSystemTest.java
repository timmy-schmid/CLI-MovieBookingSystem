package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class MovieSystemTest {

  private static ByteArrayInputStream mockIn;
  private static ByteArrayOutputStream actualOut;
  private static PrintStream actualOutPrint;

  MovieSystem  movSystem;

  @BeforeAll static void importData() {
    DataController.setBasePath("src/test/resources/movie.csv");
  }

  @BeforeEach void resetSystem() {
    actualOut = new ByteArrayOutputStream();
    actualOutPrint = new PrintStream(actualOut);

  }

  @Test void parseInputTestInvalidChars() {

    mockIn = new ByteArrayInputStream("b\n@\nt\nA\na\n".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);    

    assertEquals("a",movSystem.parseInput("a", 0));
    assertEquals(actualOut.toString(),"User Input:" + //original
                   "Invalid selection. Please try again.\n\n" + //b input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //@ input
                   "User Input:" +                  
                   "Invalid selection. Please try again.\n\n" + //t input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //A input
                   "User Input:");

              
  }

  @Test void parseInputTestInvalidInteger() {
    mockIn = new ByteArrayInputStream("-1\n-980\n100\n10 a\n10\n".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);

    assertEquals("10",movSystem.parseInput("a", 10)); // need to check if no input passed
    assertEquals(actualOut.toString(),"User Input:" + //original
                   "Invalid selection. Please try again.\n\n" + //-1 input
                   "User Input:" +
                   "Invalid selection. Please try again.\n\n" + //-980 input
                   "User Input:" + 
                   "Invalid selection. Please try again.\n\n" + //100 input
                   "User Input:" +                    
                   "Invalid selection. Please try again.\n\n" + //10a input
                   "User Input:");    
  }

  @Test void testQuit() {
    mockIn = new ByteArrayInputStream("SEE YOU NEXT TIME! :)".getBytes());
    movSystem = new MovieSystem(mockIn, actualOutPrint);
    movSystem.quit();
    assertEquals("SEE YOU NEXT TIME! :)",actualOut.toString()); 
  }
}
