package R18_G2_ASM2;
import java.util.regex.Pattern;

public abstract class UserValidation {

  //checks for user email + password are done here to ensure it satisfies acceptance criteria 
  
  public static boolean validateUser(String email){
    //should contain: @ + .com
    if (email == null){
      return false;
    }
    String emailRegex = "^.*\\w@(gmail|hotmail|yahoo)\\.com$";
    Pattern pattern = Pattern.compile(emailRegex);
    if (pattern.matcher(email).matches()){
      return true;
    } else {
      System.out.println("Please enter an email that contains a recipient name, @ symbol and valid domain.");
      return false;
    }
  }

  public static boolean isValidPassword(String password){
    //now, use regex to ensure it contains a mixture of letters + numbers + symbols (--> optional?, allow whitespace or NAH?)    
    if (password == null){
      return false;
    }
    String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{10,}$";
    Pattern pattern = Pattern.compile(passwordRegex);
    if (pattern.matcher(password).matches()){
      return true;
    } else {
      //Your password did not satisfy acceptance criteria. 
      System.out.println("Please enter a 10-digit password containing at least 1 capital letter and 1 number.");
      return false;
    }
  }

  public static boolean isValidPhoneNumber(String phoneNumber){ //ensures only numbers <n> digits
    if (phoneNumber == null){
      return false;
    }
    String pnumberRegex = "^\\d{10}$";
    Pattern pattern = Pattern.compile(pnumberRegex);

    if (pattern.matcher(phoneNumber).matches()){
      return true;
    } else {
      System.out.println("Please enter a 10 digit phone number.");
      return false;
    }
  }
  public static boolean isValidCardNumber(String number){ //did the user enter a correct card number that satisfies acceptance criteria?
    if (number == null){
      return false;
    }
    String cnumberRegex = "^\\d{5}$";
    Pattern pattern = Pattern.compile(cnumberRegex);

    if (pattern.matcher(number).matches()){
      return true;
    } else {
      System.out.println("Please enter a 5 digit card number.");
      return false;
    }
  }

  public static boolean isValidGiftCardNumber(String number){ //did the user enter a correct gift card number that satisfies acceptance criteria?
    if (number == null){
      return false;
    }
    String gnumberRegex = "^\\d{14}GC$$";
    Pattern pattern = Pattern.compile(gnumberRegex);
  
    if (pattern.matcher(number).matches()){
      return true;
    } else {
      System.out.printf("Please enter a 14 digit number followed by GC suffix: ");
      return false;
    }
  }
}
