package R18_G2_ASM2;

public enum Screen {
  GOLD(2,35),
  SILVER(1.5,80),
  BRONZE(1,150);

  private final double multiplier;
  private final int capacity;

  private Screen(double multiplier, int capacity) {
    this.multiplier = multiplier;
    this.capacity = capacity;
  }

  public double getMultiplier () {
    return multiplier;
  }

  public int getCapacity() {
    return capacity;
  }
}