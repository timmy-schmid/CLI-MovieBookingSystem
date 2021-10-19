
package R18_G2_ASM2;

import java.util.LinkedHashMap;
import java.util.List;

import R18_G2_ASM2.Registration;

public class User {

  private int ID;
  private String email; //used to represent the unique username
  private String password;
  private LinkedHashMap<Movie,String> filterMovie;

  //acts as a user settings? --> modify existing details of a customer

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

  //setter methods: e.g. for changing login details ...
  //validate to ensure values to be set to are valid
  public void setID(int ID){
    if (ID >= 0){
      this.ID = ID;
    }
  }

  //only set email if its valid
  public void setEmail(String email){
    if (new Registration().validateUser(email) == true){
      this.email = email;
    }
  }

  //only set password if its valid
  public void setPassword(String newPassword){
    if (new Registration().isValidPassword(newPassword) == true){
      this.password = newPassword;
    }
  }
}