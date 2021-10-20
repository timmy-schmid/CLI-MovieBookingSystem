package R18_G2_ASM2;

public enum Screen {
  GOLD(2,42),
  SILVER(1.5,80),
  BRONZE(1,150);

  private final double multiplier;
  private final int capacity;

  private Screen(double multiplier, int capacity) {
    this.multiplier = multiplier;
    this.capacity = capacity;
  }

  public static Screen parseScreen(String s) {
    
    for (Screen screen : Screen.values()) {
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
