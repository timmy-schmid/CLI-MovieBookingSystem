package R18_G2_ASM2;

public class Cinema {

  private int cinemaId;
  //private int capacity; (this can be calculated in Showing.java)
  Screen cinemaScreen;


  public Cinema (int cinemaId, Screen cinemaScreen){
    this. cinemaId = cinemaId;
    this. cinemaScreen = cinemaScreen;
  }

  public int getCinemaId() {
    return cinemaId;
  }


}
