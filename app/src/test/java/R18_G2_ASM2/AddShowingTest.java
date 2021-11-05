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
import java.util.*;

public class AddShowingTest {
  private MovieSystem system;;
  private AddShowing as;
  private Staff staff;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;

  @BeforeAll static void setPath() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach
  public void setUp() throws IOException {
    staff = new Staff(14, "Jenkins", "newStaff1234@gmail.com","0498999162", "McJenkins1234!");
    system = new MovieSystem();
    system.setUser(staff);
    as = new AddShowing(system);
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void tearDown(){ 
    staff = null;
    system = null;
    as = null;
    System.setOut(originalOutput);
  }

  @Test 
  public void testNotNull(){
    assertNotNull(system);
    assertNotNull(as);
  }

  @Test 
  public void validInputRetrieved(){
    String msg = "User Input:";
    as.askforInput();
    assertEquals(outContent.toString(), msg);
  }

  @Test 
  public void invalidUserTest(){
    Customer userA = new Customer(6, "Anna", "anna@yahoo.com","0412345881", "Lalala1234");
    system.setUser(userA);
    as.askforInput();
    try {
      AddShowing as2 = new AddShowing(system);
    } catch  (IllegalArgumentException e){
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

}
