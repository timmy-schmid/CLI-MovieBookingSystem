package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BookingTicketTest {
    private Calendar testcal;
    private List<String> testActor = new ArrayList<>();
    private Movie testMovie;
    private Cinema testCinema;
    private BookingTicket testBookingTicket;
    private User testUser;
    @BeforeAll
    public void setUp(){
        testcal = Calendar.getInstance();
        testActor.add("some name1");
        testActor.add("some name2");
        testUser = new User(277,"abcdhsa@gmail.com","qwertyui");
        Showing aShow = new Showing(1,testMovie,testCinema,testcal);
        testBookingTicket = new BookingTicket(aShow,testUser);
    }
}
