package R18_G2_ASM2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AddStaffTest {
    private static ByteArrayInputStream mockIn;
    private static ByteArrayOutputStream actualOut;
    private static MovieSystem mockMovieSystem;


    @BeforeAll
    static void setup() {
        mockMovieSystem = mock(MovieSystem.class);
        actualOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOut));
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
        addingStaffScreen.askForemail();
        assertEquals("Please enter a staff email: \n" +
                "Email address already exists\n" +
                "Please try again\n" +
                "\n" +
                "Please enter a staff name: \n" +
                "Please enter a password: \n" +
                "Please enter a staff phone number:\n",actualOut.toString());
    }

    @Test
    void testAskForEmailRun(){
        mockIn = new ByteArrayInputStream("hp@gmail.com\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.run();
        assertEquals("Please enter a staff email: \n" +
                "Email address already exists\n" +
                "Please try again\n" +
                "\n" +
                "Please enter a staff name: \n" +
                "Please enter a password: \n" +
                "Please enter a staff phone number:\n",actualOut.toString());
    }

    @Test
    void testAskForPhone(){
        mockIn = new ByteArrayInputStream("9027438625347652\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.askForPhone();
        assertEquals("Please enter a staff phone number: \n" +
                "Please enter a 10 digit phone number.\n" +
                "Phone number need to match the format\n" +
                "Please try again\n\n",actualOut.toString());
    }

    @Test
    void testAskForPw(){
        mockIn = new ByteArrayInputStream("dsaiew1223\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.askForPassWord();
        assertEquals("Please enter a password: \n" +
                "Please enter a 10-digit password containing at least 1 capital letter and 1 number.\n",actualOut.toString());
    }

    @Test
    void testAskForEmailInvalid(){
        mockIn = new ByteArrayInputStream("hpgmail.com\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.run();
        assertEquals("Please enter a staff email: \n" +
                "Please enter an email that contains a recipient name, @ symbol and valid domain.\n" +
                "Email address need to match the format\n" +
                "Please try again\n" +
                "\n" +
                "Please enter a staff name: \n" +
                "Please enter a password: \n" +
                "Please enter a staff phone number:\n",actualOut.toString());
    }
    @Test
    void testPrintUMessage(){
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.SuccessfulAdd();

        assertEquals("Staff successfully entered!\n",actualOut.toString());

    }


}
