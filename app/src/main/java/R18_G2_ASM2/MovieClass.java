package R18_G2_ASM2;

public enum MovieClass {
  GOLD(2,42),
  SILVER(1.5,80),
  BRONZE(1,150);

  private final double multiplier;
  private final int capacity;

  private MovieClass(double multiplier, int capacity) {
    this.multiplier = multiplier;
    this.capacity = capacity;
  }

  public static MovieClass parseScreen(String s) {
    
    for (MovieClass screen : MovieClass.values()) {
      if (screen.name().equals(s.toUpperCase())) {
        return screen;
      }
    }
    throw new InvalidScreenException(s + " is an Invalid screen type.");
  }

  public double getMultiplier () {
    return multiplier;
  }

  public int getCapacity() {
    return capacity;
  }
}
