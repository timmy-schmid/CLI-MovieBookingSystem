package R18_G2_ASM2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class EditInformationTest{
    private User testUser = new User(277,"abcdhsa@gmail.com","123Qwertyui");
    private EditInformation editInformation = new EditInformation(testUser);

    @Test
    public void testWelcome(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            editInformation.Welcome();
            String output = "Edit and Update Your Information\n"+"*******************************************************\n"+"PLEASE CHOOSE FORM THE FOLLOWING                         \n"+"*******************************************************\n";
            assertEquals(outContent.toString(),output);

        }catch (Exception e){ e.printStackTrace();}
    }

    @Test
    public void testnextOption(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            editInformation.nextOption();
            String output = "Return to the Edit Option page...\n\n";
            assertEquals(outContent.toString(),output);

        }catch (Exception e){ e.printStackTrace();}
    }

    @Test
    public void testsetUserEmail(){
        editInformation.setUserEmail("abcd@gmail.com");
        assertEquals("abcd@gmail.com",testUser.getEmail());
    }

    @Test
    public void testUserPassowrd(){
        editInformation.setUserPassword("1234Qwds");
        assertEquals("1234Qwds",testUser.getPassword());
    }
}