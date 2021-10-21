
package R18_G2_ASM2;

import java.util.LinkedHashMap;
import java.util.List;

import R18_G2_ASM2.UserFields;

public class User extends UserFields {

  private int ID;
  private String email; //used to represent the unique username
  private String password;
  private LinkedHashMap<Movie,String> filterMovie;
  private LinkedHashMap<Person,Integer> ticket = new LinkedHashMap<>();
  private String ticketMessage = "";
  private double totalPriceMutiplier = 0;

  public User(int ID, String email, String password){
    this.ID = ID;
    this.email = email;
    this.password = password;
    ticket.put(Person.Child,0);
    ticket.put(Person.Student,0);
    ticket.put(Person.Senior,0);
    ticket.put(Person.Adult,0);
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
    if (this.validateUser(email) == true){
      this.email = email;
    }
  }

  //only set password if its valid
  public void setPassword(String newPassword){
    if (this.isValidPassword(newPassword) == true){
      this.password = newPassword;
    }
  }

  //rename maybe bookingTicket --> bookTicket? OR nahh
  public void bookingTicket(Person person, int num){
    ticket.replace(person,ticket.get(person)+num);
  }

  public void AddTicketMessage(){
    for(Person key: ticket.keySet()){
      ticketMessage = (ticketMessage+"--"+key+"----"+Integer.toString(ticket.get(key))+"\n");
    }
  }
  public void totalPrice(){
    totalPriceMutiplier = 0;
    for(Person key: ticket.keySet()){
      totalPriceMutiplier += key.getValue()*ticket.get(key);
    }
  }
  public double getTotalPrice(){
    return this.totalPriceMutiplier;
  }

  public String getTicketMessage(){
    return this.ticketMessage;
  }
}