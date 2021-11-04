package R18_G2_ASM2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
public class AddShowing extends Screen {

  private ArrayList<Movie> moviesSorted;

  private MovieScreen movScreen;
  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");
  private Movie selectedMov;
  private Cinema selectedCin;
  private Calendar selectedTime;
  SimpleDateFormat f = new SimpleDateFormat("EEE dd MMM - K:mma", Locale.ENGLISH);

  public AddShowing(MovieSystem mSystem) {
    super(mSystem);
    f.setTimeZone(AEST);

    if (mSystem.getUser().getUserType() == UserType.GUEST ||
      mSystem.getUser().getUserType() == UserType.CUSTOMER) {
      throw new IllegalArgumentException("Invalid user type for this screen");
    }
    title = "ADD NEW SHOWING: CURRENT SHOWINGS (1 NOV - 14 NOV)"; //TODO create variable date
  }

  @Override
  protected void setOptions() {
    options = new ArrayList<>();
    maxInputInt = mSystem.getMovies().size();
    options.add("R"); options.add("r");
    intOption = NO_INT_OPTION;
    goBack = false;
    //options.add("A"); options.add("a");
    //options.add("G"); options.add("g");
    //options.add("S"); options.add("s");
    //options.add("B"); options.add("b");
  }

  @Override
  protected void chooseOption() {
    if (intOption != NO_INT_OPTION) {
      selectedMov = moviesSorted.get(intOption-1);
        options = new ArrayList<>();
        options.add("c"); options.add("C");
        if (!askForCinema()) {
          if (!askForDay()) {
            askForTime();
            try {
              selectedMov.addShowing(new Showing(Showing.getLastShowingIndex(),selectedMov,selectedCin,selectedTime));
              DataController.writeShowingToFile("showing.csv",selectedMov.getId(),selectedCin.getId(), selectedTime.getTimeInMillis());
            } catch (IOException e) {
              System.out.println("Error writing showing to db: " + e.getMessage());
            }
            System.out.println("Succesfully added showing at " + f.format(selectedTime.getTime()) + " for " + selectedMov.getName());
          }
          //goBack = true;
        }
    } else {
      switch (selectedOption) {
        case "G": case "g":
          // TODO add gold filter
          break;
        case "S": case "s":
          // TODO add silver filter
          break;
        case "B": case "b":
          // TODO add bronze filter
          break;
        case "A": case "a":
          // TODO add std filter
          break;
        case "R": case "r":
          goBack = true;
          break;
        default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
      }
    }
  }

  public boolean askForCinema() {
    intOption = NO_INT_OPTION;

    ArrayList<Cinema> sortedCinemas = new ArrayList<>(mSystem.getCinemas().values());
    Collections.sort(sortedCinemas, new SortCinemasByID());

    maxInputInt = sortedCinemas.size();
    System.out.println("Please select a cinema:");
    for (Cinema cinema: sortedCinemas) {
      System.out.println(cinema.toString());
    }
    System.out.print(formatANSI("C",ANSI_USER_OPTION) + " - To go cancel and go back to all movies\n");

    this.askforInput();

    if (intOption != NO_INT_OPTION) {
      selectedCin = mSystem.getCinemas().get(intOption);
    } else if (selectedOption.toLowerCase().equals("c")) {
      return true;
    } else {
      throw new IllegalArgumentException("Critical error - invalid selection passed validation");
    }
    return false;
  }

  public boolean askForDay() {
    intOption = NO_INT_OPTION;

    Calendar start = Calendar.getInstance(AEST, Locale.ENGLISH);
    start.set(Calendar.HOUR_OF_DAY, 0);
    start.set(Calendar.MINUTE, 0);
    System.out.println("START" + f.format(start.getTime()));
    Calendar end = Movie.getWeekEnd(2);

    int dayCounter = 0;
    System.out.println("Please select a day:");

    SimpleDateFormat formatter = new SimpleDateFormat("EEE dd MMM",Locale.ENGLISH);
    formatter.setTimeZone(AEST);

    Calendar temp = Calendar.getInstance(AEST, Locale.ENGLISH);
    temp.setTime(start.getTime());
    while (temp.before(end)) {
      dayCounter++;
      System.out.println(formatANSI(String.valueOf(dayCounter),ANSI_USER_OPTION) + " - " + formatter.format(temp.getTime()));
      temp.add(Calendar.DAY_OF_YEAR,1);
    }
    maxInputInt = dayCounter;

    System.out.print(formatANSI("C",ANSI_USER_OPTION) + " - To go cancel and go back to all movies\n");

    this.askforInput();

    if (intOption != NO_INT_OPTION) {
      start.add(Calendar.DAY_OF_YEAR,intOption-1);
      selectedTime = start;
    } else if (selectedOption.toLowerCase().equals("c")) {
      return true;
    } else {
      throw new IllegalArgumentException("Critical error - invalid selection passed validation");
    }
    return false;
  }

  public void askForTime() {
    intOption = NO_INT_OPTION;
    
    System.out.print("Please select a day Time (in 24 hour time): ");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

    Scanner sc = new Scanner(System.in);
    while (sc.hasNextLine()) {
      String timeString = sc.nextLine();
      try {
        LocalTime time = LocalTime.parse(timeString,formatter);
        selectedTime.add(Calendar.HOUR, time.getHour());
        selectedTime.add(Calendar.MINUTE, time.getMinute());
        break;
      } catch (DateTimeParseException e) {
        System.out.print("Invalid time entered. Example is 16:15. please try again: ");
        continue;
      }
    }
  }

  static class SortCinemasByID implements Comparator<Cinema> {
    @Override
    public int compare(Cinema a, Cinema b) {
        return Integer.compare(a.getId(),b.getId());
    }
  }

  @Override
  public void print() {
    clearScreen();
    //System.out.print("Current Date & Time: OCT 27 - THU 9:57PM\n");  // TODO make dynamic time
    moviesSorted = Movie.printAllShowings(mSystem.getMovies(),Calendar.getInstance(AEST,Locale.ENGLISH), Movie.getWeekEnd(2),true, DateSize.MED);
    //maxInputInt = moviesSorted.size();

    printHeader();

    System.out.printf("Welcome %s,\n\n", mSystem.getUser().getNickname());

    printOptionsText();

    System.out.print(formatANSI("[ID]",ANSI_USER_OPTION) +" - To book a new showing for a particular movie\n");
    System.out.print(formatANSI("R",ANSI_USER_OPTION) + " - To go back to the staff Home Page\n\n");
    /*
    System.out.print(formatANSI("[G|S|B]",ANSI_USER_OPTION) +
              " - To filter showings by " +
              formatANSI("Gold",ANSI_GOLD) + ", " +
              formatANSI("Silver",ANSI_SILVER) + " or " +
              formatANSI("Bronze",ANSI_BRONZE) + " screens\n");
    
              System.out.print(formatANSI("A",ANSI_USER_OPTION) + " - To display all showings for the coming week (default)\n");*/
  }
}
