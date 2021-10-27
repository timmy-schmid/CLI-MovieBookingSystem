package R18_G2_ASM2;

public enum SeatLocation {
  FRONT(1), MIDDLE(1), REAR(1);
  private double value;
  private SeatLocation(double value){
    this.value =  value;
  }
  public double getValue(){
    return this.value;
  }
}
