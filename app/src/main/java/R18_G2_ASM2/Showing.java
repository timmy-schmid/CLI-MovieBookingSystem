package R18_G2_ASM2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Locale;


public class Showing implements Comparable<Showing> {
  private int showingId;
  private Movie movie;
  private Cinema cinema;
  private Calendar showingTime;

  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

  private  Map<SeatLocation, Integer> seatsBooked; 
  private MovieSeat movieSeat;

  public Showing (int showingId, Movie movie, Cinema cinema, Calendar showingTime) throws IOException {
    this.showingId = showingId;
    this.movie = movie;
    this.cinema = cinema;
    this.showingTime = showingTime;

    // initialise seatMap based on cinema type
    setMovieSeat();
    seatsBooked = new HashMap<>();
    seatsBooked.put(SeatLocation.REAR,0);
    seatsBooked.put(SeatLocation.MIDDLE,0);
    seatsBooked.put(SeatLocation.FRONT,0);
    this.movieSeat = new MovieSeat(this); //handle Io-Exception
  }

  public int getShowingId() {
    return showingId;
  }

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
    formatter.setTimeZone(AEST);
    return formatter.format(showingTime.getTime()).toUpperCase();
  }

  public String getShowingTimeShort() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE K:mma",Locale.ENGLISH);
    formatter.setTimeZone(AEST);
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
  
  @Override
  public int compareTo(Showing b) {
    return Long.compare(this.getShowingTime().getTimeInMillis(), b.getShowingTime().getTimeInMillis());
  }

}

