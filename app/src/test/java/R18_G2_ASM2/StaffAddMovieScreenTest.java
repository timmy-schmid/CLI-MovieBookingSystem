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
        System.setOut(new PrintStream(actualOut));
    }


    @BeforeEach
    void resetSystem() {
        actualOut.reset();
    }

    void testAskForEmailExist(){
        mockIn = new ByteArrayInputStream("hp@gmail.com\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.askForemail();
        assertEquals("Email address already exists\nPlease try againom\n",actualOut.toString());
    }

    void testAskForEmailInvalid(){
        mockIn = new ByteArrayInputStream("hpgmail.com\n".getBytes());
        System.setIn(mockIn);
        AddingStaffScreen addingStaffScreen =  new AddingStaffScreen(mockMovieSystem);
        addingStaffScreen.askForemail();
        assertEquals("Please enter an email that contains a recipient name, @ symbol and valid domain.\nEmail address need to match the format\nPlease try againom\n",actualOut.toString());
    }


}
