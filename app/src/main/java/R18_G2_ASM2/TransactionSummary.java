package R18_G2_ASM2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class TransactionSummary {


    public static void printTransactionSummaryTitle() {
        System.out.println("**************************************************************************************************************************");
        System.out.println("                                               Transactions Summary Report                                                ");
        System.out.println("**************************************************************************************************************************");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        System.out.println("ID                  DATE                            USERNAME                                       REASON / STATUS        ");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
    }

    public static void printTransactionSummary(){
        printTransactionSummaryTitle();
        try {
            File summaryReport = DataController.accessCSVFile("transactionSummaryReport"+".csv");

            FileInputStream in = new FileInputStream(summaryReport);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                List<String> strings = Arrays.asList(line.split(","));
                String singleLine = "";
                
                singleLine += String.format("%-15s", strings.get(0));
                singleLine += String.format("%-33s", strings.get(1));
                singleLine += String.format("%-55s", strings.get(2));
                singleLine += String.format("%-15s", strings.get(3));
                System.out.print(singleLine);
                singleLine = "";
                System.out.println();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void writeToTransactionSummaryReport(User user, TransactionType transactionType){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        sdfDate.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
        Date now = new Date();
        String strDate = sdfDate.format(now);

        try {
            File summaryReport = DataController.accessCSVFile("transactionSummaryReport"+".csv");
            //get the previous transaction index
            FileInputStream in = new FileInputStream(summaryReport);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = null, tmp;
            while ((tmp = br.readLine()) != null) {
                strLine = tmp;
            }
    
            int currentID = 0;
            String lastLine = strLine;
            int firstCommaIndex = lastLine.indexOf(",");
            String currentStr = lastLine.substring(0, firstCommaIndex);
            currentID = Integer.parseInt(currentStr)+1;
            in.close(); 

            FileWriter writer = new FileWriter(summaryReport, true);
            writer.write(currentID+","+strDate+","+user.getEmail()+","+transactionType.toString()+"\n");
            writer.flush();
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
        
    }

}
