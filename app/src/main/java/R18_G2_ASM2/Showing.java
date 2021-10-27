package R18_G2_ASM2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Showing implements Comparable<Showing> {
  private int showingId;
  private Movie movie;
  private Cinema cinema;
  private Calendar showingTime;

  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

  //private  Map<String, Boolean> seatMap;
  private  Map<SeatLocation, Integer> seatsBooked; 
  private MovieSeat movieSeat;

  public Showing (int showingId, Movie movie, Cinema cinema, Calendar showingTime) throws IOException {
    this.showingId = showingId;
    this.movie = movie;
    this.cinema = cinema;
    this.showingTime = showingTime;

    // initialise seatMap based on cinema type
    
    seatsBooked = new HashMap<>();
    seatsBooked.put(SeatLocation.REAR,0);
    seatsBooked.put(SeatLocation.MIDDLE,0);
    seatsBooked.put(SeatLocation.FRONT,0);
    this.movieSeat = new MovieSeat(this); //handle Io-Exception
  }

  public int getShowingId() {
    return showingId;
  }
  //add tests
  public boolean setShowingTime(int year, int month, int date, int hourOfDay, int minute) {
    this.showingTime.set(year, month, date, hourOfDay, minute);
    return true;
  }

  public Movie getMovie() {
    return movie;
  }

  public Cinema getCinema() {
    return cinema;
  }

  public Calendar getShowingTime() {
    return showingTime;
  }

  public String getShowingTimeFormatted() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE dd MMM - K:mma",Locale.ENGLISH);

    return formatter.format(showingTime.getTime()).toUpperCase();
  }

  public String getShowingTimeShort() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE K:mma",Locale.ENGLISH);

    return formatter.format(showingTime.getTime()).toUpperCase();
  }
  
  public boolean isSeatEmpty() {
    return totalSeatsBooked()==0;
  }

  public boolean isShowingFull() {
    return totalSeatsLeft() == 0;
  }

  public int totalSeatsBooked() {
    return movieSeat.totalSeatsBooked();
  }

  public int totalSeatsLeft() {
    return movieSeat.totalSeatsLeft();
  }

  public int rearSeatBooked(){
    return movieSeat.rearSeatBooked();
}

public int frontSeatBooked(){
    return movieSeat.frontSeatBooked();
}

public int middleSeatBooked(){
    return movieSeat.middleSeatBooked();
}

  public MovieSeat getMovieSeat(){
    return movieSeat;
  }

  public void setMovieSeat() throws IOException{
    this.movieSeat = new MovieSeat(this);
  }

  public void setMovieSeatForTest() throws IOException{
    this.movieSeat = new MovieSeat(this, true);
  }

  @Override
  public String toString() {
    return cinema.getId() + ":" + getShowingTimeFormatted() + ":" + movie.getName();
  }

  static class SortMovieByShowingTime implements Comparator<Showing> {
    @Override
    public int compare(Showing a, Showing b) {
        return Long.compare(a.showingTime.getTimeInMillis(), b.showingTime.getTimeInMillis());
    }
  }
  static class SortMoviesByTitleThenShowingTime implements Comparator<Showing> {
    @Override
    public int compare(Showing a, Showing b) {

        int comp = a.getMovie().getName().compareTo(b.getMovie().getName());
        if(comp == 0) {
          return new SortMovieByShowingTime().compare(a,b);  
        }
        return comp;
    }
  }

  public void showAllSeats(){
    movieSeat.showAllSeats();
  }

  public void showFrontSeats(){
    movieSeat.showFrontSeats();
  }

  public void showMiddleSeats(){
    movieSeat.showMiddleSeats();
  }

  public void showRearSeats(){
    movieSeat.showRearSeats();
  }

  public boolean bookSeat(char rowLetter, int colNum) throws IOException{
    return movieSeat.bookSeat(rowLetter, colNum);
  }

  public boolean cancelReservation(char rowLetter, int colNum) throws IOException{
    return movieSeat.cancelReservation(rowLetter, colNum);
  }
  
  public static int getSingleMovieShowings(HashMap<Integer,Showing> showings, Movie m) {

    StringBuilder s = new StringBuilder();
    s.append("-----------------------------------------\n");
    s.append("ID  TIME                 CINEMA\n");
    s.append("-----------------------------------------\n");

    List<Showing> showingsByTime = new ArrayList<>(showings.values());
    Collections.sort(showingsByTime, new SortMovieByShowingTime());

    int count = 0;
    for (Showing currShowing : showingsByTime) {
        if(currShowing.getMovie().getId() == m.getId() &&
           currShowing.showingTime.after(Calendar.getInstance(AEST,Locale.ENGLISH))) {
          s.append(String.format("%-4s",count+1));
          s.append(String.format("%-21s",currShowing.getShowingTimeFormatted()));
          s.append(String.format("%s - %s CLASS\n",currShowing.getCinema().getId(),currShowing.getCinema().getScreen().name()));
          count++;
        }
    }
    System.out.println(s);
    return count;
  }

  public static int getAllMovieShowings(HashMap<Integer,Showing> showings) {
    
    StringBuilder s = new StringBuilder();
    s.append("------------------------------------------------------------------------------------------\n");
    s.append("ID  MOVIE                                              TIMES\n");
    s.append("------------------------------------------------------------------------------------------");
    

    List<Showing> sortedShowings = new ArrayList<>(showings.values());
    Collections.sort(sortedShowings, new SortMoviesByTitleThenShowingTime());

    int count = 0;
    int currTitle = 0;
    int lastTitle = -1;
    int padding = 55;
    int sessionCounter = 0;

    for (Showing currShowing : sortedShowings) {
      currTitle = currShowing.movie.getId();
      if (currShowing.showingTime.after(Calendar.getInstance(AEST,Locale.ENGLISH)) &&
          currShowing.showingTime.before(getNextMonday(currShowing.showingTime))) {
        
        if (lastTitle != currTitle) {

          
          //String currId = Screen.formatANSI(String.valueOf(currShowing.getMovie().getId()),Screen.ANSI_USER_OPTION);

          s.append(String.format("\n%-4s",currShowing.getMovie().getId()));

          String currName = currShowing.getMovie().getName();

          if (currName.length() > 47) {
            currName = currName.substring(0,47) + "...";
          }
          s.append(String.format("%-51s", currName));
          s.append(String.format("%s",currShowing.getShowingTimeShort()));
          sessionCounter = 1;
          count++;
        } else {
          if (Math.floorMod(sessionCounter,3) == 0) {
            s.append(String.format(",\n%" + padding + "s", " "));
            s.append(String.format("%s",currShowing.getShowingTimeShort()));
          } else {
            s.append(String.format(", %s",currShowing.getShowingTimeShort()));
          }
          sessionCounter++;
        }
        lastTitle = currTitle;

      }
    }
    System.out.println(s);
    return count;
  }

  @Override
  public int compareTo(Showing b) {
    return Long.compare(this.getShowingTime().getTimeInMillis(), b.getShowingTime().getTimeInMillis());
  }

  public static Calendar getNextMonday (Calendar c) {
    Calendar nextMon = Calendar.getInstance(AEST, Locale.ENGLISH);
    nextMon.setTime(c.getTime());
    nextMon.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    nextMon.add(Calendar.DATE,7);
    return nextMon;
  }

}

