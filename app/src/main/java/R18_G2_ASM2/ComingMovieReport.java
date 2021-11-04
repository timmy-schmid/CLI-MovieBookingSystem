package R18_G2_ASM2;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;
import java.util.TimeZone;
public class ComingMovieReport extends Screen {

    private ArrayList<Movie> moviesSorted;

    private MovieScreen movScreen;
    private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");
    SimpleDateFormat f = new SimpleDateFormat("EEE dd MMM - K:mma", Locale.ENGLISH);

    public ComingMovieReport(MovieSystem mSystem) {
        super(mSystem);
        f.setTimeZone(AEST);

        if (mSystem.getUser().getUserType() == UserType.GUEST ||
                mSystem.getUser().getUserType() == UserType.CUSTOMER) {
            throw new IllegalArgumentException("Invalid user type for this screen");
        }
        title = "Coming Movie Report";
    }

    @Override
    protected void setOptions() {
        options = new ArrayList<>();
        maxInputInt = 0;
        options.add("R"); options.add("r");
        goBack = false;
    }

    @Override
    protected void chooseOption() {
            switch (selectedOption) {
                case "R": case "r":
                    goBack = true;
                    break;
                default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
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

        printHeader();

        System.out.printf("Welcome %s,\n\n", mSystem.getUser().getNickname());

        printOptionsText();

        System.out.print(formatANSI("R",ANSI_USER_OPTION) + " - To go back to the staff Home Page\n\n");
    }
}
