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

}
