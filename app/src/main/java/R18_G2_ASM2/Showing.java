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

  public String getShowingTimeShort() {
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

  public static int getSingleMovieShowings(HashMap<Integer,Showing> showings, StringBuilder s, Movie m) {

    s.append("UPCOMING SESSIONS:\n");
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
    return count;
  }

  public static int getAllMovieShowings(HashMap<Integer,Showing> showings, StringBuilder s) {
    s.append("UPCOMING SESSIONS:\n");

    
    s.append("------------------------------------------------------------------------------------------\n");
    s.append("ID  MOVIE                                             SHOWINGS\n");
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
    return count;
  }

  public static Calendar getNextMonday (Calendar c) {
    Calendar nextMon = Calendar.getInstance(AEST, Locale.ENGLISH);
    nextMon.setTime(c.getTime());
    nextMon.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    nextMon.add(Calendar.DATE,7);
    return nextMon;
  }


}

