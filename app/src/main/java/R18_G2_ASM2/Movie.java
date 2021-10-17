package R18_G2_ASM2;

import java.util.List;

public class Movie {
  
  private int id;
  private String name;
  private String synposis;
  private ClassificationType classification;
  private List<String> directors;
  private List<String> cast;

  public Movie (int id, String name, String synposis, ClassificationType classification, List<String> directors, List<String> cast) {
    this.id = id;
    this.synposis = synposis;
    this.classification = classification;
    this.directors = directors;
    this.cast = cast;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSynopsis() {
    return synposis;
  }

  public ClassificationType getClassification() {
    return classification;
  }

  public List<String> getDirectors() {
    return directors;
  }

  public List<String> getCast() {
    return cast;
  }
}
