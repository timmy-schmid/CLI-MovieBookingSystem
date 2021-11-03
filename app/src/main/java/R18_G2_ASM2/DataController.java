package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  private static String basepath = "src/main/resources/";
  private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
  private static final TimeZone AEST = TimeZone.getTimeZone("Australia/Sydney");
  
  public static File accessCSVFile(String resource) throws FileNotFoundException {

    //checks if resource is actually .csv
    if (resource == null || !resource.endsWith(".csv")) {
      throw new FileNotFoundException("Invalid filename or type provided:" + resource);
    }

    File f = null;
    String path = "";

    if (Files.exists(Paths.get(basepath))) { // checks gradle dir v1
      path = basepath + resource;
      f = new File(path);
      //System.out.println("src: ABS PATH to copy to: "+ f.getAbsoluteFile().toPath());
    } else if (Files.exists(Paths.get("app/" + basepath))) { // checks gradle dir v2
      path = "app/" + basepath + resource;
      f = new File(path);
      //System.out.println("app: ABS PATH to copy to: "+ f.getAbsoluteFile().toPath());

    } else {
      // if not Gradle, must be .jar. Check to to see if db files exist.
      // The path of the jar file is located.
      try {
        String parentPath = new File(DataController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        f = new File(parentPath + "/" + resource);
        //System.out.println(".JAR ABS PATH to copy to: "+ f.getAbsoluteFile().toPath());
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      //If there is no db file, we copy the resource contents from the .jar to create a new db file.
      if (!f.exists()) {
        InputStream in = DataController.class.getClassLoader().getResourceAsStream(resource);
        try {
          Files.copy(in,f.getAbsoluteFile().toPath());
        } catch (IOException e) {
          return f; // if copy fails, then return f.
        }
      }
    }
    return f;    
  }

  public static File accessJSONFile(String resource) {

    //checks if resource is actually .csv
    if (resource == null || !resource.endsWith(".json")) { //mb throw an exception.
      System.out.println("Invalid filename provided:" + resource);
      return null;
    }

    File f = null;
    String path = "";

    if (Files.exists(Paths.get(basepath))) { // checks gradle dir v1
      path = basepath + resource;
      f = new File(path);
    } else if (Files.exists(Paths.get("app/" + basepath))) { // checks gradle dir v2
      path = "app/" + basepath + resource;
      f = new File(path);
    } else {
      // if not Gradle, must be .jar. Check to to see if db files exist.
      // The path of the jar file is located.
      try {
        String parentPath = new File(DataController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
        f = new File(parentPath + "/" + resource);
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }

      //If there is no db file, we copy the resource contents from the .jar to create a new db file.
      if (!f.exists()) {
        InputStream in = DataController.class.getClassLoader().getResourceAsStream(resource);
        try {
          Files.copy(in,f.getAbsoluteFile().toPath());
        } catch (IOException e) {
          return f; // if copy fails, then return f.
        }
      }
    }
    return f;
  }


  public static void setBasePath(String s) {
    basepath = s;
  }

  public static void printErrorMap(Map<Integer, String> errors) {

    System.out.println("There were errors reading from the following lines:");
    for (Map.Entry<Integer,String> entry: errors.entrySet()) {
      System.out.format("  Line %d: %s\n",entry.getKey(),entry.getValue());
    }
  }
  public static HashMap<Integer, String> importMovies(Map<Integer,Movie> movies, String filename) throws FileNotFoundException, IOException {
    HashMap<Integer, String> err = new HashMap<>();
  
    if (filename == null) throw new FileNotFoundException();
    
    BufferedReader br = new BufferedReader(new FileReader(DataController.accessCSVFile(filename)));

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

    BufferedReader br = new BufferedReader(new FileReader(DataController.accessCSVFile(filename)));

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
      MovieClass screen = null;
      try {
        screen = MovieClass.parseScreen(fields[1]);
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
                             String filename) throws FileNotFoundException, IOException {
    
                              HashMap<Integer, String> err = new HashMap<>();
  
    if (filename == null) throw new FileNotFoundException();

    BufferedReader br = new BufferedReader(new FileReader(DataController.accessCSVFile(filename)));

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
      }
    
      //validate showingTime
      Calendar showingTime = Calendar.getInstance(AEST,Locale.ENGLISH);
      showingTime.setTimeInMillis(Long.parseLong(fields[2]));
  
      movies.get(movieId).addShowing(new Showing(lineNum, movies.get(movieId), cinemas.get(cinemaId),showingTime));
      lineNum++;
    }
    br.close();
    return err;
  }
}
