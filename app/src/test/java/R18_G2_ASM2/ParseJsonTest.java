package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;

class ParseJsonTest {

  ParseJson parseJson;

  @BeforeAll public static void setPath() {
    DataController.setBasePath("src/main/resources/");
  }

  @BeforeEach
  public void setUp() throws Exception{
    ParseJson.setCreditsCardsFileName("credit_cards.json");
    parseJson = new ParseJson();
  }

  @AfterEach
  public void tearDown(){
    parseJson = null;
  }

  @Test
  public void ParseJsonNotNull() throws Exception{
    assertNotNull(parseJson);
  }

  @Test
  public void testCreateCreditCardMap() throws Exception{
    parseJson.retrieveCardDetails();
    HashMap<String, String> creditCards = parseJson.getCreditCards();
    assertEquals(creditCards.size(), 48);
  }

  @Test
  public void testMatchValidCard1() throws Exception{
    parseJson.retrieveCardDetails();
    assertTrue(parseJson.matchCard("Charles", "40691"));
  }

  @Test
  public void testMatchValidCard2() throws Exception{
    parseJson.retrieveCardDetails();
    assertTrue(parseJson.matchCard("Christine", "35717"));
  }

  @Test
  public void testMatchValidCard3() throws Exception{
    parseJson.retrieveCardDetails();
    assertTrue(parseJson.matchCard("Debbie", "92090"));
  }

  @Test
  public void testMatchInvalidCardName() throws Exception{
    parseJson.retrieveCardDetails();
    assertFalse(parseJson.matchCard("Deie", "92090"));
  }

  @Test
  public void testMatchInvalidCardNumber() throws Exception{
    parseJson.retrieveCardDetails();
    assertFalse(parseJson.matchCard("Blake", "11190"));
  }

  @Test
  public void testMatchInvalidCardNameAndNumber() throws Exception{
    parseJson.retrieveCardDetails();
    assertFalse(parseJson.matchCard("Bl", "110"));
  }

  @Test
  public void testSetCreditCards() throws Exception{
    parseJson.retrieveCardDetails();
    assertEquals(parseJson.getCreditCards().size(), 48);
    HashMap<String, String> creditCards = new HashMap<String, String>();
    creditCards.put("SOFT", "2412");
    parseJson.setCreditCards(creditCards);
    assertEquals(parseJson.getCreditCards().size(), 1);
  }

  @Test
  public void testGetCreditCardsFile() throws Exception{
    assertEquals(parseJson.getCreditCardsFile(), DataController.accessJSONFile("credit_cards.json"));
  }

  @Test
  public void testGetCreditsCardsFileName() throws Exception{
    assertEquals(parseJson.getCreditsCardsFileName(), "credit_cards.json");
  }

}
