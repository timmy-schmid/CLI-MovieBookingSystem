package R18_G2_ASM2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingTicketTest {
    private Calendar testcal;
    private List<String> testActor = new ArrayList<>();
    private Movie testMovie;
    private Cinema testCinema;
    private BookingTicket testBookingTicket;
    private Customer testUser;
    private Showing aShow;

    @BeforeEach
    public void setUp() throws IOException{
        testMovie = new Movie(2, null, null, null, null, null, null);
        testCinema = new Cinema(2, MovieClass.GOLD);
        testcal = Calendar.getInstance();
        testActor.add("some name1");
        testActor.add("some name2");
        
        testUser = new Customer(277, "lisa", "abcdhsa@gmail.com", "0411123432","123Qwertyui");
        aShow = new Showing(1,testMovie,testCinema,testcal);
        aShow.setMovieSeat();
        testBookingTicket = new BookingTicket(aShow,testUser);
    }

    @Test
    public void testCancelBooking(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            testBookingTicket.printBookingMessage();
            String output = (testUser.getTicketMessage()+
                    "\nTotal = "+testUser.getTotalPrice()*50+"\n");
            assertEquals(outContent.toString(),output);
        }
        catch (Exception e){ e.printStackTrace();
        }
    }
    @Test
    public void testPrintBookingMessage(){
        try{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            testBookingTicket.printBookingMessage();
            String output = ("\nTotal = "+testUser.getTotalPrice()*50+"\n");
            assertEquals(outContent.toString(),output);
        }
        catch (Exception e){ e.printStackTrace();
        }
    }

    @Test
    public void setTestBookingTicketForPerson(){
        testUser.bookingTicket(Person.Child,1);
        assertEquals(0.5,testUser.getTotalPrice());
    }

    // @Test void testInvalidContinue(){
    //     try{
    //         String a = "-34\r\n";
    //         ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
    //         System.setIn(inContent);
    //         ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    //         System.setOut(new PrintStream(outContent));
    //         assertEquals(3,testBookingTicket.Continue());
    //     } catch (Exception e){ e.printStackTrace();}
    // }

    @Test
    public void testContinue(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String a = "1\n";
            ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
            System.setIn(inContent);
            assertEquals(1, testBookingTicket.Continue());

        }catch (Exception e){ e.printStackTrace();}
    }

    @Test
    public void testContinueEnd(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String a = "2\n";
            ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
            System.setIn(inContent);
            assertEquals(2, testBookingTicket.Continue());

        }catch (Exception e){ e.printStackTrace();}
    }

//    @Test
//    public void testAskForBooking(){
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//        Integer a = -1;
//        ByteArrayInputStream inContent = new ByteArrayInputStream(new byte[]{a.byteValue()});
//        System.setIn(inContent);
//        testBookingTicket.askForBooking();
//        assertEquals("Invalid input,please try again", outContent.toString());
//    }
    @Test
    public void testInvalidCancelSeatForShow(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String a = "2\n";
            ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
            System.setIn(inContent);
            testBookingTicket.cancelSeatForShow('D',999);
            assertEquals("Invalid message cancel seat for show, please try again.\n",outContent.toString());

        }catch (Exception e){ e.printStackTrace();}

    }
    // @Test
    // public void testCheckFullorNot(){
    //     assertEquals(aShow.isShowingFull(),testBookingTicket.checkFullorNot());}

    @Test
    public void testCancellingBookingForPerson(){
        testBookingTicket.bookingTicketForPersons(Person.Child,1);
        testBookingTicket.cancellingBookingForPerson(Person.Child,1);
        assertEquals(0,testUser.getTotalPrice());
    }

}
