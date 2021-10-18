package R18_G2_ASM2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

public class Showing {
  private int showingId;
  private Movie movie;
  private Cinema cinema;
  private Calendar showingTime;

  //private  Map<String, Boolean> seatMap;
  private  Map<SeatLocation, Integer> seatsBooked; 

  public Showing (int showingId, Movie movie, Cinema cinema, Calendar showingTime) {
    this.showingId = showingId;
    this.movie = movie;
    this.cinema = cinema;
    this.showingTime = showingTime;

    // initialise seatMap based on cinema type
    
    seatsBooked = new HashMap<SeatLocation, Integer>();
    seatsBooked.put(SeatLocation.REAR,0);
    seatsBooked.put(SeatLocation.MIDDLE,0);
    seatsBooked.put(SeatLocation.FRONT,0);
  }

  public int getShowingId() {
    return showingId;
  }

  public Movie getMovie() {
    return movie;
  }

  public Cinema getCinema() {
    return cinema;
  }

  public String getShowingTimeFormatted() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE K:mma",Locale.ENGLISH);

    return formatter.format(showingTime.getTime()).toUpperCase();
  }
  
  public boolean isSeatEmpty(String seat) {
    return false;
  }

  public boolean isShowingFull() {
    return false;
  }

  public int getTotalSeatsBooked() {
    return 0;
  }

  public int getTotalSeatsLeft() {
    return 0;
  }

  @Override
  public String toString() {
    return cinema.getCinemaId() + ":" + getShowingTimeFormatted() + ":" + movie.getName();
  }
}

