package R18_G2_ASM2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
public class Movie {
  
  private int id;
  private String name;
  private String synopsis;
  private Calendar releaseDate;
  private Classification classification;
  private List<String> directors;
  private List<String> cast;
  private ArrayList<Showing> showings;

  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

  public Movie (int id, String name, List<String> cast, Classification classification,
                List<String> directors, String synopsis,Calendar releaseDate) {

    this.id = id;
    this.name = name;
    this.cast = cast;
    this.classification = classification;
    this.directors = directors;
    this.synopsis = synopsis;
    this.releaseDate = releaseDate;
    this.showings = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public Classification getClassification() {
    return classification;
  }

  public List<String> getDirectors() {
    return directors;
  }

  public List<String> getCast() {
    return cast;
  }

  public Calendar getReleaseDate() {
    return releaseDate;
  }

  public void addShowing(Showing showing) {
    // inserts in order of showing time
    int i = 0;
    while (i < showings.size()) {
      if (showing.compareTo(showings.get(i)) < 0) {
        showings.add(i, showing);
        return;
      }
      i++;
    }
    showings.add(showing);
  }

  public ArrayList<Showing> getShowingsBeforeNextMonday() {
    ArrayList<Showing> filtered = new ArrayList<>();
    for(Showing showing: showings) {
      if (showing.getShowingTime().after(Calendar.getInstance(AEST,Locale.ENGLISH)) &&
          showing.getShowingTime().before(getNextMonday(showing.getShowingTime()))) {
        filtered.add(showing);
      }
    }
    return filtered;
  }

  public ArrayList<Showing> getShowings() {
    return showings;
  }

  public void printMovieDetails() {

    StringBuilder s = new StringBuilder();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YY",Locale.ENGLISH);
    formatter.setTimeZone(AEST);

    s.append(String.format("SYNOPSIS: %s\n\n",synopsis));
    s.append(String.format("CLASSIFICATION: %s\n",classification.name()));
    s.append(String.format("RELEASE DATE: %s\n",formatter.format(releaseDate.getTime())));
    s.append("DIRECTORS: ");

    int i = 0;
    int lineSize = "DIRECTORS: ".length();
    String currDirector;

    while (i < directors.size()) {
      currDirector = directors.get(i);
      lineSize += currDirector.length();

      if (lineSize >= 80) {
        s.append(String.format("\n   %s",currDirector));
        lineSize = 0;
      } else {
        s.append(currDirector);
      }

      if (i != directors.size() - 1) {
        s.append(", ");
      } else {
        s.append('\n');
      }
      i++;
    }

    s.append("CAST: ");

    i = 0;
    lineSize = "CAST: ".length();
    String currActor;

    while (i < cast.size()) {
      currActor = cast.get(i);
      lineSize += currActor.length();

      if (lineSize >= 80) {
        s.append(String.format("\n   %s",currActor));
        lineSize = 0;
      } else {
        s.append(currActor);
      }
      if (i != cast.size() - 1) {
        s.append(", ");
      }  else {
        s.append('\n');
      }
      i++;
    }
    System.out.println(s);
  }
  static class SortMoviesByTitle implements Comparator<Movie> {
    @Override
    public int compare(Movie a, Movie b) {
        return a.getName().compareTo(b.getName());
    }
  }
  public static ArrayList<Movie> printAllShowings(HashMap<Integer,Movie> movies) {
    
    StringBuilder s = new StringBuilder();
    s.append("------------------------------------------------------------------------------------------\n");
    s.append("ID  MOVIE                                              TIMES\n");
    s.append("------------------------------------------------------------------------------------------");

    ArrayList<Movie> sortedMovies = new ArrayList<>(movies.values());
    Collections.sort(sortedMovies, new SortMoviesByTitle());

    int count = 1;
    int padding = 55; //TODO make a constant

    for (Movie movie : sortedMovies) {      
      int showCounter = 1;
      for (Showing showing: movie.getShowingsBeforeNextMonday()) {
        //prints title on 1st showing
        if (showCounter == 1) {
          //String currId = Screen.formatANSI(String.valueOf(currShowing.getMovie().getId()),Screen.ANSI_USER_OPTION);
          s.append(String.format("\n%-4s",count));
          // cuts name if over certain length to allow clean formatting.
          String truncatedName = movie.getName();
          if (truncatedName.length() > 47) { //TODO get rid of these magic number 47
            truncatedName = truncatedName.substring(0,47) + "...";
          }
          s.append(String.format("%-51s", truncatedName));
          s.append(String.format("%s",showing.getShowingTimeShort()));
          count++;
        } else {
          // wraps the showing time to next row  if more than 3 long.
          if (Math.floorMod(showCounter,3) == 0) { //TODO get rid of these magic number 3
            s.append(String.format(",\n%" + padding + "s", " "));
            s.append(String.format("%s",showing.getShowingTimeShort()));
          } else {
            s.append(String.format(", %s",showing.getShowingTimeShort()));
          }
        }
        showCounter++; 
      }
      //TODO remove movies that have now showing times.
      /*
      if (!titlePrinted) {
        sortedMovies.remove(movie);
      }*/
    }
    System.out.println(s);
    return sortedMovies;
  }

  public int printMovieShowings() {

    StringBuilder s = new StringBuilder();
    s.append("-----------------------------------------\n");
    s.append("ID  TIME                 CINEMA\n");
    s.append("-----------------------------------------\n");

    int count = 0;
    for (Showing currShowing : getShowingsBeforeNextMonday()) {
      s.append(String.format("%-4s",count+1));
      s.append(String.format("%-21s",currShowing.getShowingTimeFormatted()));
      s.append(String.format("%s - %s CLASS\n",currShowing.getCinema().getId(),currShowing.getCinema().getScreen().name()));
      count++;
    }
    System.out.println(s);
    return count;
  }





  public static Calendar getNextMonday (Calendar c) {
    Calendar nextMon = Calendar.getInstance(AEST, Locale.ENGLISH);
    nextMon.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    nextMon.add(Calendar.DATE,6);
    return nextMon;
  }
}
