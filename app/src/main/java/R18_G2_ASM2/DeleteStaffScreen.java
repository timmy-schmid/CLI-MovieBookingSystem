package R18_G2_ASM2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DeleteStaffScreen {
    private MovieSystem movieSystem;
    private User checkinguser;
    private User BeingDeleted;
    private ArrayList<Staff> currentStaffList;
    private String nameToDelete;
    private static String USER_FILE_NAME = "newUserDetails.csv";
    private boolean goBack = false;
    private boolean haveAuser = false;
    private File userCsvFile;

    public DeleteStaffScreen(MovieSystem movieSystem){
        try {
           userCsvFile = DataController.accessCSVFile(USER_FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to delete staff: " + e.getMessage());
            return;
        }
        this.movieSystem = movieSystem;
        this.checkinguser=movieSystem.getUser();
    }


    public void run() {
        while(!goBack){
            haveAuser = false;
            askForemail();
            if(haveAuser){
                this.deleteUserFromFile(nameToDelete);
                System.out.println("Successfully delete. Returning to the Edit Staff page ...");
                    goBack = true;
            }
        }
    };

    public String getUserFileName(){
        return this.USER_FILE_NAME;
    }

    // message method
    public void  EmailnotExist(){
        System.out.println("User don't exist\n Please try again.\n");
    }

    public void EmailExistAsAClient(){System.out.println("The user input is not a staff\nPlease try again.\n");}


    public void askForemail(){
        System.out.println("Please enter a staff email: ");
        Scanner scan =  new Scanner(System.in);
        while(scan.hasNextLine()){
            String inputString = scan.nextLine();

            //check if match the format or already exist
            try {
                if(!checkinguser.validateUser(inputString)){
                    throw new Exception("1");
                }if(checkinguser.doesUserExistInCSV(USER_FILE_NAME,inputString,"9000010000") != 1){
                    throw new Exception("2");
                }if (doesStaffExistInCSV(USER_FILE_NAME,inputString) == 0){
                    throw new Exception("3");
                }if (doesStaffExistInCSV(USER_FILE_NAME,inputString) == -2){
                    throw new Exception("4");}

                // if not correct format ask again;
            }catch (Exception e){
                if(e.getMessage().equals("1")){
                    InvaLidEmail();
                    continue;
                }else if(e.getMessage().equals("2")){
                    EmailnotExist();
                    continue;
                }else if(e.getMessage().equals("3")){
                    EmailExistAsAClient();
                    continue;}
                else if(e.getMessage().equals("4")){
                    System.out.println("4");
                    continue;}
                continue;
            }

            nameToDelete = inputString;
            break;
        }
        //scan.close();
    }


    public void askForContinue(){
        System.out.println("Do you want to continue?(Y/N)");
        Scanner scan =  new Scanner(System.in);
        while(scan.hasNextLine()){
            String inputString = scan.nextLine();

            //check if match the format or already exist
            try {
                if(inputString.equals("Y")){}
                else if(inputString.equals("N")){
                    goBack = true;
                    break;
                }
            }catch (Exception e){
                System.out.println("Invalid input please try again");
                continue;
                }
            }
        //scan.close();
    }

    public void InvaLidEmail(){
        System.out.println("Email address need to match the format\nPlease try again\n");
    }

    public void deleteUserFromFile(String username) {
        try{

        //temp file
        File oFile = File.createTempFile("tempUserFileForDelete",".csv");

        //input
        FileInputStream fileInputStream = new FileInputStream(userCsvFile);
        BufferedReader inReader = new BufferedReader(new InputStreamReader(fileInputStream));

        //outfile
        FileOutputStream fos = new FileOutputStream(oFile);
        PrintWriter out = new PrintWriter(fos);
        String thisline;
        int i =0;
        while ((thisline = inReader.readLine()) != null){
            String fields[] = thisline.split(",");

            //skips row if invalid amount of fields
            if (fields.length < 7 ) {
              continue;
            }

            if (!fields[2].equals(username)){
                out.println(thisline);
                i++;
            }
        }
        out.flush();
        out.close();
        inReader.close();
        userCsvFile.delete();
        oFile.renameTo(userCsvFile);
            System.out.println(userCsvFile.getName()+"final");
            System.out.println(oFile.getName());



    }catch (Exception e){
            System.out.println("exception!!!1");
        e.printStackTrace();}
    }


    // check the username is a staff or not
    // if the username is a staff return 1
    // else return 0
    // -2 for the file not found exception
    public int doesStaffExistInCSV(String fileName, String userEmail){


        //check file follows right format...
        try{ Scanner myReader = new Scanner(this.userCsvFile);
            while (myReader.hasNextLine()) { //as long as you can keep reading the file, grab the details
                String line = myReader.nextLine();
                String[] detailsArray = line.split(",");

                //TODO more checks on bool? Maybe a validate file fntion.

                //checks valid amount of fields otherwise movies to next line
                if (detailsArray.length < 7) {
                    continue;
                }

                //checks if valid ID, if not it will continue to next line
                try{
                    Integer.parseInt(detailsArray[0]);
                } catch(NumberFormatException e){
                    continue;
                }

                String email = detailsArray[2];
                if(userEmail.equals(email) && detailsArray[6].equals("STAFF")){
                    myReader.close();
                    haveAuser = true;
                    return 1;
                }

            }
            myReader.close();
            return 0;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return -2;
    }

}
