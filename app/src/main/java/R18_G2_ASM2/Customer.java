package R18_G2_ASM2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Customer extends User {
  private LinkedHashMap<Person,Integer> ticket = new LinkedHashMap<>();
  private String ticketMessage = "";
  private double totalPriceMutiplier = 0;

  private boolean autoFill;
  private String cardName;
  private String cardNumber;
  private String cvvNumber;
  private Showing pendingPaymentShow;


  public Customer(int ID, String nickname, String email, String phoneNumber, String password) {
    super(ID, nickname, email, phoneNumber, password);
    ticket.put(Person.Child,0);
    ticket.put(Person.Student,0);
    ticket.put(Person.Senior,0);
    ticket.put(Person.Adult,0);

    this.userType = UserType.CUSTOMER;
    this.cardName = null;
    this.cardNumber = null;
    this.cvvNumber = null;
    this.autoFill = false; //default, then prompt user during transaction stage to update
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
    if (User.isValidCardNumber(number)){
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

  public void setPendingPaymentShow(Showing showing){
    this.pendingPaymentShow = showing;
  }

  public Showing getPendingPaymentShow(){
    return pendingPaymentShow;
  }

  public void resetSeatMap(){
    pendingPaymentShow.resetSeatMap();
  }


  public void completeTransaction(){
    pendingPaymentShow.completeTransaction();
  }
}
