package R18_G2_ASM2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class HomeScreenTest {
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
    public void testConstructor() {
        HomeScreen s =  new HomeScreen(mockMovieSystem);
        assertEquals("CURRENT SHOWINGS (1 NOV - 7 NOV)",s.title);
    }

    @Test public void testInvalidUserInputSimpleInvalidIntegers() {
        mockIn = new ByteArrayInputStream("q\n".getBytes());
        System.setIn(mockIn);

        HomeScreen s =  new HomeScreen(mockMovieSystem);

        s.askforInput();
        Assertions.assertEquals(actualOut.toString(),"User Input:Invalid selection. Please try again.\n" +
                "\n" +
                "User Input:");
    }
}
