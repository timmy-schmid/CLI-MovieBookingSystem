package R18_G2_ASM2.SeatDataTools;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        File f = new File("mydata.csv");
        // FileTools ft= new FileTools();
        FileTools.writeToCsv(filmAvailable, f);
        
        DataFrame mdf =  FileTools.readFromCsv(f);
        // DataFrame<Double> smaller2 = df.select(row -> !row.getValue("year").equals(2017d));
        // DataFrame<String> smaller1 = mdf.project("1");
        mdf.print();
        // smaller1.print();
    }
}
