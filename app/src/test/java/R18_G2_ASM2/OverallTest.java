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
            String inputMessage = "2\n"
            + "1\n"
            + "2\n"
            + "F\n"
            + "1\n"
            + "Q\n";

            // String inputMessage = "C\n";


            setInput(inputMessage);
        
            // MovieSystem system = new MovieSystem();
            // system.run();
            Showing show = new Showing(2, new Movie(1, null, null, null, null, null, null), new Cinema(1, MovieClass.SILVER), null);
            Customer user = new Customer(1,  "bob", "bobbie@gmail.com", "0412345678", "Password123");
            BookingTicket bt = new BookingTicket(show, user);
            bt.run();
            // bt.bookingShowingSection();
            // bt.bookingASeat();

        } catch (NullPointerException e){
        }
    }

    public void runBookingTicketTest3() throws IOException{
        Showing show = new Showing(2, new Movie(1, null, null, null, null, null, null), new Cinema(1, MovieClass.SILVER), null);
        Customer user = new Customer(1,  "bob", "bobbie@gmail.com", "0412345678", "Password123");
        BookingTicket bt = new BookingTicket(show, user);
        try{
            String inputMessage = "E\n"
            + "2\n"
            + "C\n";

            // String inputMessage = "C\n";


            setInput(inputMessage);
        
            // MovieSystem system = new MovieSystem();
            // system.run();

            // bt.run();
            // bt.bookingShowingSection();
            bt.bookingASeat();

        } catch (NullPointerException e){
        }
    }

    @Test
    public void testStaffScreen(){
        MovieSystem system = new MovieSystem();
        Manager staff = new Manager(500, "nickname", "email", "phoneNumber", "password");
        system.setUser(staff);
        StaffScreen sfc = new StaffScreen(system);
        sfc.setOptions();
        sfc.print();

        String inputMessage = "2\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        sfc.chooseOption();

        // inputMessage = "1\n";
        // setInput(inputMessage);
        // sfc.askforInput();
        // sfc.setOptions();
        // sfc.chooseOption();

        inputMessage = "3\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        sfc.chooseOption();

        inputMessage = "4\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        sfc.chooseOption();

        inputMessage = "5\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        // sfc.chooseOption();

        inputMessage = "6\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        // sfc.chooseOption();

        inputMessage = "7\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        sfc.chooseOption();

        inputMessage = "8\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        sfc.chooseOption();

        inputMessage = "9\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        // sfc.chooseOption();

        inputMessage = "q\n";
        setInput(inputMessage);
        sfc.askforInput();
        sfc.setOptions();
        // sfc.chooseOption();

    }

}