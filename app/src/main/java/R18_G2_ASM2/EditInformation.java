package R18_G2_ASM2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EditInformation {
    /*
    This class: print screen for when user clicks "E" in the welcome page of user
    and edit the information of it
    change the user.csv data including card details
     */
    private File userCsvFile = new File("src/main/datasets/user1.csv");
    private User userChanged;

    // call from the default screen
    public EditInformation(User aUser){
        this.userChanged = aUser;
    }

    public File getUserFile(){
        return this.userCsvFile;
    }

    public void setUserFile(File name){
        this.userCsvFile = name;
    }
    public void giveChoice(){
        this.Welcome();
        Scanner scan = new Scanner(System.in);
        System.out.println("1 - Edit Email\n" +
                "2 - Edit Password\n" +
                "3- return to the user page\n");
        System.out.println("User input: ");
        String option = scan.nextLine();
        if(option.equals("1")){
            this.editEmail();
        }
        else if(option.equals("2")){this.editPassword();}
        else if(option.equals("3")){this.returnUserPage();}
        else{
            System.out.println("Invalid order :( Please try again");
        }

    }

    public void Welcome(){
        System.out.println("Edit and Update Your Information");
        System.out.println("*******************************************************");
        System.out.println("PLEASE CHOOSE FORM THE FOLLOWING                         ");
        System.out.println("*******************************************************\n");
    }

    public void editEmail(){
        boolean Success = false;
        boolean wanttoContinue = true;
        System.out.println("\nYour current username is: " + userChanged.getEmail());
        Scanner scan = new Scanner(System.in);
        System.out.println("The new username: ");
        while(!Success && wanttoContinue){
            String option = scan.nextLine();
            if(option.equals(this.userChanged.getEmail())){
                System.out.println("Please enter a new username");
            }
            if(this.validateUser(option)){
                if (this.checkIfUserExists(option) == 1){
                    this.userChanged.setEmail(option);
                    Success = true;
                }else{
                    System.out.println("The username already exists\n");
                }
            }else {
                System.out.println("Invalid Username :( Please use gmail, hotmail or yahoo email address.");
                System.out.println("Do you wanna try again? (Y/N)\n");
                if(option.equals("Y")){}else if(option.equals("N")){
                    wanttoContinue = false;
                }else{
                    System.out.println("Invalid input.Please choose from Y or N\n");
                }
            }
        }
        if(wanttoContinue == false){
            this.nextOption();
        }
    }

    public void editPassword(){
        Scanner scan = new Scanner(System.in);
        boolean Success = false;
        boolean wanttoContinue = true;
        while (!Success && wanttoContinue) {
            System.out.println("Security check, please enter your old password: ");
            String pw = scan.nextLine();
            if (pw.equals(this.userChanged.getPassword())) {
                System.out.println("New password: ");
                if(this.isValidPassword(pw)){
                    this.userChanged.setPassword(pw);
                    Success = true;
                }
            } else {
                System.out.println("The password is incorrect. Please check it again");
            }
            System.out.println("Do you wanna try again? (Y/N)\n");
            if(pw.equals("Y")){}else if(pw.equals("N")){
                wanttoContinue = false;
            }else{
                System.out.println("Invalid input.Please choose from Y or N\n");
            }
        }
        if(!wanttoContinue){
            this.nextOption();
        }
    }


//    public void setUserEmail(String email){
//        this.userChanged.setEmail(email);
//    }
//
//    public void setUserPassword(String password) {
//        this.userChanged.setPassword(password);
//    }

    // same as the Registration method check the username whether matches format or not
    public boolean validateUser(String email){ //or email
        //should contain: @ + .com
        String emailRegex = "^.*\\w@(gmail|hotmail|yahoo)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (pattern.matcher(email).matches()){
            return true;
        } else {
            System.out.println("Your email did not satisfy acceptance criteria.");
            return false;
        }
    }


    //same as the Registration method to check the duplicate user name
    public int checkIfUserExists(String userEmail){
        int userID = 1;
        String email = null;
        String password = null;
        int result = 1;

        try {
            Scanner myReader = new Scanner(userCsvFile);
            while (myReader.hasNextLine()) { //as long as you can keep reading the file, grab the details
                String line = myReader.nextLine();
                String[] detailsArray = line.split(",");
                try{
                    userID = Integer.parseInt(detailsArray[0]);
                } catch(NumberFormatException e){
                    e.printStackTrace();
                    break;
                }
                email = detailsArray[1];
                if(userEmail.equals(email)){
                    result = -1;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf("FILE NOT FOUND ERROR: %s FILE NOT FOUND!", this.userCsvFile);
        }
        return result;
    }


    //same as the Registration method to check the password is valid
    public boolean isValidPassword(String password){
        //now, use regex to ensure it contains a mixture of letters + numbers + symbols (--> optional?, allow whitespace or NAH?)

        //\\w === [a-zA-Z_0-9]
        // String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).{10,}$";
        String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        if (pattern.matcher(password).matches()){
            return true;
        } else {
            System.out.println("Your password did not satisfy acceptance criteria.");
            return false;
        }
    }

    public void nextOption(){
        System.out.println("Return the Edit Option page...\n");
    }

    public void returnUserPage(){
        System.out.println("Return the User default page...\n");
        this.giveChoice();
    }
}
