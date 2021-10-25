package R18_G2_ASM2;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ParseJson {
  public ParseJson() throws Exception{
    JSONArray a = (JSONArray) new JSONParser().parse(new FileReader("src/main/datasets/credit_cards.json"));

    for (Object o : a)
    {
      JSONObject person = (JSONObject) o;

      String name = (String) person.get("name");
      System.out.println(name);

      String number = (String) person.get("number");
      System.out.println(number);
    }
  }
}