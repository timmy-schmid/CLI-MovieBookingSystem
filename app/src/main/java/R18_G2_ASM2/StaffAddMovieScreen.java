package R18_G2_ASM2;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class StaffAddMovieScreen {
  private HashMap<Integer,Movie> movies = new HashMap<>();
  private PrintStream outt;
  private File movieCsvFile;
  private static String MOVIES_FILE_NAME = "movie.csv";
  private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");

  public StaffAddMovieScreen() {
    try {
      movieCsvFile = DataController.accessCSVFile(MOVIES_FILE_NAME);
    } catch (FileNotFoundException e) {
      System.out.println("Unable to edit User: " + e.getMessage());
      return;
    }
  }

  public void retrieveMovieInfo() throws Exception {
    int count = 1;
    String title = null;
    String synopsis = null;
    String classification = null;
    String releaseDateInput = null;
    String actor = "";
    String option = null;
    String director = "";
    boolean flag = true;
    String movieinfo = null;

    Scanner sc = new Scanner(System.in);
    System.out.println("*************************************************************************");
    System.out.println("                                ADD    MOVIE                             ");
    System.out.println("*************************************************************************");
    System.out.printf("Please enter a movie title: ");
    title = sc.nextLine();
    System.out.printf("Please enter a movie synopsis: ");
    synopsis = sc.nextLine();
    while(true) {
      System.out.printf("Please enter a classification (G,PG,M,MA,R): ");
      classification = sc.nextLine();
      if(!(classification.equals("G") || classification.equals("PG")
          || classification.equals("M") || classification.equals("MA") || classification.equals("R"))) {
        System.out.println("Invalid input, please try again!");
      } else {
        break;
      }
    }

    while(true) {
      System.out.printf("Please enter a release date <yy-mm-dd>: ");
      releaseDateInput = sc.nextLine();
      Calendar releaseDate = Calendar.getInstance(AEST,Locale.ENGLISH);
      try {
        releaseDate.setTime(formatter.parse(releaseDateInput));
        break;
      } catch (ParseException e) {
        System.out.println("Invalid input, please try again! ");
        continue;
      }
    }

    while(true) {
      if(count == 1) {
        System.out.printf("Please enter an actor: ");
        actor = sc.nextLine();
        while(true) {
          System.out.printf("Do you want to enter another actor? y/n ");
          option = sc.nextLine();
          if(option.equalsIgnoreCase("y")) {
            break;
          } else if(option.equalsIgnoreCase("n")) {
            flag = false;
            break;
          } else {
            System.out.printf("Invalid input, please try again!");
            continue;
          }
        }
        count ++;
      } else {
        System.out.printf("Please enter an actor: ");
        actor = actor + ":" + sc.nextLine();
        while(true) {
          System.out.printf("Do you want to enter another actor? y/n ");
          option = sc.nextLine();
          if(option.equalsIgnoreCase("y")) {
            break;
          } else if(option.equalsIgnoreCase("n")) {
            flag = false;
            break;
          } else {
            System.out.printf("Invalid input, please try again!");
            continue;
          }
        }
      }
      if(flag == true) {
        continue;
      } else {
        break;
      }
    }
    count = 1;
    flag = true;

    while(true) {
      if(count == 1) {
        System.out.printf("Please enter an director: ");
        director = sc.nextLine();
        while(true) {
          System.out.printf("Do you want to enter another director? y/n ");
          option = sc.nextLine();
          if(option.equalsIgnoreCase("y")) {
            break;
          } else if(option.equalsIgnoreCase("n")) {
            flag = false;
            break;
          } else {
            System.out.printf("Invalid input, please try again!");
            continue;
          }
        }
        count ++;
      } else {
        System.out.printf("Please enter an director: ");
        director = director + ":" + sc.nextLine();
        while(true) {
          System.out.printf("Do you want to enter another director? y/n ");
          option = sc.nextLine();
          if(option.equalsIgnoreCase("y")) {
            break;
          } else if(option.equalsIgnoreCase("n")) {
            flag = false;
            break;
          } else {
            System.out.printf("Invalid input, please try again!");
            continue;
          }
        }
      }
      if(flag == true) {
        continue;
      } else {
        break;
      }
    }

//    movieinfo = (movies.size() + 1) + "," + title + "," + actor + "," + classification + "," + director + "," + synopsis + "," + releaseDateInput;
    movieinfo = title + "," + actor + "," + classification + "," + director + "," + synopsis + "," + releaseDateInput;
//    System.out.println(movieinfo);
//    writeToCsv(movieinfo, movieCsvFile);
    insertStringInFile(movieCsvFile, movies.size()+2, movieinfo);
    System.out.println("Movie successfully entered!");
  }

  public void writeToCsv(String movieInfo, File output) throws IOException {
    FileOutputStream fos = new FileOutputStream(output);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    bw.write(movieInfo);
    bw.newLine();
    bw.close();
  }

  public void insertStringInFile(File inFile, int lineno, String lineToBeInserted) throws Exception {
    // temp file
    File outFile = File.createTempFile("temp", ".csv");
    // input
    FileInputStream fis = new FileInputStream(inFile);
    BufferedReader in = new BufferedReader(new InputStreamReader(fis));
    // output
    FileOutputStream fos = new FileOutputStream(outFile);
    PrintWriter out = new PrintWriter(fos);
    // save one line
    String thisLine;
    int i = 1;
    while ((thisLine = in.readLine()) != null) {
      out.println(thisLine);
      i++;
    }
    lineToBeInserted = (i-1) + "," + lineToBeInserted;
    out.println(lineToBeInserted);
    out.flush();
    out.close();
    in.close();
    // delete original file
    inFile.delete();
    // change temp file name to original one
    outFile.renameTo(inFile);
  }

//  public void importMovieData() {
//    try {
//      DataController.importMovies(movies,MOVIES_FILE_NAME);
//    } catch (IOException e) {
//      outt.println("Error reading file: " + MOVIES_FILE_NAME);
//    }
//
//  }


}