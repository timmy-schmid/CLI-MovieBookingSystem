package R18_G2_ASM2;

public class Staff extends User {

  public Staff(int ID, String nickname, String email, String phoneNumber, String password) {
    super(ID, nickname, email, phoneNumber, password);
    this.userType = UserType.STAFF;
  }

}
