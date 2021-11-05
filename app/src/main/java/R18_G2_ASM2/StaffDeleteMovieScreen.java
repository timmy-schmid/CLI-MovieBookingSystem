package R18_G2_ASM2;

import java.util.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class StaffDeleteMovieScreen {
  private HashMap<Integer,Movie> movies = new HashMap<>();
  private PrintStream outt;
  private File movieCsvFile;
  private static String MOVIES_FILE_NAME = "movie.csv";

  public StaffDeleteMovieScreen() {
    try {
      movieCsvFile = DataController.accessCSVFile(MOVIES_FILE_NAME);
      importMovieData();
    } catch (FileNotFoundException e) {
      System.out.println("Unable to edit User: " + e.getMessage());
      return;
    }
  }

  public void retrieveMovieInfo() throws Exception {
    System.out.println("*************************************************************************");
    System.out.println("                             DELETE    MOVIE                             ");
    System.out.println("*************************************************************************");
    deleteStringInFile(movieCsvFile);
  }

  public void writeToCsv(String movieInfo, File output) throws IOException {
    FileOutputStream fos = new FileOutputStream(output);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    bw.write(movieInfo);
    bw.newLine();
    bw.close();
  }

  public void deleteStringInFile(File inFile) throws Exception {
    String option = null;
    String title = null;
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.printf("Please enter a movie title to delete: (validate if title exists) ");
      title = sc.nextLine();
      boolean flag = true;
      boolean flag2 = false;
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
        String fields[] = thisLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        if (!fields[1].equals(title)) {
          out.println(thisLine);
          i++;
        } else {
          flag2 = true;
          continue;
        }
      }
      out.flush();
      out.close();
      in.close();
      // delete original file
      inFile.delete();
      // change temp file name to original one
      outFile.renameTo(inFile);
      if (flag2 == false) {
        System.out.println("Movie not exist!");
        while (true) {
          System.out.printf("Do you want to try again? y/n ");
          option = sc.nextLine();
          if (option.equalsIgnoreCase("y")) {
            break;
          } else if (option.equalsIgnoreCase("n")) {
            flag = false;
            break;
          } else {
            System.out.println("Invalid input, please try again!");
            continue;
          }
        }
        if(flag == true) {
          continue;
        } else {
          break;
        }
      } else {
        System.out.println("Movie successfully deleted!");
        break;
      }
    }
  }

  public void importMovieData() {
    try {
      DataController.importMovies(movies,MOVIES_FILE_NAME);
    } catch (IOException e) {
      System.out.println("Error reading file: " + MOVIES_FILE_NAME);
    }

  }


}