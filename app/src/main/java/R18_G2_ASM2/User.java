
package R18_G2_ASM2;

public class User {

  private int ID;
  private String email; //used to represent the unique username
  private String password;

  //add card details later? Subclass for customer or use current one?

  public User(int ID, String email, String password){
    this.ID = ID;
    this.email = email;
    this.password = password;
  }

  //getter methods below ~
  public int getID(){
    return this.ID;
  }
  public String getEmail(){
    return this.email;
  }
  public String getPassword(){
    return this.password;
  }
}