package R18_G2_ASM2;

public class Cinema {

  private int id;
  //private int capacity; (this can be calculated in Showing.java)
  Screen cinemaScreen;


  public Cinema (int id, Screen cinemaScreen){
    this.id = id;
    this.cinemaScreen = cinemaScreen;
  }

  public int getId() {
    return id;
  }

  public Screen getScreen() {
    return cinemaScreen;

  }
}
