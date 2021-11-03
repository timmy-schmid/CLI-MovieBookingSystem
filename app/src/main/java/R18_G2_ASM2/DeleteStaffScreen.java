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

    public DeleteStaffScreen(MovieSystem movieSystem){
        this.movieSystem = movieSystem;
        this.checkinguser=movieSystem.getUser();
    }
    public void run() {
        while(!goBack){
            askForemail();
            if(haveAuser){
                try{
                deleteUserFromFile(nameToDelete);}
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            askForContinue();
        }
    };
    public void askForemail(){
        System.out.println("Please enter a staff email: ");
        Scanner scan =  new Scanner(System.in);
        while(scan.hasNextLine()){
            String inputString = scan.nextLine();

            //check if match the format or already exist
            try {
                if(!checkinguser.validateUser(inputString)){
                    throw new Exception("1");
                }if(checkinguser.doesUserExistInCSV(USER_FILE_NAME,inputString,"9000010000") == 1){
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
           nameToDelete = inputString;
            break;
        }
        scan.close();
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
        scan.close();
    }

    public void emailExist(){
        System.out.println("Email address already exists\nPlease try again\n");
    }
    public void InvaLidEmail(){
        System.out.println("Email address need to match the format\nPlease try again\n");
    }

    public void deleteUserFromFile(String username) throws IOException {
        File userCsvFile = new File(USER_FILE_NAME);
        //temp file
        File oFile = File.createTempFile("tempUserForDelete",".csv");

        //input
        FileInputStream fileInputStream = new FileInputStream(userCsvFile);
        BufferedReader inReader = new BufferedReader(new InputStreamReader(fileInputStream));

        //outfile
        FileOutputStream fos = new FileOutputStream(oFile);
        PrintWriter out = new PrintWriter(fos);
        String thisline;

        int i =1;

        while ((thisline = inReader.readLine()) != null){
            String fields[] = thisline.split(",");
            if (!fields[1].equals(username)){
                out.println(thisline);
                i++;
            }
        }
        out.flush();
        out.close();
        inReader.close();
        userCsvFile.delete();
        oFile.renameTo(userCsvFile);



    }

}
