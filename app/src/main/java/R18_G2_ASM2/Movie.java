package R18_G2_ASM2;

import java.util.Calendar;
import java.util.List;

public class Movie {
  
  private int id;
  private String name;
  private String synopsis;
  private Calendar releaseDate;
  private Classification classification;
  private List<String> directors;
  private List<String> cast;

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

  public Calendar getreleaseDate() {
    return releaseDate;
  }

  @Override
  public String toString() {
    return id + " - " + name + ": " + synopsis;
  }
}
