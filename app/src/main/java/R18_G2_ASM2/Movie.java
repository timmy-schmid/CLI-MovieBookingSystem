package R18_G2_ASM2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
  private HashMap<Integer,Showing> showings;

  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");


  public Movie (int id, String name, List<String> cast, Classification classification, List<String> directors, String synopsis,Calendar releaseDate) {
    this.id = id;
    this.name = name;
    this.cast = cast;
    this.classification = classification;
    this.directors = directors;
    this.synopsis = synopsis;
    this.releaseDate = releaseDate;
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
    showings.put(showing.getShowingId(), showing);
  }

  public void printMovieDetails(StringBuilder s) {

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
  }
}
