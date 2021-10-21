package R18_G2_ASM2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import R18_G2_ASM2.SeatDataTools.DataFrame;
import R18_G2_ASM2.SeatDataTools.MovieDataFrame;

class MainTest{

    public static void main(String[] args) throws IOException{
        List<String> colNames;
        // colNames = Arrays.asList("col_1", "col_2", "col_3", "col_4", "col_5", "col_6", "col_7");
        colNames = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        String[][] data = { { "Available", "Available",  "Available", "Available", "Available", "Available", "Available" },
                { "Reserved", "Available", "Available", "Available", "Available", "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Available" , "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Reserved" , "Available", "Available"},
                { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"},
                { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"}};
        MovieDataFrame filmAvailable = new MovieDataFrame(colNames, data);
        filmAvailable.print(0,4);
    
        // System.out.println(filmAvailable.getRow(1).getValues());
    
    
        System.out.println("\033[1;93;45m"+ "hello"+"\033[m");
    
        // mdf.print();
        // File movieSeat = new File("src/test/resources/"+ "SeatMapTest.csv");
    
        MovieSeat seatMap = new MovieSeat(new Showing(2, new Movie(1,"77", null, null, null, null,null), new Cinema(1, Screen.SILVER), null));
        DataFrame<String> newFrame = seatMap.readFromDatabase();
        seatMap.writeToDatabase();
        // newFrame.print();

        seatMap.bookSeat('D', 5);
    
        seatMap.showAllSeats();
        System.out.println(seatMap.frontSeatBooked());
        System.out.println(seatMap.middleSeatBooked());
        System.out.println(seatMap.totalSeatsLeft());
        System.out.println(seatMap.totalSeatsBooked());
    }
}