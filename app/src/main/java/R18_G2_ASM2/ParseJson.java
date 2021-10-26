package R18_G2_ASM2;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ParseJson {
  private HashMap<String, String> creditCards = new HashMap<>();
  private File creditCardsFile;
  private static String CREDITS_CARDS_FILE_NAME = "credit_cards.json";

  public ParseJson() {
    creditCardsFile = DataController.accessJSONFile(CREDITS_CARDS_FILE_NAME);
  }

  public void retrieveCardDetails() throws Exception{
    JSONArray a = (JSONArray) new JSONParser().parse(new FileReader(creditCardsFile));
    for (Object o : a) {
      JSONObject person = (JSONObject) o;
      String name = (String) person.get("name");
//      System.out.println(name);
      String number = (String) person.get("number");
//      System.out.println(number);
      creditCards.put(name, number);
    }
    System.out.println(creditCards);
  }

  public boolean matchCard(String name, String number) {
    for (String s : creditCards.keySet()) {
//      System.out.println("key: " + s + " value: " + creditCards.get(s));
      if (s.equals(name)) {
        if ((creditCards.get(s)).equals(number)) {
          return true;
        }
      }
    }
    return false;
  }

  public HashMap<String, String> getCreditCards() {
    return creditCards;
  }

  public void setCreditCards(HashMap<String, String> creditCards) {
    this.creditCards = creditCards;
  }

  public File getCreditCardsFile() {
    return creditCardsFile;
  }

  public static String getCreditsCardsFileName() {
    return CREDITS_CARDS_FILE_NAME;
  }

  public void setCreditCardsFile(File creditCardsFile) {
    this.creditCardsFile = creditCardsFile;
  }

  public static void setCreditsCardsFileName(String creditsCardsFileName) {
    CREDITS_CARDS_FILE_NAME = creditsCardsFileName;
  }

  //  # add into corresponding file
//  ParseJson t = new ParseJson();
//        if (t.matchCard("Julie", "5690")) {
//    System.out.println("Match!");
//  } else {
//    System.out.println("Not Match!");
//  };
}