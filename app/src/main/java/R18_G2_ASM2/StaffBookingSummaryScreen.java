package R18_G2_ASM2;

import java.util.*;
import java.io.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class StaffBookingSummaryScreen {
  private Showing showing;
  private File movieSeat;
  public StaffBookingSummaryScreen(Showing showing) throws IOException{
    this.showing = showing;
  }

  public int seatsBooked() {
    return this.showing.getMovieSeat().totalSeatsBooked();
  }

  public int seatsLeft() {
    return this.showing.getMovieSeat().totalSeatsLeft();
  }
}