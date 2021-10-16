package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.io.*;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterEach;

class RegistrationTest {
  @Test void RegNotNull(){
    Registration reg = new Registration();
      assertNotNull(reg);
  }

  // @BeforeEach
  // public void setUp(){
  //   Registration reg = new Registration();
  // }

  // @AfterEach
  // public void tearDown(){ 
  //   reg = null;
  // }

  @Test void testInvalidUsername(){ //missing @ + .com
    Registration reg = new Registration();
    String input = "lala@hotmail";
    assertFalse(reg.validateUser(input));
  }

  @Test void testInvalidUsername1(){ //""- no input
    Registration reg = new Registration();
    String input = "";
    assertFalse(reg.validateUser(input));
  }
  @Test void testInvalidUsername2(){ //""- no letters before@
    Registration reg = new Registration();
    String input = " @gmail.com";
    assertFalse(reg.validateUser(input));
  }

  @Test void testValidUsername(){
    Registration reg = new Registration();
    String input = "hola@gmail.com";
    assertTrue(reg.validateUser(input));
  }
  @Test void testValidUsername2(){ //alpha-numeric
    Registration reg = new Registration();
    String input = "hoLLie241@hotmail.com";
    assertTrue(reg.validateUser(input));
  }

  @Test void testInvalidPassword(){ //too short
    Registration reg = new Registration();
    String input = "abc234X!";
    assertFalse(reg.isValidPassword(input));
  }

  @Test void testInvalidPassword2(){ //no >= 1 cap
    Registration reg = new Registration();
    String input = "abc234xx#!";
    assertFalse(reg.isValidPassword(input));
  }

  @Test void testInvalidPassword3(){
    Registration reg = new Registration();
    String input = "";
    assertFalse(reg.isValidPassword(input));
  }

  @Test void testValidPassword(){
    Registration reg = new Registration();
    String input = "Abcd1244#1";
    assertTrue(reg.isValidPassword(input));
  }
}
