/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package R18_G2_ASM2;

import java.util.HashMap;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {

        HashMap<Integer,Movie> movies = new HashMap<>();
        HashMap<Integer,Cinema> cinemas = new HashMap<>();
        HashMap<Integer,Showing> showings = new HashMap<>();

        HashMap<Integer,String> errors1;
        HashMap<Integer,String> errors2;
        HashMap<Integer,String> errors3;

        DataController d = new DataController();

        errors1 = d.importMovies(movies);
        errors2 = d.importCinemas(cinemas);
        errors3 = d.importShowings(movies,cinemas,showings);

        System.out.println("Errors found while importing movies");
        for (HashMap.Entry<Integer, String> entry : errors1.entrySet()) {
            System.out.println("  " + entry.getKey() + ":" + entry.getValue());
        }

        System.out.println("\n--------\n");

        System.out.println("Errors found while importing cinemas");
        for (HashMap.Entry<Integer, String> entry : errors2.entrySet()) {
            System.out.println("  " + entry.getKey() + ":" + entry.getValue());
        }
        
        System.out.println("\n--------\n");

        System.out.println("Errors found while importing showings");
        for (HashMap.Entry<Integer, String> entry : errors3.entrySet()) {
            System.out.println("  " + entry.getKey() + ":" + entry.getValue());
        }

        //System.out.println("\n---MOVIES-----\n");
        System.out.println("\n--------\n");
        StringBuilder s = new StringBuilder();
        movies.get(4).toString(s);
        s.append("\n");
        Showing.getMovieShowings(showings,s,movies.get(4));
        System.out.println(s);
    

        /*
        for (HashMap.Entry<Integer, Movie> entry : movies.entrySet()) {
            System.out.println(entry.getValue().toString());
        }

        System.out.println("\n---SHOWINGS-----\n");
        for (HashMap.Entry<Integer, Showing> entry : showings.entrySet()) {
            System.out.println(entry.getValue().toString());
        }*/
    }
}
