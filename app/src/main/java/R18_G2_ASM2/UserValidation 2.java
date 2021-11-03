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
      System.out.println("Your email did not satisfy acceptance criteria.");
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
      System.out.println("Your password did not satisfy acceptance criteria.");
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
      System.out.println("Your phone number did not satisfy acceptance criteria.");
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
      System.out.println("Your card number did not satisfy acceptance criteria.");
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
      System.out.println("Your gift card number did not satisfy acceptance criteria.");
      return false;
    }
  }
}
