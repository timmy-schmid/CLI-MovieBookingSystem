package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;

public class GiftCardTest {
  GiftCard validCard;
  GiftCard invalidCard;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;    

  @BeforeEach
  public void setUp() {
    validCard = new GiftCard("1111111111111111", true); //redeemable
    invalidCard = new GiftCard("1111111111111112", false); //redeemable
  }

  @AfterEach
  public void tearDown(){ 
    validCard = null;
    //restoreStreams
    invalidCard = null;
    System.setOut(originalOutput);
  }

  @Test void testNotNullGiftCards(){
    assertNotNull(validCard);
    assertNotNull(invalidCard);
  }

  @Test void testValidStatusChange(){
    boolean expected = false;
    validCard.setStatus(false);

    assertEquals(expected, validCard.getStatus());
  }

  @Test void testCanGetCardNumber(){
    String expected = "1111111111111112";
    assertEquals(expected, invalidCard.getCardNumber());
  }
}
