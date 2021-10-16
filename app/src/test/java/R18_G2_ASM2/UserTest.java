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
}
