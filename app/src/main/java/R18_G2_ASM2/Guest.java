package R18_G2_ASM2;

public class Guest extends User {
  public Guest() {
    super(0, "Guest", "", "", "");
    this.userType = UserType.GUEST;
  }
}
