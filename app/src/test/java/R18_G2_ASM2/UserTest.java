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

  @Test void testNotNullUsers(){
    User userA = new User(-1, "bob@gmail.com", "abc123Axd#!");
    User userB = new User(5, "hello@gmail.com", "oHxd124!xh");
    User userC = new User(-1, null, null);
    assertNotNull(userA);
    assertNotNull(userB);
    assertNotNull(userC);
  }
  @Test void testCanGetPassword(){
    User userB = new User(5, "hello@gmail.com", "oHxd124!xh");
    String res = "oHxd124!xh";
    assertEquals(res, userB.getPassword());
  }

  //testing setting methods work
  @Test void testCanSetEmail(){
    User userB = new User(5, "hello@gmail.com", "oHxd124!xh");
    String newEmail = "hollo@gmail.com";
    userB.setEmail(newEmail);
    assertEquals(userB.getEmail(), newEmail);
  }

  @Test void testCantSetEmail(){
    User userB = new User(5, "hello@gmail.com", "oHxd124!xh");
    String newEmail = "holaAmigos";
    userB.setEmail(newEmail);
    assertNotEquals(userB.getEmail(), newEmail);
  }

  @Test void testCanSetPassword(){
    User userB = new User(5, "hello@gmail.com", "oHxd124!xh");
    String newPassword = "BobTheBuilder1";
    userB.setPassword(newPassword);
    assertEquals(userB.getPassword(), newPassword);
  }

  @Test void testCantSetPassword(){
    User userB = new User(5, "hello@gmail.com", "oHxd124!xh");
    String newPassword = "holaAmigos";
    userB.setPassword(newPassword);
    assertNotEquals(userB.getPassword(), newPassword);
  }

  @Test void testCanGetUserID(){
    User userB = new User(5, "hello@gmail.com", "oHxd124!xh");
    assertEquals(5, userB.getID());
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
