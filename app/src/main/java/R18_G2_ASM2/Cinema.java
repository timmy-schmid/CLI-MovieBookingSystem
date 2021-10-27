package R18_G2_ASM2;

public class Cinema {

  private int id;
  //private int capacity; (this can be calculated in Showing.java)
  MovieClass cinemaScreen;


  public Cinema (int id, MovieClass cinemaScreen){
    this.id = id;
    this.cinemaScreen = cinemaScreen;
  }

  public int getId() {
    return id;
  }

  public MovieClass getScreen() {
    return cinemaScreen;

  }
}
