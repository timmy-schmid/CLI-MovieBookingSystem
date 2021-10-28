package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

class UserTest {
  User userA;
  User userB;
  User userC;

  @BeforeEach
  public void setUp() {

    userA = new User(1, "bob", "bob@gmail.com", "0488881188", "abc123Axd#!");
    userB = new User(5, "hola", "hello@gmail.com", "0488881122" ,"oHxd124!xh");

    userC = new User(-1, null, null, null, null);
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
