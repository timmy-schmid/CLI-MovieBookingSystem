package R18_G2_ASM2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import R18_G2_ASM2.SeatDataTools.DataFrame;
import R18_G2_ASM2.SeatDataTools.MovieDataFrame;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class MovieSeatTest{

    Showing show;

    MovieDataFrame filmAvailable;

    @BeforeEach
    public void setUp() throws IOException{
        List<String> colNames;
        // colNames = Arrays.asList("col_1", "col_2", "col_3", "col_4", "col_5", "col_6", "col_7");
        colNames = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        String[][] data = { { "Available", "Available",  "Available", "Available", "Available", "Available", "Available" },
                { "Reserved", "Available", "Available", "Available", "Available", "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Available" , "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Reserved" , "Available", "Available"},
                { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"}};
        filmAvailable = new MovieDataFrame(colNames, data);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date1 = null;
        try {
            date1 = df.parse("2017-08-07 11:11:11");
        } catch (ParseException e) {
            e.printStackTrace();
        }
         
       Calendar calendar1 = Calendar.getInstance();
       calendar1.setTime(date1);
       show = new Showing(2, new Movie(1,"77", null, null, null, null,null), new Cinema(1, MovieClass.SILVER), calendar1);
    }

    @Test
    public void movieDataFrameTest(){
        assertNotNull(filmAvailable);
    }


    @Test 
    public void MovieSeatTest() throws IOException {


        MovieSeat seatMap = new MovieSeat(new Showing(2, new Movie(1,"77", null, null, null, null,null), new Cinema(1, MovieClass.SILVER), null), true);
        //DataFrame<String> newFrame = seatMap.readFromDatabase();
        // newFrame.print();

        assertEquals(true, seatMap.bookSeat('A', 1)); 
        assertEquals(true, seatMap.cancelReservation('A', 1));
        assertEquals(true, seatMap.bookSeat('D', 7));
        assertEquals(false, seatMap.bookSeat('D', 7));
        assertEquals(true, seatMap.cancelReservation('D', 7));
        assertEquals(false, seatMap.cancelReservation('D', 7));
        seatMap.bookSeat('D', 5);
        // seatMap.getSeatMap().print();
        // seatMap.cancelReservation('A', 0);
        // seatMap.getSeatMap().print();
        seatMap.showAllSeats();
        System.out.println(seatMap.frontSeatBooked());
        System.out.println(seatMap.middleSeatBooked());
        System.out.println(seatMap.totalSeatsLeft());
        System.out.println(seatMap.totalSeatsBooked());
        // assertEquals(2, seatMap.frontSeatBooked());
        seatMap.showFrontSeats();
        seatMap.showMiddleSeats();
        seatMap.showRearSeats();

        assertNotNull(seatMap.getSeatMap());

        boolean thrown = false;
        try {
            seatMap.bookSeat('.', 1);
        } catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            seatMap.cancelReservation('.', 1);
        } catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        //try {
            //MovieSeat testSeat = new MovieSeat(new Showing(2, new Movie(1,"77", null, null, null, null,null), new Cinema(1, Screen.SILVER), null));
        //} catch (IOException e){
            //thrown = true;
        //}
        
        // assertTrue(thrown);

    }

    @Test 
    public void movieSeatTest() throws IOException{
        boolean thrown = false;
        //try {
            //show.setMovieSeat();
        //} catch (IOException e){
            //thrown = true;
        //}
        show.setMovieSeatForTest();
        assertEquals(6, show.totalSeatsBooked());
        assertEquals(36, show.totalSeatsLeft());
        assertEquals(1, show.frontSeatBooked());
        assertEquals(1, show.middleSeatBooked());
        assertEquals(4, show.rearSeatBooked());
        assertFalse(show.isSeatEmpty());
        assertFalse(show.isShowingFull());

    }

    @Test
    public void movieTempFileTest() throws IOException{
        Showing show = new Showing(2, new Movie(1, null, null, null, null, null, null), new Cinema(1, MovieClass.SILVER), null);
        show.getMovieSeat().showAllSeats();
        File testFile = DataController.accessCSVFile("movieSeatsMap/"+ "1-1-2" +"-temp"+".csv");
        assertTrue(testFile.exists());
        show.resetSeatMap();
        assertFalse(testFile.exists());
    }

    @Test
    public void notExistSeatMap() throws IOException{
        Showing show = new Showing(100, new Movie(1, null, null, null, null, null, null), new Cinema(1, MovieClass.SILVER), null);
        File testFile = DataController.accessCSVFile("movieSeatsMap/"+ "1-1-100" +".csv");
        assertTrue(testFile.exists());
        assertTrue(testFile.delete());
    }
}