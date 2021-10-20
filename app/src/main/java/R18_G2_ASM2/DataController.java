package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.naming.directory.InvalidAttributeValueException;

import java.util.Arrays;

public class DataController {

  //int id, String name, List<String> cast, Classification classification, List<String> directors, String synopsis,Calendar releaseDate) {

  private static String basepath = "src/main/datasets/";
  private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");
  
  public static void setBasePath(String s) {
    basepath = s;
  }

  public static void printErrorMap(Map<Integer, String> errors) {

    System.out.println("There were errors reading from the following lines:");
    for (var entry: errors.entrySet()) {
      System.out.format("  Line %d: %s\n",entry.getKey(),entry.getValue());
    }
  }

  public static HashMap<Integer, String> importMovies(Map<Integer,Movie> movies, String filename) throws FileNotFoundException, IOException {
    HashMap<Integer, String> err = new HashMap<>();
  
    if (filename == null) throw new FileNotFoundException();

    BufferedReader br = new BufferedReader(new FileReader(new File(basepath + filename)));

    String line;
    int lineNum = 0;

    while ((line = br.readLine()) != null) {

      // skips the header row.
      if (lineNum == 0) {
        lineNum++;
        continue;
      } 

      /*
        Splits on commas, except in the case where comma's are inside a field
        and escaped by quotations. It works by checking there is an odd # of
        quotes between them and end of line.
      */
      String fields[] = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
      int movieId = -1;

      //checks if fields missing
      if (fields.length < 7) {
        err.put(lineNum, "the movie is missing fields");
        lineNum++;
        continue;
      }

      //validate Id field
      try {
        movieId = Integer.parseInt(fields[0]);
        if (movieId < 0) throw new NumberFormatException();

      } catch (NumberFormatException e) {
        err.put (lineNum, "movieID '" + fields[0] + "' is not a positive integer");
        lineNum++;
        continue;
      }
      
      List<String> cast = Arrays.asList(fields[2].split(":"));
      List<String> directors = Arrays.asList(fields[4].split(":"));

      //validate classification
      Classification classification = null;
      try {
        classification = Classification.parseClassification(fields[3]);
      } catch (InvalidClassificationException e) {
        err.put(lineNum, e.getMessage());
        lineNum++;
        continue; 
      }

      //validate releaseDate
      Calendar releaseDate = Calendar.getInstance(AEST,Locale.ENGLISH);
      try {
        releaseDate.setTime(formatter.parse(fields[6]));
      } catch (ParseException e) {
        err.put(lineNum, e.getMessage());
        lineNum++;
        continue; 
      }

      movies.put(movieId, new Movie(movieId, fields[1],cast,classification,directors,fields[5],releaseDate));
      lineNum++;
    }
    br.close();
    return err;
  }

  public static HashMap<Integer, String> importCinemas(Map<Integer,Cinema> cinemas, String filename) throws FileNotFoundException, IOException {
    HashMap<Integer, String> err = new HashMap<>();
  
    if (filename == null) throw new FileNotFoundException();

    BufferedReader br = new BufferedReader(new FileReader(new File(basepath + filename)));

    String line;
    int lineNum = 0;

    while ((line = br.readLine()) != null) {

      // skips the header row.
      if (lineNum == 0) {
        lineNum++;
        continue;
      } 

      String fields[] = line.split(",");
      int cinemaId = -1;

      //checks if fields missing
      if (fields.length < 2) {
        err.put(lineNum, "the cinema is missing fields");
        lineNum++;
        continue; 
      }
      //validate Id field
      try {
        cinemaId = Integer.parseInt(fields[0]);
        if (cinemaId < 0) throw new NumberFormatException();

      } catch (NumberFormatException e) {
        err.put (lineNum, "cinemaID '" + fields[0] + "' is not a positive integer");
        lineNum++;
        continue;
      }
      
      //validate screen
      Screen screen = null;
      try {
        screen = Screen.parseScreen(fields[1]);
      } catch (InvalidScreenException e) {
        err.put(lineNum, e.getMessage());
        lineNum++;
        continue; 
      }

      cinemas.put(cinemaId, new Cinema(cinemaId, screen));
      lineNum++;
    }
    br.close();
    return err;
  }
  public static HashMap<Integer, String> importShowings(Map<Integer,Movie> movies,
                                                        Map<Integer,Cinema> cinemas,
                                                        Map<Integer,Showing> showings,
                                                        String filename)
                                                        throws FileNotFoundException, IOException {
    HashMap<Integer, String> err = new HashMap<>();
  
    if (filename == null) throw new FileNotFoundException();

    BufferedReader br = new BufferedReader(new FileReader(new File(basepath + filename)));

    String line;
    int lineNum = 0;

    while ((line = br.readLine()) != null) {

      // skips the header row.
      if (lineNum == 0) {
        lineNum++;
        continue;
      } 

      String fields[] = line.split(",");
      int movieId, cinemaId = -1;

      //checks if fields missing
      if (fields.length < 3) {
        err.put(lineNum, "the showing is missing fields");
        lineNum++;
        continue;
      }

      //validate movieId field
      try {
        movieId = Integer.parseInt(fields[0]);

        if (movies.get(movieId) == null) throw new InvalidAttributeValueException();
        if (movieId < 0) throw new NumberFormatException();

      } catch (NumberFormatException e) {
        err.put (lineNum, "movieID '" + fields[0] + "' is not a positive integer");
        lineNum++;
        continue;
      } catch (InvalidAttributeValueException e) {
        err.put (lineNum, "movieID '" + fields[0] + "' does not exist in db");
        lineNum++;
        continue;
      }

      //validate Id field
      try {
        cinemaId = Integer.parseInt(fields[1]);
        if (cinemaId < 0) throw new NumberFormatException();
        if (cinemas.get(cinemaId) == null) throw new InvalidAttributeValueException();

      } catch (NumberFormatException e) {
        err.put (lineNum, "cinemaID '" + fields[1] + "' is not a positive integer");
        lineNum++;
        continue;
      } catch (InvalidAttributeValueException e) {
        err.put (lineNum, "cinemaID '" + fields[1] + "' does not exist in db");
        lineNum++;
        continue;
      } finally {

      }
    
      //validate showingTime
      Calendar showingTime = Calendar.getInstance(AEST,Locale.ENGLISH);
      showingTime.setTimeInMillis(Long.parseLong(fields[2]));
   
      showings.put(lineNum, new Showing(lineNum, movies.get(movieId), cinemas.get(cinemaId),showingTime));
      lineNum++;
    }
    br.close();
    return err;
  }
}
