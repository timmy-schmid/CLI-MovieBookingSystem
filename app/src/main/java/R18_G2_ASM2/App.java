/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package R18_G2_ASM2;


public class App {
    public static void main(String[] args) {
        // Login login = new Login();
        // login.retrieveUserInputDetails();
        Registration reg = new Registration();
        reg.retrieveUserInputDetails();
        MovieSystem system = new MovieSystem();
        system.run();
    }
}
