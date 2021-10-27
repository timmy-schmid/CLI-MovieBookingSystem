
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
  private GiftCard giftCard;
  private Card creditCard;

  private String cardNumber;

  public User(int ID, String email, String password){
    this.ID = ID;
    this.email = email;
    this.password = password;
    ticket.put(Person.Child,0);
    ticket.put(Person.Student,0);
    ticket.put(Person.Senior,0);
    ticket.put(Person.Adult,0);

    this.autoFill = false; //default, then prompt user during transaction stage to update
  }

  // TO BE REMOVED!!!!! SPRINT 2 TUES MEETING --> NEW UPDATW
  public User(int ID, String nickname, String email, String phoneNumber, String password, Card creditCard, GiftCard userGiftCard){ //extra fields added
    this.ID = ID;
    this.nickname = nickname;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
    this.creditCard = creditCard;
    this.giftCard = userGiftCard;

    ticket.put(Person.Child,0);
    ticket.put(Person.Student,0);
    ticket.put(Person.Senior,0);
    ticket.put(Person.Adult,0);

    this.autoFill = false; //default, then prompt user during transaction stage to update
  }


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
    this.cardNumber = null;
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

  // public Card getCreditCard(){
  //   return this.creditCard;
  // }

  // public GiftCard getGiftCard(){
  //   return this.giftCard;
  // }
  
  public String getCardNumber(){
    return this.cardNumber;
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

  public void setAutoFillStatus(boolean newStatus){
    this.autoFill = newStatus;
  }

  public void setCardNumber(String number){
    this.cardNumber = number;
  }


  //rename maybe bookingTicket --> bookTicket? OR nahh
  public void bookingTicket(Person person, int num){
    ticket.replace(person,ticket.get(person)+num);
    this.totalPrice();
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