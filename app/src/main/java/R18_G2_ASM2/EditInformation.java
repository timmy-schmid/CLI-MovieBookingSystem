package R18_G2_ASM2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

//this class acts as user settings that can modify a user's details
public class EditInformation extends UserFields { 
    /*
    This class: print screen for when user clicks "E" in the welcome page of user
    and edit the information of it
    change the user.csv data including card details
     */
    // private File userCsvFile = new File("src/main/datasets/user1.csv");

    private static String USER_FILE_NAME = "newUserDetails.csv";
    private static String TEMP_FILE_NAME = "TempUser1.csv";
    private File userCsvFile;
    private User userChanged;
    private File tempFile;

    // call from the default screen
    public EditInformation(User aUser){
        this.userChanged = aUser;
        userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
        tempFile = DataController.accessCSVFile(TEMP_FILE_NAME);
    }

    public void setUserFileName(String s){
      USER_FILE_NAME = s;
  }

    public File getUserFile(){
        return this.userCsvFile;
    }

    public void setUserFile(File name){
        this.userCsvFile = name;
    }
    public void run(){
        this.Welcome();
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()){
            System.out.println("1 - Edit Nickname\n" +
                               "2 - Edit Email\n" +
                               "3 - Edit Phone Number\n" +
                               "4 - Edit Password\n" +
                               "5 - Return to the user page\n");
            System.out.println("User input: ");
            String option = scan.nextLine();
            if (option.equals("1")) {
                this.editNickname();
                break;
            } else if (option.equals("2")) {
                this.editEmail();
                break;
            } else if (option.equals("3")) {
                this.editPhoneNumber();
                break;
            } else if (option.equals("4")) {
                this.editPassword();
                break;
            } else if (option.equals("5")) {
//                this.returnUserPage();
                break;
            } else{
                System.out.println("Invalid order :( Please try again");
            }
        }
    }

    public void Welcome(){
        System.out.println("\033[H\033[2JEdit and Update Your Information");
        System.out.println("*******************************************************");
        System.out.println("PLEASE CHOOSE FORM THE FOLLOWING                         ");
        System.out.println("*******************************************************\n");
    }

    public void editEmail(){
        //version one
//        boolean Success = false;
//        boolean wantToContinue = true;
//        System.out.println("\nYour current username is: " + userChanged.getEmail());
//        Scanner scan = new Scanner(System.in);
//        System.out.println("The new username: ");
//        while(!Success && wantToContinue){
//            String option = scan.next();
//            if(option.equals(this.userChanged.getEmail())){
//                System.out.println("Please enter a new username");
//            }
//            else if(this.validateUser(option)){
//                if (this.checkIfUserExists(option) == 1){
//                    this.setUserEmail(option);
//                    Success = true;
//                    System.out.println("Success\n");
//                }else{
//                    System.out.println("The username already exists!\n");
//                }
//            }
//            else {
//                System.out.println("Invalid Username :( Please use gmail, hotmail or yahoo email address.");
//                System.out.println("Do you wanna try again? (Y/N): \n");
//                if(option.equals("Y")){} else if(option.equals("N")){
//                    wantToContinue = false;
//                }else{
//                    System.out.println("Invalid input. Please choose from Y or N: \n");
//                }
//            }
//        }
//        if(wantToContinue == false){
//            this.nextOption();
//        }

        System.out.println("\nYour current username is: " + userChanged.getEmail());
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("The new username: ");
            String option = scan.next();
            if(option.equals(this.userChanged.getEmail())){
                System.out.println("Please enter a new username");
            }
            else if(this.validateUser(option)){
                if (this.checkIfUserExists(option) == 1){
                    this.setUserEmail(option);
                    System.out.println("Success\n");
                    break;
                }else{
                    System.out.println("The username already exists!\n");
                }
            }
            else {
                System.out.println("Invalid Username :( Please use gmail, hotmail or yahoo email address.");
                System.out.println("Do you wanna try again? (Y/N): \n");
                if(option.equals("Y")){} else if(option.equals("N")){
                    break;
                }else{
                    System.out.println("Invalid input. Please choose from Y or N: \n");
                }
            }
        }
    }


    public void editPassword(){
        Scanner scan = new Scanner(System.in);
        boolean Success = false;
        boolean wantToContinue = true;
        while (!Success && wantToContinue) {
            System.out.println("Security check, please enter your old password: ");
            String pw = scan.nextLine();
            if (pw.equals(this.userChanged.getPassword())) {
                System.out.println("New password: ");
                if(this.isValidPassword(pw)){
                    this.setUserPassword(pw);
                    Success = true;
                }
            } else {
                System.out.println("The password is incorrect. Please check it again");
            }
            System.out.println("Do you wanna try again? (Y/N)\n");
            String option = scan.nextLine();
            if(option.equals("Y")){}else if(option.equals("N")){
                wantToContinue = false;
            }else{
                System.out.println("Invalid input. Please choose from Y or N\n");
            }
        }
        if(!wantToContinue){
            this.nextOption();
        }
    }

        //newly added - Anna (editNickname, editPhoneNumber)
    //change the logic - Ke
    public void editNickname(){
        //version 1
//        Scanner scan = new Scanner(System.in);
//        boolean Success = false;
//        boolean wantToContinue = true;
//        while (!Success && wantToContinue) {
//            System.out.println("Security check, please enter your old name: ");
//            String nickname = scan.nextLine();
//            if (nickname.equals(this.userChanged.getNickname())) {
//                System.out.println("New nickname: ");
//                this.setUserNickname(nickname);
//                Success = true;
//            } else {
//                System.out.println("The name you have entered is invalid, please check it again");
//            }
//            System.out.println("Do you wanna try again? (Y/N)\n");
//            String option = scan.nextLine();
//            if(option.equals("Y")){} else if(option.equals("N")){
//                wantToContinue = false;
//            }else{
//                System.out.println("Invalid input. Please choose from Y or N.\n");
//            }
//        }
//        if(!wantToContinue){
//            this.nextOption();
//        }

        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            System.out.println("Security check, please enter your old name: ");
            String nickname = scan.nextLine();
            if (nickname.equals(this.userChanged.getNickname())) {
                System.out.println("New nickname: ");
                String newnickname = scan.next();
                this.setUserNickname(newnickname);
                System.out.println("Your nickname now is "+this.userChanged.getNickname());
                break;
            } else {
                System.out.println("The name you have entered is invalid, please check it again");
            }
            System.out.println("Do you wanna try again? (Y/N)\n");
            String option = scan.nextLine();
            if(option.equals("Y")){
            } else if(option.equals("N")){
                break;
            }else{
                System.out.println("Invalid input. Please choose from Y or N.\n");
            }
        }
        this.returnUserPage();

    }

    public void editPhoneNumber(){
        //version 1
//        Scanner scan = new Scanner(System.in);
//        boolean Success = false;
//        boolean wantToContinue = true;
//        while (!Success && wantToContinue) {
//            System.out.println("Security check, please enter your old phone number: ");
//            String phoneNumber = scan.nextLine();
//            if (phoneNumber.equals(this.userChanged.getPhoneNumber())) {
//                System.out.println("New phone number: ");
//                this.setUserPhoneNumber(phoneNumber);
//                Success = true;
//            } else {
//                System.out.println("The number you have entered is invalid, please check it again");
//            }
//            System.out.println("Do you wanna try again? (Y/N)\n");
//            String option = scan.nextLine();
//            if(option.equals("Y")){} else if(option.equals("N")){
//                wantToContinue = false;
//            }else{
//                System.out.println("Invalid input. Please choose from Y or N.\n");
//            }
//        }
//        if(!wantToContinue){
//            this.nextOption();
//        }

        Scanner scan = new Scanner(System.in);
        boolean Success = false;
        boolean wantToContinue = true;
        while (true) {
            System.out.println("Security check, please enter your old phone number: ");
            String phoneNumber = scan.nextLine();
            if (phoneNumber.equals(this.userChanged.getPhoneNumber())) {
                System.out.println("New phone number: ");
                String newpN = scan.next();
                this.setUserPhoneNumber(phoneNumber);
                System.out.println("The current phone number: " + this.userChanged.getPhoneNumber());
                break;
            } else {
                System.out.println("The number you have entered is invalid, please check it again");
            }
            System.out.println("Do you wanna try again? (Y/N)\n");
            String option = scan.nextLine();
            if(option.equals("Y")){

            } else if(option.equals("N")){
                break;
            }else{
                System.out.println("Invalid input. Please choose from Y or N.\n");
            }
        }

            this.returnUserPage();
    }


    public void setUserNickname(String nickname){
        this.userChanged.setNickname(nickname);
        this.writeUsertoFile(this.userChanged,this.userCsvFile);
    }

    public void setUserEmail(String email){
        this.userChanged.setEmail(email);
        this.writeUsertoFile(this.userChanged,this.userCsvFile);

    }

    public void setUserPassword(String password) {
        this.userChanged.setPassword(password);
        this.writeUsertoFile(this.userChanged,this.userCsvFile);
    }

    //newly added - Anna
    public void setUserPhoneNumber(String phoneNumber) {
        this.userChanged.setPhoneNumber(phoneNumber);
        this.writeUsertoFile(this.userChanged, this.userCsvFile);
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
                email = detailsArray[2];
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


    public void nextOption(){
        System.out.println("Return to the Edit Option page...\n");
    }

    public void returnUserPage(){
        System.out.println("Return to the User default page...\n");
    }

    public void writeUsertoFile(User user, File file) {
        try {
            Scanner myReader = new Scanner(file);
            FileWriter myWriter = new FileWriter(tempFile);
            while ((myReader.hasNextLine())){
                String str = myReader.nextLine();
                if (str.split(",")[0].equals(Integer.toString(user.getID()))){
                    String[] arr = str.split(",");
                    // myWriter.write(arr[0]+","+user.getEmail()+","+user.getPassword());


                    //updated to include phone num
                    myWriter.write(arr[0]+","+user.getNickname()+","+
                    
                    user.getEmail()+"," + user.getPhoneNumber() + "," + user.getPassword() + "," + arr[5]);

                }else{
                    myWriter.write(str+"\n");
                }
                }
            myWriter.close();
            myReader.close();
            } catch (FileNotFoundException e) {
            System.out.printf("FILE NOT FOUND ERROR: %s FILE NOT FOUND!", this.userCsvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean success = tempFile.renameTo(file);
    }
}
