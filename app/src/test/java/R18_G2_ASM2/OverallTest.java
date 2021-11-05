package R18_G2_ASM2;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class OverallTest{

    public void setInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    public void runBookingTicketTest1() throws IOException{
        try{
            // String inputMessage = "1\n"
            // + "manager@gmail.com\n"
            // + "000\n"
            // + "1\n"
            // + "manager@gmail.com\n"
            // + "Testone1234!\n"
            // + "Q\n"
            // + "";

            String inputMessage = "C\n";


            setInput(inputMessage);
        
            // MovieSystem system = new MovieSystem();
            // system.run();
            Showing show = new Showing(2, new Movie(1, null, null, null, null, null, null), new Cinema(1, MovieClass.SILVER), null);
            Customer user = new Customer(1,  "bob", "bobbie@gmail.com", "0412345678", "Password123");
            BookingTicket bt = new BookingTicket(show, user);
            bt.run();

        } catch (NullPointerException e){
        }
    }

    public void runBookingTicketTest2() throws IOException{
        try{
            // String inputMessage = "1\n"
            // + "manager@gmail.com\n"
            // + "000\n"
            // + "1\n"
            // + "manager@gmail.com\n"
            // + "Testone1234!\n"
            // + "Q\n"
            // + "";

            String inputMessage = "C\n";


            setInput(inputMessage);
        
            // MovieSystem system = new MovieSystem();
            // system.run();
            Showing show = new Showing(2, new Movie(1, null, null, null, null, null, null), new Cinema(1, MovieClass.SILVER), null);
            Customer user = new Customer(1,  "bob", "bobbie@gmail.com", "0412345678", "Password123");
            BookingTicket bt = new BookingTicket(show, user);
            bt.run();

        } catch (NullPointerException e){
        }
    }
}