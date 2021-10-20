package R18_G2_ASM2;
import java.util.regex.Pattern;

public abstract class UserFields {

  public boolean validateUser(String email){ //or email
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

  public boolean isValidPassword(String password){
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
}
