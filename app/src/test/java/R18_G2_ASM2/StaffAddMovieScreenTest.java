package R18_G2_ASM2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class StaffAddMovieScreenTest {
    private static ByteArrayInputStream mockIn;
    private static ByteArrayOutputStream actualOut;
    private static MovieSystem mockMovieSystem;


    @BeforeAll
    static void setup() {
        mockMovieSystem = mock(MovieSystem.class);
        actualOut = new ByteArrayOutputStream();
    }


    @BeforeEach
    void resetSystem() {
        actualOut.reset();
    }

    @Test
    void testAskForEmailExist(){
        mockIn = new ByteArrayInputStream("hp@gmail.com\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.run();
        System.setOut(new PrintStream(actualOut));
        assertEquals("Email address already exists\nPlease try againom\n",actualOut.toString());
    }

    @Test
    void testAskForPhone(){
        mockIn = new ByteArrayInputStream("9027438625347652\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.askForPhone();
        System.setOut(new PrintStream(actualOut));
        assertEquals("Phone number need to match the format\nPlease try again\n",actualOut.toString());
    }

    @Test
    void testAskForPw(){
        mockIn = new ByteArrayInputStream("dsaiew1223\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.askForPhone();
        System.setOut(new PrintStream(actualOut));
        assertEquals("Please enter a 10-digit password containing at least 1 capital letter and 1 number.\n",actualOut.toString());
    }

    @Test
    void testAskForEmailInvalid(){
        mockIn = new ByteArrayInputStream("hpgmail.com\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.run();
        System.setOut(new PrintStream(actualOut));
        assertEquals("Please enter an email that contains a recipient name, @ symbol and valid domain.\nEmail address need to match the format\nPlease try againom\n",actualOut.toString());
    }
    @Test
    void testPrintUMessage(){
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        actualOut = new ByteArrayOutputStream();
        addingStaffScreen.SuccessfulAdd();
        System.setOut(new PrintStream(actualOut));
        assertEquals("Staff successfully entered!",actualOut.toString());

    }


}
