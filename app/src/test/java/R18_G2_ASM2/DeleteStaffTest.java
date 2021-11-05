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

//    @Test
//    void testAskForEmailExist(){
//        mockIn = new ByteArrayInputStream("hp@gmail.com\n".getBytes());
//        System.setIn(mockIn);
//        DeleteStaffScreen ds =  new DeleteStaffScreen(mockMovieSystem);
//        ds.run();
//        assertEquals("The user input is not a staff\nPlease try again.\n",actualOut.toString());
//    }

}
