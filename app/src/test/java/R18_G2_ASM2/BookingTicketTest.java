package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;

public class BookingTicketTest {
    private Calendar testcal;
    private List<String> testActor = new ArrayList<>();
    private Movie testMovie;
    private Cinema testCinema;
    private BookingTicket testBookingTicket;
    private User testUser;
    private Showing aShow;
    @BeforeAll
    public void setUp(){
        testcal = Calendar.getInstance();
        testActor.add("some name1");
        testActor.add("some name2");
        testUser = new User(277,"abcdhsa@gmail.com","qwertyui");
        aShow = new Showing(1,testMovie,testCinema,testcal);
        testBookingTicket = new BookingTicket(aShow,testUser);
    }

    @Test
    public void testCheckFullorNot(){
        assertFalse(testBookingTicket.checkFullorNot());
    }

    @Test
    public void testPrintBookingMessage(){
        try{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String output = ("Total = "+testUser.getTotalPrice()*50);
            assertEquals(outContent,output);
        }
        catch (Exception e){ e.printStackTrace();
        }
    }
}
