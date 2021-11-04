package R18_G2_ASM2;

public class Cinema {

  private int id;
  MovieClass cinemaScreen;

  public static final String ANSI_BRONZE = "\033[38;2;176;141;87;m";
  public static final String ANSI_SILVER = "\033[38;2;170;169;173;m";
  public static final String ANSI_GOLD = "\033[38;2;212;175;55;m";
  public static final String ANSI_RESET = "\u001B[0m";

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

  @Override
  public String toString() {
    return formatByClass(id + " - " + cinemaScreen.name());
  }

  public String formatByClass (String s) {
    if (cinemaScreen == MovieClass.BRONZE) {
      s = ANSI_BRONZE + s + ANSI_RESET;
    } else if (cinemaScreen == MovieClass.SILVER) {
      s = ANSI_SILVER + s + ANSI_RESET;
    } else if (cinemaScreen == MovieClass.GOLD) {
      s = ANSI_GOLD + s + ANSI_RESET;
    }
    return s;
  }

  
}
