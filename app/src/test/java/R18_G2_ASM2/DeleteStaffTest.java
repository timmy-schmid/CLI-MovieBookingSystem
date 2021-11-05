package R18_G2_ASM2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class DeleteStaffTest {
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
        DeleteStaffScreen ds =  new DeleteStaffScreen(mockMovieSystem);
        ds.askForemail();
        assertEquals("Please enter a staff email: \n" +
                "The user input is not a staff\n" +
                "Please try again.\n\n",actualOut.toString());
    }

    @Test
    void testAskForEmailInvaild(){
        mockIn = new ByteArrayInputStream("hdsahdnsagmail.com\n".getBytes());
        System.setIn(mockIn);
        DeleteStaffScreen ds =  new DeleteStaffScreen(mockMovieSystem);
        ds.askForemail();
        assertEquals("Please enter a staff email: \n" +
                "Please enter an email that contains a recipient name, @ symbol and valid domain.\n" +
                "Email address need to match the format\n" +
                "Please try again\n" +
                "\n",actualOut.toString());
    }

    @Test
    void testAskForContinue(){
        mockIn = new ByteArrayInputStream("N\n".getBytes());
        System.setIn(mockIn);
        DeleteStaffScreen ds =  new DeleteStaffScreen(mockMovieSystem);
        ds.askForContinue();
        assertEquals("Do you want to continue?(Y/N)\n",actualOut.toString());
    }

    @Test
    void testdoesStaffExistInCSV(){
        DeleteStaffScreen ds =  new DeleteStaffScreen(mockMovieSystem);
        assertEquals(0,ds.doesStaffExistInCSV("newUserDetails.csv","manager@gmail.com"));
    }
}
