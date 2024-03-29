package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

class UserTest {
  Customer userA;
  Customer userB;
  Customer userC;

  @BeforeEach
  public void setUp() {

    userA = new Customer(1, "bob", "bob@gmail.com", "0488881188", "abc123Axd#!");
    userB = new Customer(5, "hola", "hello@gmail.com", "0488881122" ,"oHxd124!xh");

    userC = new Customer(-1, null, null, null, null);
  }

  @AfterEach
  public void tearDown(){ 
    userA = null;
    userB = null;
    userC = null;
  }

  @Test void testNotNullUsers(){
    assertNotNull(userA);
    assertNotNull(userB);
    assertNotNull(userC);
  }
  @Test void testCanGetPassword(){
    String res = "oHxd124!xh";
    assertEquals(res, userB.getPassword());
  }

  //testing setting methods work
  @Test void testCanSetEmail(){
    String newEmail = "hollo@gmail.com";
    userB.setEmail(newEmail);
    assertEquals(userB.getEmail(), newEmail);
  }

  @Test void testCantSetEmail(){
    String newEmail = "holaAmigos";
    userB.setEmail(newEmail);
    assertNotEquals(userB.getEmail(), newEmail);
  }

  @Test void testCantGetCardNumber(){
    String num = "C1234";
    userB.setCardNumber(num);
    assertNotEquals(userB.getCardNumber(), num);
  }

  @Test void testCanGetCardNameAndNumber(){
    String num = "12345";
    String name = "Anna";
    userB.setCardName(name);
    userB.setCardNumber(num);
    assertEquals(userB.getCardNumber(), num);
    assertEquals(userB.getCardName(), name);
  }

  @Test void testCanGetNickname(){
    String name = "Anna";
    userB.setNickname(name);
    assertEquals(userB.getNickname(), name);
  }

  @Test void testCanSetPhoneNumber(){
    String num = "0412345818";
    userB.setPhoneNumber(num);
    assertEquals(userB.getPhoneNumber(), num);
  }

  @Test void testCantSetPhoneNumber(){
    String num = "041234512A818";
    userB.setPhoneNumber(num);
    assertNotEquals(userB.getPhoneNumber(), num);
  }

  @Test void testCanSetPassword(){
    String newPassword = "BobTheBuilder1";
    userB.setPassword(newPassword);
    assertEquals(userB.getPassword(), newPassword);
  }

  @Test void testCantSetPassword(){
    String newPassword = "holaAmigos";
    userB.setPassword(newPassword);
    assertNotEquals(userB.getPassword(), newPassword);
  }

  @Test void testCanGetUserID(){
    assertEquals(5, userB.getID());
  }

  @Test void testCanGetUserInformation(){
    String expected = "User ID: 1\n" + 
                      "User type: CUSTOMER\n" + 
                      "User nickname: bob\n" + 
                      "User email: bob@gmail.com\n" + 
                      "User phone number: 0488881188\n";
    assertEquals(expected, userA.getUserInformation());
  }

  @Test void testCantSetInvalidId(){
    userB.setID(-2);
    assertEquals(5, userB.getID()); //no chaneg
  }

  // // test failed: IllegalFormatConversionException

  @Test void testBookingTicket(){
    userB.bookingTicket(Person.Child,1);
    assertEquals(0.5,userB.getTotalPrice());
  }

  //updates on user --> additional methods e.g. on tickets
  // @Test void test userCanBookTicket(){ //single ticket test
  //   User userA = new User(10, "amy@gmail.com", "holaAmigos1");
  //   // x0.5, 
  //   userA.bookingTicket(Person.Child, 1);
  //   // assertEquals(userA.getTicketMessage(), "--"+Person.Child.getValue()+
  //   // "----"+Integer.toString(Person.Child.getValue()))+"\n");
  // }
}
