package R18_G2_ASM2;

public enum Screen {
  GOLD(2),
  SILVER(1.5),
  BRONZE(1);

  private final double multiplier;

  private Screen(double multiplier) {
    this.multiplier = multiplier;
  }

  public double getMultiplier () {
    return multiplier;
  }
}
