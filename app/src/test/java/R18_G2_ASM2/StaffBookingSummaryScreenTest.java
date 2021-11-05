package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.text.*;

public class StaffBookingSummaryScreenTest {
  final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

  private StaffBookingSummaryScreen sbs;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream(); //for testing printing statements
  private final PrintStream originalOutput = System.out;

  @BeforeAll static void setPath() {
    DataController.setBasePath("src/test/resources/");
  }

  @BeforeEach
  public void setUp() throws IOException {
    sbs = new StaffBookingSummaryScreen();
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void tearDown(){
    sbs = null;
    //restoreStreams
    System.setOut(originalOutput);
  }

  @Test
  public void canPrintBookingSummary(){
    String message = "************************************************************" + "**************************************************************\n" +
    "                                                  Booking Summary Report                                                  \n" +
    "*********************************************************************" + "*****************************************************\n" +
    "-----------------------------------------------------" + "---------------------------------------------------------------------\n" +
    "MOVIE NAME                                              SHOWING ID                SEATS BOOKED                SEATS LEFT\n" +
    "----------------------------------------------------------------" + "----------------------------------------------------------\n";
    sbs.printBookingSummary();
    assertEquals(outContent.toString(), message);

  }

  @Test 
  public void testNotNull(){
    assertNotNull(sbs.getMovies());
    assertNotNull(sbs.getCinemas());
    assertNotNull(sbs.getMovieDataFile());
  }

  @Test 
  public void checkNullSeats() {
    int booked = sbs.seatsBooked(null);
    assertEquals(booked, 0);
  }

  @Test 
  public void checkNullSeats2() {
   int remaining = sbs.seatsLeft(null);
    assertEquals(remaining, 0);
  }
}
