package R18_G2_ASM2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import R18_G2_ASM2.SeatDataTools.DataFrame;
import R18_G2_ASM2.SeatDataTools.FileTools;
import R18_G2_ASM2.SeatDataTools.MovieDataFrame;

public class movieSeat{
    private Showing showing;
    private DataFrame<String> seatMap;
    private File movieSeat;
    public movieSeat(Showing showing) throws IOException{
        this.showing = showing;

        movieSeat = new File("app/src/main/datasets/movieSeats/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getCinemaId())+"-"+String.valueOf(showing.getShowingId())+".csv");
        
        // List<String> colNames = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        // String[][] data = { { "Available", "Available",  "Available", "Available", "Available", "Available", "Available" },
        //         { "Reserved", "Available", "Available", "Available", "Available", "Available", "Available"},
        //         { "Available", "Available", "Available", "Reserved", "Available" , "Available", "Available"},
        //         { "Available", "Available", "Available", "Reserved", "Reserved" , "Available", "Available"},
        //         { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"}};
        // seatMap = new MovieDataFrame(colNames, data);
        seatMap = readFromDatabase();
        
    }

    public void writeToDatabase() throws IOException{
        FileTools.writeToCsv(seatMap, movieSeat);
    }

    public DataFrame<String> readFromDatabase() throws IOException{
        return FileTools.readFromCsv(movieSeat);
    }

    public DataFrame<String> getSeatMap(){
        return seatMap;
    }
}