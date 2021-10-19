package R18_G2_ASM2.SeatDataTools;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import R18_G2_ASM2.Cinema;
import R18_G2_ASM2.Movie;
import R18_G2_ASM2.Screen;
import R18_G2_ASM2.SeatMap;
import R18_G2_ASM2.Showing;

public class Main {
    public static void main(String[] args) throws IOException{
        List<String> colNames;
        // colNames = Arrays.asList("col_1", "col_2", "col_3", "col_4", "col_5", "col_6", "col_7");
        colNames = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        String[][] data = { { "Available", "Available",  "Available", "Available", "Available", "Available", "Available" },
                { "Reserved", "Available", "Available", "Available", "Available", "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Available" , "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Reserved" , "Available", "Available"},
                { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"}};
        MovieDataFrame filmAvailable = new MovieDataFrame(colNames, data);
        filmAvailable.print();

        // System.out.println(filmAvailable.getRow(1).getValues());


        System.out.println("\033[1;93;45m"+ "hello"+"\033[m");
        
        // File f = new File("mydata.csv");


        // FileTools.writeToCsv(filmAvailable, f);
        
        // DataFrame mdf =  FileTools.readFromCsv(f);

        // mdf.print();

        SeatMap seatMap = new SeatMap(new Showing(1, new Movie(1,"77", null, null, null, null ), new Cinema(1, Screen.GOLD), null));
        DataFrame<String> newFrame = seatMap.readFromDatabase();
        newFrame.print();
        seatMap.getSeatMap().setValue(3, "2", "Reserved");
        seatMap.writeToDatabase();
        seatMap.readFromDatabase().print();
    }
}
