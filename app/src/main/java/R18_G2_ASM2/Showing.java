package R18_G2_ASM2;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Showing {
  private int showingId;
  private Movie movie;
  private Cinema cinema;

  private Date showingTime;

  private  Map<String, Boolean> seatMap;
  private  Map<SeatLocation, Integer> seatsBooked; 

  public Showing (int showingId, Movie movie, Cinema cinema, Date showingTime) {
    this.showingId = showingId;
    this.movie = movie;
    this.cinema = cinema;
    this.showingTime = showingTime;

    // initialise seatMap based on cinema type
    
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

  public int getShowingDay() {
    Calendar calendar = Calendar.getInstance(); //local timezone
    calendar.setTime(showingTime);
    return calendar.get(Calendar.DAY_OF_WEEK);
  }

  public String getShowingTime() {
    Calendar calendar = Calendar.getInstance(); //local timezone
    calendar.setTime(showingTime);

    SimpleDateFormat formatter = new SimpleDateFormat("KK:mm a");
    return formatter.format(showingTime);
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
}

