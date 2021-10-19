package R18_G2_ASM2;

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

public class Showing {
  private int showingId;
  private Movie movie;
  private Cinema cinema;
  private Calendar showingTime;

  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

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
    SimpleDateFormat formatter = new SimpleDateFormat("EEE dd MMM - K:mma",Locale.ENGLISH);

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
    return cinema.getId() + ":" + getShowingTimeFormatted() + ":" + movie.getName();
  }

  static class SortByShowingTime implements Comparator<Showing> {
    @Override
    public int compare(Showing a, Showing b) {
        return Long.compare(a.showingTime.getTimeInMillis(), b.showingTime.getTimeInMillis());
    }
  }

  public static int getMovieShowings(HashMap<Integer,Showing> showings, StringBuilder s, Movie m) {

    s.append("UPCOMING SESSIONS:\n");
    s.append("-----------------------------------------\n");
    s.append("ID  TIME                 CINEMA\n");
    s.append("-----------------------------------------\n");
  
    List<Showing> showingsByTime = new ArrayList<>(showings.values());
    Collections.sort(showingsByTime, new SortByShowingTime());

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
    return count;
  }
}

