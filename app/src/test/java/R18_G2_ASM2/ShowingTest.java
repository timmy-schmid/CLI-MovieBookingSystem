package R18_G2_ASM2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class ShowingTest{

  @BeforeAll public static void setPath() {
    DataController.setBasePath("src/main/resources/");
  }

    Showing show;
    @BeforeEach
    public void setUp() throws IOException{
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
    public void getShowInfoTest() throws IOException{

       assertEquals(show.getShowingId(), 2);
       assertNotNull(show.getCinema());
       assertNotNull(show.getMovie());
       assertNull(show.getMovieSeat());
       assertEquals("MON 07 AUG - 11:11AM", show.getShowingTimeFormatted());
       assertEquals("MON 11:11AM",show.getShowingTimeShort());
    }

    @Test 
    public void movieSeatTest() throws IOException{
        boolean thrown = false;
        try {
            show.setMovieSeat();
        } catch (IOException e){
            thrown = true;
        }
        show.setMovieSeatForTest();
        // assertEquals(8, show.totalSeatsBooked());
        // assertEquals(34, show.totalSeatsLeft());
        // assertEquals(2, show.frontSeatBooked());
        // assertEquals(1, show.middleSeatBooked());
        // assertEquals(5, show.rearSeatBooked());
        assertFalse(show.isSeatEmpty());
        assertFalse(show.isShowingFull());

    }

    @Test
    public void showingTest(){
        assertEquals("1:MON 07 AUG - 11:11AM:77", show.toString());
    }

}
