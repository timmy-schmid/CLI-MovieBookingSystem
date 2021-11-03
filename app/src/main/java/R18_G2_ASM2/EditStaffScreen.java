package R18_G2_ASM2;

import java.io.File;
import java.util.ArrayList;

public class EditStaffScreen extends Screen{

    private static User manager;
    private final static UserType type = UserType.STAFF;
    private AddingStaffScreen addingStaffScreen;
    private DeleteStaffScreen deleteStaffScreen;

    // this should be the home page for manager screen
    private Screen superiorScreen;

    //the parameters there should be (ManagerScreen a,User user)
    // user there should be a manager which is confirmed in the home page of staff?
    public EditStaffScreen(MovieSystem mSystem) {
        super(mSystem);
        this.title = "Editing Staff";
        this.maxInputInt =2;
        this.addingStaffScreen = new AddingStaffScreen(mSystem);
        this.deleteStaffScreen = new DeleteStaffScreen(mSystem);
    }

    @Override
    protected void chooseOption(){
        switch (selectedOption) {
            case "1":
                //AddingStaffScreen;
                this.addingStaffScreen.run();
                break;
            case "2":
                //AddingStaffScreen;
                this.deleteStaffScreen.run();
                break;
            case "Q": case "q":
                System.out.print("back to home page of staff");
                goBack = true;
                // break to the home page of staff
                break;
            default: throw new IllegalArgumentException("Critical error - invalid selection passed validation");
        }
    }

    @Override
    protected void setOptions() {
        maxInputInt = 2;
        options.add("Q");options.add("q");
    }

    @Override
    public void print(){
        clearScreen();
        printHeader();
        System.out.println("Welcome "+mSystem.getUser().getNickname()+ "\n");
        printOptionsText();
        System.out.print(formatANSI("1",ANSI_USER_OPTION)+"Add a new staff\n");
        System.out.print(formatANSI("2",ANSI_USER_OPTION)+"Delete a current staff\n");
        System.out.print(formatANSI("Q",ANSI_USER_OPTION)+"back to manager home page\n");
    }

}
