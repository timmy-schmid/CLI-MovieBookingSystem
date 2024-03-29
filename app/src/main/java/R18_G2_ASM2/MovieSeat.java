package R18_G2_ASM2;

import java.io.File;
import java.io.FileNotFoundException;
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
    private File tempMovieSeat;
    private int frontRowNum;

    private int totalFrontSeat;
    private int totalMiddleSeat;
    private int totalRearSeat;
    public MovieSeat(Showing showing) throws IOException{
        this.showing = showing;
        movieSeat = DataController.accessCSVFile("movieSeatsMap/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getId())+"-"+String.valueOf(showing.getShowingId())+".csv");

        //create dir first if does not exist!
        if (!movieSeat.getParentFile().exists()) {
          movieSeat.getParentFile().mkdirs();
        }

        tempMovieSeat = DataController.accessCSVFile("movieSeatsMap/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getId())+"-"+String.valueOf(showing.getShowingId())+"-temp"+".csv");
        if (tempMovieSeat.exists()){
            tempMovieSeat.renameTo(movieSeat);
        }
        
        if (!movieSeat.exists()){
            try {
                movieSeat.createNewFile();
                seatMap = FileTools.readFromCsv(DataController.accessCSVFile("movieSeatsMap/"+showing.getCinema().cinemaScreen.toString()+".csv"));
                writeToDatabase(movieSeat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        seatMap = readFromDatabase(movieSeat);
        frontRowNum = seatMap.getRowCount()/3;

        totalFrontSeat = frontRowNum*seatMap.getColumnCount();
        totalRearSeat = frontRowNum*seatMap.getColumnCount();
        totalMiddleSeat = seatMap.getRowCount()*seatMap.getColumnCount()-totalFrontSeat-totalRearSeat;
    }

    public MovieSeat(Showing showing, boolean isUnderTest) throws IOException{
        this.showing = showing;
        movieSeat = DataController.accessCSVFile("movieSeatsMap/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getId())+"-"+String.valueOf(showing.getShowingId())+".csv");
        movieSeat = new File("src/test/resources/"+ "SeatMapTest.csv");

        seatMap = readFromDatabase(movieSeat);
        frontRowNum = seatMap.getRowCount()/3;

        totalFrontSeat = frontRowNum*seatMap.getColumnCount();
        totalRearSeat = frontRowNum*seatMap.getColumnCount();
        totalMiddleSeat = seatMap.getRowCount()*seatMap.getColumnCount()-totalFrontSeat-totalRearSeat;
    }

    public void writeToDatabase(File csvFile) throws IOException{
        FileTools.writeToCsv(seatMap, csvFile);
    }

    public DataFrame<String> readFromDatabase(File csvFile) throws IOException{
        return FileTools.readFromCsv(csvFile);
    }

    public DataFrame<String> getSeatMap(){
        return seatMap;
    }

    public boolean bookSeat(char rowLetter, int colNum) throws IOException{
        if (Character.getNumericValue(rowLetter)-10 >25 || Character.getNumericValue(rowLetter)-10 < 0){
            throw new IllegalArgumentException();
        }
        else if (seatMap.getValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum)).equals("Reserved")){
            return false;
        }
        seatMap.setValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum), "Reserved");
        writeToDatabase(movieSeat);
        return true;
    }

    public boolean cancelReservation(char rowLetter, int colNum) throws IOException{
        if (Character.getNumericValue(rowLetter)-10 >25 || Character.getNumericValue(rowLetter)-10 < 0){
            throw new IllegalArgumentException();
        }
        else if (seatMap.getValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum)).equals("Available")){
            return false;
        }
        seatMap.setValue(Character.getNumericValue(rowLetter)-10, String.valueOf(colNum), "Available");
        writeToDatabase(movieSeat);
        return true;
    }


    public void showAllSeats(){
        DataFrame<String> tempSeatMap;
        File tempMovieSeat;
        try {
            tempMovieSeat = DataController.accessCSVFile("movieSeatsMap/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getId())+"-"+String.valueOf(showing.getShowingId())+"-temp"+".csv");
        
        if (!tempMovieSeat.exists()){

            tempMovieSeat.createNewFile();
            tempSeatMap = FileTools.readFromCsv(DataController.accessCSVFile("movieSeatsMap/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getId())+"-"+String.valueOf(showing.getShowingId())+".csv"));
            writeToDatabase(tempMovieSeat);

        }
        tempSeatMap = readFromDatabase(tempMovieSeat);


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        seatMap.print(frontRowNum-1, seatMap.getRowCount()-frontRowNum-1, seatMap.getRowCount()-1);
        // seatMap.print(0, seatMap.getRowCount()-1);
    }

    public void showFrontSeats(){


        seatMap.print(frontRowNum-1, seatMap.getRowCount()-frontRowNum-1, seatMap.getRowCount()-1);
    }

    public void showMiddleSeats(){
        // seatMap.print(frontRowNum, seatMap.getRowCount()-frontRowNum-1);
        seatMap.print(frontRowNum-1, seatMap.getRowCount()-frontRowNum-1, seatMap.getRowCount()-1);
    }

    public void showRearSeats(){
        // seatMap.print(seatMap.getRowCount()-frontRowNum, seatMap.getRowCount()-1);
        seatMap.print(frontRowNum-1, seatMap.getRowCount()-frontRowNum-1, seatMap.getRowCount()-1);
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
        return middleSeatBooked()+frontSeatBooked()+ rearSeatBooked();
    }
    
    public int totalSeatsLeft() {
        return totalFrontSeat+totalMiddleSeat+totalRearSeat-totalSeatsBooked();
    }

    public void completeTransaction(){
        tempMovieSeat.delete();
    }

    public void resetSeatMap(){
        try {
            tempMovieSeat = DataController.accessCSVFile("movieSeatsMap/"+ String.valueOf(showing.getMovie().getId())+"-"+ String.valueOf(showing.getCinema().getId())+"-"+String.valueOf(showing.getShowingId())+"-temp"+".csv");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (tempMovieSeat.exists()){
            tempMovieSeat.renameTo(movieSeat);
        }
    }


    // public static void main(String[] args) throws IOException{
    //     List<String> colNames;
    //     // colNames = Arrays.asList("col_1", "col_2", "col_3", "col_4", "col_5", "col_6", "col_7");
    //     colNames = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
    //     String[][] data = { { "Available", "Available",  "Available", "Available", "Available", "Available", "Available" },
    //             { "Reserved", "Available", "Available", "Available", "Available", "Available", "Available"},
    //             { "Available", "Available", "Available", "Reserved", "Available" , "Available", "Available"},
    //             { "Available", "Available", "Available", "Reserved", "Reserved" , "Available", "Available"},
    //             { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"},
    //             { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"}};
    //     MovieDataFrame filmAvailable = new MovieDataFrame(colNames, data);
    //     // filmAvailable.print(0,4);
    
    //     // System.out.println(filmAvailable.getRow(1).getValues());
    
    
    //     System.out.println("\033[1;93;45m"+ "hello"+"\033[m");
    
    //     // mdf.print();
    //     // File movieSeat = new File("src/test/resources/"+ "SeatMapTest.csv");
    
    //     MovieSeat seatMap = new MovieSeat(new Showing(2, new Movie(1,"77", null, null, null, null,null), new Cinema(1, MovieClass.SILVER), null));
    //     seatMap.writeToDatabase();
    //     // newFrame.print();
    //     System.out.println(seatMap.totalSeatsLeft()==0);

    //     seatMap.bookSeat('D', 5);

    //     seatMap.showFrontSeats();
    //     seatMap.showMiddleSeats();
    //     seatMap.showRearSeats();

    //     seatMap.showAllSeats();
    //     seatMap.cancelReservation('D', 5);
    //     System.out.println(seatMap.cancelReservation('A', 1));
    //     seatMap.showAllSeats();

    //     System.out.println("Front seats booked: "+seatMap.frontSeatBooked());
    //     System.out.println("Middle seats booked: "+seatMap.middleSeatBooked());
    //     System.out.println("Total seats left: "+seatMap.totalSeatsLeft());
    //     System.out.println("Total seats booked: "+seatMap.totalSeatsBooked());



    // }

    
}