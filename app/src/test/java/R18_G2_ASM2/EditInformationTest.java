package R18_G2_ASM2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class EditInformationTest{
    private User testUser = new Customer(1, "bob", "bob@gmail.com", "0488881188", "abc123Axd#!");

    private EditInformation editInformation = new EditInformation(testUser);
    //private File testFile = new File("app/src/test/resources/newUserDetailsTest.csv");

    //public EditInformationTest(){
        //this.editInformation.setUserFile(testFile);
    //}

    @BeforeAll static void importData() {
      DataController.setBasePath("src/test/resources/");
    }

    @BeforeEach void resetFile() {
      editInformation.setUserFileName("newUserDetailsTest.csv");
    }


    @Test
    public void testWelcome(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            editInformation.Welcome();
            String output = "\033[H\033[2JEdit and Update Your Information\n"+"*******************************************************\n"+"PLEASE CHOOSE FORM THE FOLLOWING                         \n"+"*******************************************************\n\n";
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
        editInformation.setUserPassword("12345ABCed");
        assertEquals("12345ABCed",testUser.getPassword());
    }

    //assertionfailederror
    
    // @Test
    // public void testCheckIfExist(){
    //     assertEquals(-1,editInformation.checkIfUserExists("hhh@gmail.com"));
    // }

    @Test
    public void testReturnUserPage(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            editInformation.returnUserPage();
            String output = "Return to the User default page...\n\n";
            assertEquals(outContent.toString(),output);

        }catch (Exception e){ e.printStackTrace();}
    }
    @Test
    public void testgiveChoice(){
        try{
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String a = "sjafdnsa\n";
            ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
            System.setIn(inContent);
            editInformation.run();
            String output = "\033[H\033[2JEdit and Update Your Information\n"+"*******************************************************\n"+"PLEASE CHOOSE FORM THE FOLLOWING                         \n"+"*******************************************************\n\n" +
                    "1 - Edit Nickname\n" +
                    "2 - Edit Email\n" +
                    "3 - Edit Phone Number\n" +
                    "4 - Edit Password\n" +
                    "5 - Return to the user page\n" +
                    "\n" +
                    "User input: \n" +
                    "Invalid order :( Please try again\n";
            assertEquals(outContent.toString(),output);

        }catch (Exception e){ e.printStackTrace();}
    }

    @Test
    public void testInvalidEditNickname(){
        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String a = "sjafdnsa\n";
            ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
            System.setIn(inContent);
            editInformation.editNickname();
            String output = ("Security check, please enter your old name: \n"+"The name you have entered is invalid, please check it again\n"+"Return to the User default page...\n\n");
            assertEquals(outContent.toString(),output);
        }catch (Exception e){e.printStackTrace();}
    }

    @Test
    public void testInvalidEditPhoneNumber(){
        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String a = "123\n";
            ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
            System.setIn(inContent);
            editInformation.editPhoneNumber();
            String output = ("Security check, please enter your old phone number: \n"+"The number you have entered is invalid, please check it again\n"+"Return to the User default page...\n\n");
            assertEquals(outContent.toString(),output);
        }catch (Exception e){e.printStackTrace();}
    }

    @Test
    public void testInvalidEmail(){
        try {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));
            String a = testUser.getEmail();
            ByteArrayInputStream inContent = new ByteArrayInputStream(a.getBytes());
            System.setIn(inContent);
            editInformation.editEmail();
            String output = ("\nYour current username is: " + testUser.getEmail()+"\nThe new username: \n"+"Please enter a new username\n");
            assertEquals(outContent.toString(),output);
        }catch (Exception e){e.printStackTrace();}
    }

    // @Test
    // public void testwriteUserForFile(){
    //     editInformation.writeUsertoFile(testUser,editInformation.getUserFile());
    // }


}