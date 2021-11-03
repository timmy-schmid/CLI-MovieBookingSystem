package R18_G2_ASM2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class AddingStaffScreen{
    private MovieSystem movieSystem;
    private User usertobeAdd;
    private boolean goBack = false;
//    private String userEmail;
//    private String username;
//    private String password;
//    private int ID;
//    private String phoneNum;
    private Scanner scan;
    private ArrayList<String> option = new ArrayList<>();
    private int step_num = 0;
    private String input;
    private static String USER_FILE_NAME = "newUserDetails.csv";
    private File userCsvFile;
    private Staff checkinguser = new Staff (999,"check","checkingEmailcannotmTach@gmail.com","91654391234","checking1234!*");


    public AddingStaffScreen(MovieSystem mSystem) {
        this.movieSystem = mSystem;

        try {
            userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to edit User: " + e.getMessage());
            return;
        }

        /*
        option.add("Please enter a staff name: ");
        option.add("Please enter a staff password: ");
        option.add("Please enter a staff phone: ");

         */
    }

    public void run(){

        while(!goBack){
            scan =  new Scanner(System.in);
            checkinguser = new Staff (999,"check","checkingEmailcannotmTach@gmail.com","91654391234","checking1234!*");
            askForemail();
            askForName();
            askForPassWord();
            askForPhone();
           // scan.close();
            changeCheckingUserId();
            try {
                checkinguser.writeNewUserToCSV(USER_FILE_NAME);
            }catch (Exception e){
                e.printStackTrace();
            }
            goBack = true;
        }

    }

    public void printUserMessage(){
        System.out.println(checkinguser.getUserInformation());
    }

    public void SuccessfulAdd(){
        System.out.println("Staff successfully entered!");

    }

    public void emailExist(){
        System.out.println("Email address already exists\nPlease try again\n");
    }
    public void InvaLidEmail(){
        System.out.println("Email address need to match the format\nPlease try again\n");
    }

    public void phoneExist(){
        System.out.println("Phone number already exists\nPlease try again\n");
    }
    public void InvaLidPhone(){
        System.out.println("Phone number need to match the format\nPlease try again\n");
    }

    public void askForemail(){
        System.out.println("Please enter a staff email: ");
        while(scan.hasNextLine()){
            String inputString = scan.nextLine();

            //check if match the format or already exist
            try {
                if(!checkinguser.validateUser(inputString)){
                    throw new Exception("1");
                }if(checkinguser.doesUserExistInCSV(USER_FILE_NAME,inputString,checkinguser.phoneNumber) == 1){
                    throw new Exception("2");
                }
                // if not correct format ask again;
            }catch (Exception e){
                if(e.getMessage().equals("1")){
                    InvaLidEmail();
                    continue;
                }else if(e.getMessage().equals("2")){
                    emailExist();
                    continue;
                }
                continue;
            }
            // if the email is valid
            checkinguser.setEmail(inputString);
            break;
        }
    }

    public void askForName(){
        System.out.println("Please enter a staff name: ");
        Scanner scan =  new Scanner(System.in);
        while(scan.hasNextLine()){
            String inputString = scan.nextLine();
            checkinguser.setNickname(inputString);
            break;
        }
//        scan.close();
    }

    public void askForPassWord(){
        System.out.println("Please enter a password: ");
        Scanner scan =  new Scanner(System.in);
        while(scan.hasNextLine()){
            String inputString = scan.nextLine();

            //check if match the format
            try {
                if(!checkinguser.isValidPassword(inputString)){
                    throw new Exception("1");
                }

                // if not correct format ask again;
            }catch (Exception e){
                if(e.getMessage().equals("1")){
                    continue;
                }
                continue;
            }
            // if the pw is valid
            checkinguser.setPassword(inputString);
            break;
        }
//        scan.close();
    }


    public void askForPhone(){
        System.out.println("Please enter a staff phone number: ");
        Scanner scan =  new Scanner(System.in);
        while(scan.hasNextLine()){
            String inputString = scan.nextLine();

            //check if match the format or already exist
            try {
                if(!checkinguser.isValidPhoneNumber(inputString)){
                    throw new Exception("1");
                }if(checkinguser.doesUserExistInCSV(USER_FILE_NAME,checkinguser.getEmail(),inputString) == 2){
                    throw new Exception("2");
                }
                // if not correct format ask again;
            }catch (Exception e){
                if(e.getMessage().equals("1")){
                    InvaLidPhone();
                    continue;
                }else if(e.getMessage().equals("2")){
                    phoneExist();
                    continue;
                }
                continue;
            }
            // if the phone is valid
            checkinguser.setPhoneNumber(inputString);
            break;
        }
    }

    public void changeCheckingUserId(){
        try{
            checkinguser.setID(User.getLastUserIDFromCSV(USER_FILE_NAME));}
        catch (Exception e){
            e.printStackTrace();
        }
    }




}
