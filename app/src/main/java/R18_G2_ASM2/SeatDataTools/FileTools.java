package R18_G2_ASM2.SeatDataTools;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class FileTools {
    public static void writeToCsv(DataFrame<String> data, File output) throws IOException {
        FileOutputStream fos = new FileOutputStream(output);
     
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
     
        for (int i = 0; i < data.getRowCount(); i++) {
            for (int j = 0; j < data.getColumnCount(); j++){
                bw.write(data.getValue(i, String.valueOf(j+1)));
                if (j < data.getColumnCount()-1){
                    bw.write(",");
                }
            }
            bw.newLine();
        }
        bw.close();
    }

    public static DataFrame <String> readFromCsv(File input) throws IOException {
     
        
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String currentLine;
        int rowCount = 0;
        int colNum = 0;
        int rowNum = 0;
        while ((currentLine = reader.readLine()) != null) {
            colNum = currentLine.split(",").length;
            rowNum++;
        }
        List<String> names = new ArrayList<>();
        for (int i = 0; i < colNum; i++){
            names.add(String.valueOf(i+1));
        }
        reader.close();
        String[][] dataFromFile = new String[rowNum][colNum];
        reader = new BufferedReader(new FileReader(input));
        while ((currentLine = reader.readLine()) != null) {
            dataFromFile[rowCount]=currentLine.split(",");
            rowCount++;
        }

        
        reader.close();
        DataFrame<String> dataFrame = new MovieDataFrame(names, dataFromFile);
        return dataFrame;
    }    
}