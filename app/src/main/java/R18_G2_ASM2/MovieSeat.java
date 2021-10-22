package R18_G2_ASM2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import R18_G2_ASM2.SeatDataTools.DataFrame;
import R18_G2_ASM2.SeatDataTools.FileTools;
import R18_G2_ASM2.SeatDataTools.MovieDataFrame;

public class MovieSeat{
    private Showing showing;
    private DataFrame<String> seatMap;
    private File movieSeat;
    private int frontRowNum;

    private int totalFrontSeat;
    private int totalMiddleSeat;
    private int totalRearSeat;
    public MovieSeat(Showing showing) throws IOException{
        this.showing = showing;

        movieSeat = new File("src/main/datasets/movieSeatsMap/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getId())+"-"+String.valueOf(showing.getShowingId())+".csv");
        // movieSeat = new File("app/src/main/datasets/movieSeatsMap/BRONZE.csv");

        if (!movieSeat.exists()){
            try {
                movieSeat.createNewFile();
                seatMap = FileTools.readFromCsv(new File("src/main/datasets/movieSeatsMap/"+showing.getCinema().cinemaScreen.toString()+".csv"));
                writeToDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        seatMap = readFromDatabase();
        frontRowNum = seatMap.getRowCount()/3;

        totalFrontSeat = frontRowNum*seatMap.getColumnCount();
        totalRearSeat = frontRowNum*seatMap.getColumnCount();
        totalMiddleSeat = seatMap.getRowCount()*seatMap.getColumnCount()-totalFrontSeat-totalRearSeat;
    }

    public MovieSeat(Showing showing, boolean isUnderTest) throws IOException{
        this.showing = showing;

        movieSeat = new File("src/test/resources/"+ "SeatMapTest.csv");

        seatMap = readFromDatabase();
        frontRowNum = seatMap.getRowCount()/3;

        totalFrontSeat = frontRowNum*seatMap.getColumnCount();
        totalRearSeat = frontRowNum*seatMap.getColumnCount();
        totalMiddleSeat = seatMap.getRowCount()*seatMap.getColumnCount()-totalFrontSeat-totalRearSeat;
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

    public boolean bookSeat(char rowLetter, int colNum) throws IOException{
        if (Character.getNumericValue(rowLetter)-10 >25 || Character.getNumericValue(rowLetter)-10 < 0){
            throw new IllegalArgumentException();
        }
        else if (seatMap.getValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum+1)).equals("Reserved")){
            return false;
        }
        seatMap.setValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum+1), "Reserved");
        writeToDatabase();
        return true;
    }

    public boolean cancelReservation(char rowLetter, int colNum) throws IOException{
        if (Character.getNumericValue(rowLetter)-10 >25 || Character.getNumericValue(rowLetter)-10 < 0){
            throw new IllegalArgumentException();
        }
        else if (seatMap.getValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum+1)).equals("Available")){
            return false;
        }
        seatMap.setValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum+1), "Available");
        writeToDatabase();
        return true;
    }


    public void showAllSeats(){
        seatMap.print(0, seatMap.getRowCount()-1);
    }

    public void showFrontSeats(){
        seatMap.print(0, frontRowNum-1);
    }

    public void showMiddleSeats(){
        seatMap.print(frontRowNum, seatMap.getRowCount()-frontRowNum-1);
    }

    public void showRearSeats(){
        seatMap.print(seatMap.getRowCount()-frontRowNum, seatMap.getRowCount()-1);
    }

    public int rearSeatBooked(){
        int seatBooked = 0;
        for (int i = seatMap.getRowCount()-1-frontRowNum; i< seatMap.getRowCount(); i++){
            for (String value : seatMap.getRow(i).getValues()){
                if (value.equals("Reserved")){
                    seatBooked++;
                }
            }   
        }
        
        return seatBooked;
    }

    public int frontSeatBooked(){
        int seatBooked = 0;
        for (int i = 0; i< frontRowNum; i++){
            for (String value : seatMap.getRow(i).getValues()){
                if (value.equals("Reserved")){
                    seatBooked++;
                }
            }   
        }
        
        return seatBooked;
    }

    public int middleSeatBooked(){
        int seatBooked = 0;
        for (int i = frontRowNum; i< seatMap.getRowCount()-1-frontRowNum; i++){
            for (String value : seatMap.getRow(i).getValues()){
                if (value.equals("Reserved")){
                    seatBooked++;
                }
            }   
        }
        
        return seatBooked;
    }

    public int totalSeatsBooked() {
        return middleSeatBooked()+frontSeatBooked()+rearSeatBooked();
    }
    
    public int totalSeatsLeft() {
        return totalFrontSeat+totalMiddleSeat+totalRearSeat-totalSeatsBooked();
    }


    
}