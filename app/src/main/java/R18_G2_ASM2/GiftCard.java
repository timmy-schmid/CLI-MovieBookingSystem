package R18_G2_ASM2;


/*

Gift Cards: The customer will enter a 16 digit gift card 
number with the suffix GC. These gift cards numbers once redeemed 
should have their status changed to redeemed and no longer redeemable.

Cinema Staff: Cinema staff should be able to insert movie data, delete movie data, modify movie data, add new shows for the upcoming week and choose the selected screen sizes. Cinema staff are also responsible for maintaining the gift card database/file and ensuring all new gift cards are entered accordingly. Only once these gift cards are entered in the database/file by the cinema staff , a customer can use the gift card.


Q: how to know which customer has a gift card?
*/
public class GiftCard {
  private boolean status; //redeemable or not
  private String cardNumber;
  private String giftCardFile;

  public GiftCard(String cardNumber, boolean status){
    this.cardNumber = cardNumber;
    this.status = status;
    this.giftCardFile = null;
  }
  public String getCardNumber(){ //16-digit
    return this.cardNumber;
  }

  public boolean getStatus(){ 
    return this.status;
  }

  public void setStatus(boolean newStatus){ 
    this.status = newStatus;
  }

  public void validateGiftCard(){ //a staff member enters gift cards into database/file so that a customer can use them
    //check length of cardNumber == 16, numbers only 
  }
  
  public String readCardCsvFile(String filename){
    return null;
  }

  public void updateGiftCardFile(String filename){ //overwrites existing gift cards in file by changing the status of the card
    return;
  }
}
