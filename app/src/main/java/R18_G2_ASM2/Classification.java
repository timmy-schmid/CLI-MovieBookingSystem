package R18_G2_ASM2;

public enum Classification {
  G,PG,M,MA15,R18;


  public static Classification parseClassification(String s) {
    
    for (Classification c : Classification.values()) {
      if (c.name().equals(s)) {
        return c;
      }
    }
    throw new InvalidClassificationException(s + " cannot be converted to a valid screen type.");
  }
}
