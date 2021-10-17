package R18_G2_ASM2;
public class Screen {

  public static void printBar(StringBuilder s) {
    s.append("-------------------------------------------------------\n");
  }

  public static void printHeader(StringBuilder s) {
    s.append("-------------------------------------------------------\n");
    s.append("ID   MOVIE                             TIME     CINEMA\n");
    s.append("-------------------------------------------------------\n");
  }

  //public static void printMovieRow(StringBuilder s, Movie m) {
    //printBar(s);
    //s.append(String.format("%4d",f.id));
    //s.append(String.format("%10s",f.departureTime.toString(TextStyle.SHORT)));
    //printBar(s);
  //}


}