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
    }

    @Override
    protected void chooseOption(){
        if(intOption != NO_INT_OPTION){

        }
    }

    @Override
    protected void setOptions() {
        options = new ArrayList<>();
        options.add("1");
        options.add("2");
    }
    @Override
    public void print(){
        clearScreen();
        printHeader();
        System.out.println("Welcome %s\n",mSystem.getUser().getNickname());
        printOptionsText();
        System.out.print(formatANSI("1",ANSI_USER_OPTION)+"Add a new staff\n");
        System.out.print(formatANSI("2",ANSI_USER_OPTION)+"Delete a current staff\n");
        System.out.print(formatANSI("Q",ANSI_USER_OPTION)+"back to manager home page\n");
    }

}
