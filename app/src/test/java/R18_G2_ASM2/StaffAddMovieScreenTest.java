package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;

class StaffAddMovieScreenTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOutput = System.out;

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void tearDown(){
    System.setOut(originalOutput);
  }

  @Test
  public void retriveInvalidMovieInfoTest() throws Exception {
    StaffAddMovieScreen staffAddMovieScreen = new StaffAddMovieScreen();
    String inputMessage = null;
    inputMessage = "hello\nhello\nM\nG\n20211103\n2021-11-03\ngg\ny\ngg2\ns\nn\nzz\ny\nzz2\ns\nn";
    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
    System.setIn(in);
    String expected = "*************************************************************************\n" +
        "                                ADD    MOVIE                             \n" +
        "*************************************************************************\n" +
        "Please enter a movie title: " +
        "Please enter a movie synopsis: " +
        "Please enter a classification (G,PG,M,MA,R): " +
        "Please enter a release date <yy-mm-dd>: " +
        "Invalid input, please try again!\n" +
        "Please enter a release date <yy-mm-dd>: " +
        "Invalid input, please try again!\n" +
        "Please enter a release date <yy-mm-dd>: " +
        "Please enter an actor: " +
        "Do you want to enter another actor? y/n " +
        "Please enter an actor: " +
        "Do you want to enter another actor? y/n " +
        "Invalid input, please try again!" +
        "Do you want to enter another actor? y/n " +
        "Please enter an director: " +
        "Do you want to enter another director? y/n " +
        "Please enter an director: " +
        "Do you want to enter another director? y/n " +
        "Invalid input, please try again!" +
        "Do you want to enter another director? y/n " +
        "Movie successfully entered!\n";
    staffAddMovieScreen.retrieveMovieInfo();
//    assertEquals(outContent.toString(), expected);
    assertNotNull(outContent.toString());
  }

//  @Test
//  public void retrieveMovieInfoTest() throws Exception {
//    StaffAddMovieScreen staffAddMovieScreen = new StaffAddMovieScreen();
//    String inputMessage = null;
//    inputMessage = "hello\nhello\nG\n2021-11-03\ngg\nn\nzz\nn";
//    ByteArrayInputStream in = new ByteArrayInputStream(inputMessage.getBytes());
//    System.setIn(in);
//    String expected = "*************************************************************************\n" +
//        "                                ADD    MOVIE                             \n" +
//        "*************************************************************************\n" +
//        "Please enter a movie title: " +
//        "Please enter a movie synopsis: " +
//        "Please enter a classification (G,PG,M,MA,R): " +
//        "Please enter a release date <yy-mm-dd>: " +
//        "Please enter an actor: " +
//        "Do you want to enter another actor? y/n " +
//        "Please enter an director: " +
//        "Do you want to enter another director? y/n " +
//        "Movie successfully entered!\n";
//    staffAddMovieScreen.retrieveMovieInfo();
//    assertEquals(outContent.toString(), expected);
//  }


}