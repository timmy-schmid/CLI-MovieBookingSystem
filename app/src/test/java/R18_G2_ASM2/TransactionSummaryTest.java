package R18_G2_ASM2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionSummaryTest {
    @BeforeAll
    public static void setUpFile(){
        File tempSummaryReport;
        File summaryReport;
        try {
            tempSummaryReport = DataController.accessCSVFile("transactionSummaryReport-temp.csv");
            summaryReport = DataController.accessCSVFile("transactionSummaryReport.csv");
        
        
        // tempSummaryReport.createNewFile();
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(tempSummaryReport);
            os = new FileOutputStream(summaryReport);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
        
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Test
    public void testPrint(){
        TransactionSummary.printTransactionSummary();
    }

}
