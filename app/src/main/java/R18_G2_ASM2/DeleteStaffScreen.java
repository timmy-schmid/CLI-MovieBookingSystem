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
                    deleteUserFromFile(nameToDelete);
                    goBack = true;
                    break;
            }
        }
    };

    public void  EmailnotExist(){
        System.out.println("User don't exist\n");
    }


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
                }
                // if not correct format ask again;
            }catch (Exception e){
                if(e.getMessage().equals("1")){
                    InvaLidEmail();
                    continue;
                }else if(e.getMessage().equals("2")){
                    EmailnotExist();
                    continue;
                }
                continue;
            }

            nameToDelete = inputString;
            haveAuser = true;
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
        File oFile = File.createTempFile("tempUserForDelete",".csv");


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



    }catch (Exception e){
        e.printStackTrace();}
    }

}
