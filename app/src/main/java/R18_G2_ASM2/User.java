package R18_G2_ASM2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class User extends UserValidation {

  protected int ID;
  protected String nickname;
  protected String phoneNumber;
  protected String email; //used to represent the unique username
  protected String password;
  protected UserType userType;

  //current new version - sprint 3
  public User(int ID, String nickname, String email, String phoneNumber, String password){ //extra fields added

    //TODO add null checks for args?
    /*
    if (nickname == null || email == null || phoneNumber == null || phoneNumber.equals("") || password == null || password .equals("")){
      throw new IllegalArgumentException("user cannot have missing fields");
    }*/

    this.ID = ID;
    this.nickname = nickname;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  //getter methods below ~
  public int getID(){
    return this.ID;
  }

  public String getNickname(){
    return this.nickname;
  }
  public String getEmail(){
    return this.email;
  }
  public String getPassword(){
    return this.password;
  }
  public String getPhoneNumber(){
    return this.phoneNumber;
  }
  public UserType getUserType() {
    return this.userType;
  }
  
  //setter methods: e.g. for changing login details ...
  //validate to ensure values to be set to are valid
  public void setID(int ID){
    if (ID >= 0){
      this.ID = ID;
    }
  }
  public void setNickname(String nickname){
    if (nickname != null && !nickname.equals("")) {
      this.nickname = nickname;
    }
  }
  //only set email if its valid
  public void setEmail(String email){
    if (User.validateUser(email)){
      this.email = email;
    }
  }
  public void setPhoneNumber(String phoneNumber){
    if (User.isValidPhoneNumber(phoneNumber)){
      this.phoneNumber = phoneNumber;
    }
  }
  //only set password if its valid
  public void setPassword(String newPassword){
    if (User.isValidPassword(newPassword)){
      this.password = newPassword;
    }
  }

  //checks if userEmail & phoneNumber already exist in .csv file. If there are line errors the line is skipped.
  //throws a FileNotFoundException if not file.
  // returns 0 if no user found, 1 if emailFound, 2 if userPhoneNumber found.
  public static int doesUserExistInCSV(String fileName, String userEmail, String userPhoneNumber) throws FileNotFoundException {

    File userFile;
    userFile = DataController.accessCSVFile(fileName); //throws exception if fileName is not of form *.csv

    //check file follows right format...
    Scanner myReader = new Scanner(userFile);
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
      if(userEmail.equals(email)){
        myReader.close();
        return 1;
      }

      String phoneNumber = detailsArray[3];
      if(userPhoneNumber.equals(phoneNumber)){
        myReader.close();
        return 2;
      }
    }
    myReader.close();
    return 0;
  }

  /*
    Assumes that the user does not already exist in the file.
    Writes the following attributes: ID, nickname, email, phone#, pw, autoSave, userType
  */
  public void writeNewUserToCSV(String fileName) throws FileNotFoundException, IOException {

    File userFile;
    userFile = DataController.accessCSVFile(fileName); //throws exception if fileName is not of form *.csv

    //Appends new user to the end of a file. If no file exists a new file is created
    FileWriter myWriter = new FileWriter(userFile, true); //for appending to existing file
    myWriter.write("\n"+String.valueOf(ID)+","+nickname+","+email+","+phoneNumber+","+password+",F" + "," + getUserType());
    myWriter.close();
  }

  public static int getLastUserIDFromCSV(String fileName) throws FileNotFoundException , IOException {

    File userFile;
    userFile = DataController.accessCSVFile(fileName); //throws exception if fileName is not of form *.csv

    int id = 1;
    String lastLine = "";

    if (userFile.exists()) {     //if file exists and theres data inside
      Scanner myReader = new Scanner(userFile);
      while (myReader.hasNextLine()){
        lastLine = myReader.nextLine();
      }
      myReader.close();
      try{
        id = Integer.parseInt(lastLine.split(",")[0]) + 1; //Increments user ID to be the next ID.
      } catch(NumberFormatException e){
        throw new IOException("Could not read valid ID from user database");
      }
    }
    return id;
  }
}