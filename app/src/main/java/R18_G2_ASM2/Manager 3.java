package R18_G2_ASM2;

public class Manager extends User {

  public Manager(int ID, String nickname, String email, String phoneNumber, String password) {
    super(ID, nickname, email, phoneNumber, password);
    this.userType = UserType.MANAGER;
  }

}
