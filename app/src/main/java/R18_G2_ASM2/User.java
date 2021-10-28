
package R18_G2_ASM2;

import java.util.LinkedHashMap;
import java.util.List;

import R18_G2_ASM2.UserFields;


/*

ADD user status: save card details for next time

*/
public class User extends UserFields {

  private int ID;
  private String nickname;
  private String phoneNumber;
  private String email; //used to represent the unique username
  private String password;
  private LinkedHashMap<Movie,String> filterMovie;
  private LinkedHashMap<Person,Integer> ticket = new LinkedHashMap<>();
  private String ticketMessage = "";
  private double totalPriceMutiplier = 0;

  private boolean autoFill;
  private String cardName;
  private String cardNumber;
  private String cvvNumber;

  //current new version - sprint 2
  public User(int ID, String nickname, String email, String phoneNumber, String password){ //extra fields added
    this.ID = ID;
    this.nickname = nickname;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;

    ticket.put(Person.Child,0);
    ticket.put(Person.Student,0);
    ticket.put(Person.Senior,0);
    ticket.put(Person.Adult,0);

    this.cardName = null;
    this.cardNumber = null;
    this.cvvNumber = null;
    this.autoFill = false; //default, then prompt user during transaction stage to update
  }

  //getter methods below ~
  public int getID(){
    return this.ID;
  }

  public String getNickname(){
    return this.nickname;
  }
  public String getEmail(){
    return this.email;
  }
  public String getPassword(){
    return this.password;
  }

  public String getPhoneNumber(){
    return this.phoneNumber;
  }

  public boolean getAutoFillStatus(){
    return this.autoFill;
  }

  public String getCardName(){
    return this.cardName;
  }

  public String getCardNumber(){
    return this.cardNumber;
  }

  public String getCvvNumber(){
    return this.cvvNumber;
  }

  //setter methods: e.g. for changing login details ...
  //validate to ensure values to be set to are valid
  public void setID(int ID){
    if (ID >= 0){
      this.ID = ID;
    }
  }

  public void setNickname(String nickname){
    if (nickname != null && !nickname.equals("")) {
      this.nickname = nickname;
    }
  }
  //only set email if its valid
  public void setEmail(String email){
    if (this.validateUser(email)){
      this.email = email;
    }
  }

  public void setPhoneNumber(String phoneNumber){
    if (this.isValidPhoneNumber(phoneNumber)){
      this.phoneNumber = phoneNumber;
    }
  }

  //only set password if its valid
  public void setPassword(String newPassword){
    if (this.isValidPassword(newPassword)){
      this.password = newPassword;
    }
  }

  public void setAutoFillStatus(boolean newStatus){
    this.autoFill = newStatus;
  }

  public void setCardName(String name){
    if (name != null && !name.equals("")){
      this.cardName = name;
    }
  }

  public void setCardNumber(String number){
    //validate before setting
    if (this.isValidCardNumber(number)){
      this.cardNumber = number;
    }
  }

  public void setCvvNumber(String cvvNumber){ //validate length of this (? = 3)
    this.cvvNumber = cvvNumber;
  }

  //rename maybe bookingTicket --> bookTicket? OR nahh
  public void bookingTicket(Person person, int num){
    ticket.replace(person,ticket.get(person)+num);
    this.totalPrice();
  }

  public void cancelTicket(Person person, int num){
    ticket.replace(person,ticket.get(person)-num);
    this.totalPrice();
  }

  public void AddTicketMessage(){
    ticketMessage = "";
    for(Person key: ticket.keySet()){
      ticketMessage = (ticketMessage+key+"-----"+Integer.toString(ticket.get(key))+"\n");
    }
  }
  public void totalPrice(){
    totalPriceMutiplier = 0;
    for(Person key: ticket.keySet()){
      totalPriceMutiplier += key.getValue()*ticket.get(key);
      // System.out.printf("USER: totalpricemultipler: %d\n", totalPriceMutiplier);
    }
  }
  public double getTotalPrice(){
    return this.totalPriceMutiplier;
  }

  public String getTicketMessage(){
    return this.ticketMessage;
  }
}