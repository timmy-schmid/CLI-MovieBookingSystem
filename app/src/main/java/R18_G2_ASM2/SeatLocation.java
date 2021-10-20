package R18_G2_ASM2;

public enum SeatLocation {
  FRONT(0.8), MIDDLE(1), REAR(0.8);
  private double value;
  private SeatLocation(double value){
    this.value =  value;
  }
  public double getValue(){
    return this.value;
  }
}
