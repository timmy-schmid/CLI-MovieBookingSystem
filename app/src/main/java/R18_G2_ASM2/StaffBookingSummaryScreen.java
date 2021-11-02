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
  private String MOVIES_FILE_NAME = "movie.csv";
  private String CINEMAS_FILE_NAME = "cinema.csv";
  private String SHOWINGS_FILE_NAME = "showing.csv";
  private HashMap<Integer,Movie> movies = new HashMap<>();
  private HashMap<Integer,Cinema> cinemas = new HashMap<>();
  private PrintStream out;

//  private Showing showing;
//  private ArrayList<Showing> showings = new ArrayList<>();

  public StaffBookingSummaryScreen() {
    importMovieData();
//    this.showings = movie.getShowingsBeforeNextMonday();
  }

  public void run() {
    printBookingSummary();
    getShowings();
  }

  public int seatsBooked(Showing showing) {
    return showing.getMovieSeat().totalSeatsBooked();
  }

  public int seatsLeft(Showing showing) {
    return showing.getMovieSeat().totalSeatsLeft();
  }

  public void printBookingSummary() {
    System.out.println("******************************************************************");
    System.out.println("                     Booking Summary Report                       ");
    System.out.println("******************************************************************");
    System.out.println("------------------------------------------------------------------");
    System.out.println("SHOWING ID                SEATS BOOKED                SEATS LEFT");
    System.out.println("------------------------------------------------------------------");
  }

  public void importMovieData() {
    try {
      DataController.importMovies(movies,MOVIES_FILE_NAME);
    } catch (IOException e) {
      out.println("Error reading file: " + MOVIES_FILE_NAME);
    }

    try {
      DataController.importCinemas(cinemas,CINEMAS_FILE_NAME);
    } catch (IOException e) {
      out.println("Error reading file: " + CINEMAS_FILE_NAME);
    }

    try {
      DataController.importShowings(movies,cinemas, SHOWINGS_FILE_NAME);
    } catch (IOException e) {
      out.println("Error reading file: " + SHOWINGS_FILE_NAME);
    }
  }

  public void setMovieDataFile(String name) {
    MOVIES_FILE_NAME = name;
  }

  public void setCinemaDataFile(String name) {
    SHOWINGS_FILE_NAME = name;
  }

  public void setShowingDataFile(String name) {
    CINEMAS_FILE_NAME = name;
  }

  public HashMap<Integer, Movie> getMovies() {
    return movies;
  }

  public HashMap<Integer, Cinema> getCinemas() {
    return cinemas;
  }

  static class SortShowingById implements Comparator<Showing> {
    @Override
    public int compare(Showing a, Showing b) {
      if(a.getShowingId() > b.getShowingId()) {
        return 1;
      } else if(a.getShowingId() == b.getShowingId()) {
        return 0;
      } else {
        return -1;
      }
    }
  }

  public void getShowings() {
    StringBuilder s = new StringBuilder();
    int count = 1;
    for (Movie movie: movies.values()) {
      ArrayList<Showing> sortedShowings = movie.getShowings();
      Collections.sort(sortedShowings, new SortShowingById());
      for (Showing showing: sortedShowings) {
        if(count == 1) {
          s.append(String.format("%-26s", showing.getShowingId()));
          s.append(String.format("%-28s", showing.totalSeatsBooked()));
          s.append(String.format("%s", showing.totalSeatsLeft()));
        } else {
          s.append(String.format("\n%-26s", showing.getShowingId()));
          s.append(String.format("%-28s", showing.totalSeatsBooked()));
          s.append(String.format("%s", showing.totalSeatsLeft()));
        }
        count ++;
      }
    }
    System.out.println(s);
  }
}