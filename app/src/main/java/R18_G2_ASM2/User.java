
package R18_G2_ASM2;

import java.util.LinkedHashMap;
import java.util.List;

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
  public void setID(int ID){
    this.ID = ID;
  }
  public void setEmail(String email){
    this.email = email;
  }
  public void setPassword(String Password){
    this.password = password;
  }
}