package R18_G2_ASM2;

import org.junit.jupiter.api.Test;

import R18_G2_ASM2.SeatDataTools.DataFrame;
import R18_G2_ASM2.SeatDataTools.DataVector;
import R18_G2_ASM2.SeatDataTools.FileTools;
import R18_G2_ASM2.SeatDataTools.MovieDataFrame;
import R18_G2_ASM2.SeatDataTools.MovieDataVector;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

class SeatDataToolsTest{

    MovieDataFrame filmAvailable;
    List<String> colNames;
    File movieSeat;
    @BeforeEach
    public void setUp(){
        movieSeat = new File("src/test/resources/"+ "SeatMapTest.csv");
        // colNames = Arrays.asList("col_1", "col_2", "col_3", "col_4", "col_5", "col_6", "col_7");
        colNames = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
        String[][] data = { { "Available", "Available",  "Available", "Available", "Available", "Available", "Available" },
                { "Reserved", "Available", "Available", "Available", "Available", "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Available" , "Available", "Available"},
                { "Available", "Available", "Available", "Reserved", "Reserved" , "Available", "Available"},
                { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"},
                { "Available", "Available", "Available", "Available", "Reserved" , "Available", "Available"}};
        filmAvailable = new MovieDataFrame(colNames, data);
    }

    @Test
    public void movieDataFrameTest() throws IOException{
        assertNotNull(filmAvailable);
        assertNotNull(filmAvailable.getData());
        assertEquals(6, filmAvailable.getRowCount());
        assertEquals(7, filmAvailable.getColumnCount());
        assertEquals(colNames, filmAvailable.getColumnNames());
        assertEquals(filmAvailable.getValue(1, "1"), "Reserved");
        boolean thrown = false;
        try {
            filmAvailable.getValue(1, "9");
            filmAvailable.setValue(1, "9", "Available");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            filmAvailable.getValue(100, "1");
            filmAvailable.setValue(100, "1", "Available");
        } catch (IndexOutOfBoundsException e){
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            filmAvailable.setValue(100, "1", "Available");
        } catch (IndexOutOfBoundsException e){
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            filmAvailable.setValue(1, "9", "Available");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);


        filmAvailable.setValue(1, "1", "Reserved");
        assertEquals(filmAvailable.getValue(1, "1"), "Reserved");
        FileTools ft = new FileTools();
        FileTools.writeToCsv(filmAvailable, movieSeat);
        FileTools.readFromCsv(movieSeat);

        String[][] newData = {{"Available"},{"Reserved"}};
        MovieDataFrame newFilmAvailable = new MovieDataFrame(Arrays.asList("1"), newData);
        newFilmAvailable.print(0, 0);
    }

    @Test
    public void movieDataVectorTest(){
        assertNotNull(filmAvailable.getColumn("1"));
        assertNotNull(filmAvailable.getRow(1));

        boolean thrown = false;
        try {
            filmAvailable.getColumn("10");
        } catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue(thrown);

        assertNotNull(filmAvailable.getRows());
        assertNotNull(filmAvailable.getColumns());

        DataVector<String> mdv= filmAvailable.getRow(1);
        assertNotNull(mdv.getName());
        assertNotNull(mdv.getEntryNames());
        assertNotNull(mdv.getValues());
        assertNotNull(mdv.getValue("1"));
        assertNotNull(mdv.asMap());

        thrown = false;
        try {
            mdv.getValue("10");
        } catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue(thrown);
        mdv.print();



    }



}