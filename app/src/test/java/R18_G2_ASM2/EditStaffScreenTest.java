package R18_G2_ASM2;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;


public class EditStaffScreenTest {
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
        EditStaffScreen testEd = new EditStaffScreen(mockMovieSystem);
        assertEquals("Editing Staff",testEd.title);
    }

    @Test
    public void testPrint(){
        EditStaffScreen testEd = new EditStaffScreen(mockMovieSystem);
        testEd.printHeader();
        assertEquals(actualOut.toString(),("******************************************************************************************\n" +
                                           "                                      Editing Staff                                      \n" +
                                           "******************************************************************************************\n"));
    }

    @Test public void testValidInputBasicCases() {
        EditStaffScreen s = new EditStaffScreen(mockMovieSystem);
        s.setOptions();

        mockIn = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(mockIn);
        s.askforInput();
        assertEquals(actualOut.toString(),"User Input:");
        actualOut.reset();

        mockIn = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(mockIn);
        s.askforInput();
        assertEquals(actualOut.toString(),"User Input:");
        actualOut.reset();

        mockIn = new ByteArrayInputStream("q\n".getBytes());
        System.setIn(mockIn);
        s.askforInput();
        assertEquals(actualOut.toString(),"User Input:");
        actualOut.reset();
    }


}
