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
    public EditStaffScreen(Screen a,User user){
        super();
        this.superiorScreen = a;
        this.manager = user;
        this.title = "Editing STAFF";
        options = new ArrayList<>();


    }
    @Override
    protected void chooseOption(){

    }

    @Override
    public void print(){
        clearScreen();
        printHeader();

        printOptionsText();
    }


}
