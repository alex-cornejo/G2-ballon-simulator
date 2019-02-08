package com.unosquare.ballonsimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import com.unosquare.ballonsimulator.util.DatasetGenerator;

import org.junit.Test;

/**
 * DatasetGeneratorTest
 */
public class DatasetGeneratorTest {

    @Test
    public void generateDatasetTest() {
        try {
            int recordsAmount = 908;
            String folderName = "testtmp";
            File folder = new File(folderName);
            folder.mkdirs();

            String testOutputFile = folderName + "/testoutput.txt";
            File tmpFile = new File(testOutputFile);
            DatasetGenerator dsg = new DatasetGenerator(testOutputFile, recordsAmount);
            dsg.execute();

            // output file was created
            assertTrue(tmpFile.exists());

            FileReader input = new FileReader(testOutputFile);
            LineNumberReader count = new LineNumberReader(input);
            while (count.skip(Long.MAX_VALUE) > 0);
            input.close();

            //amount of lines in output file does match with the recordsAmount
            assertEquals(recordsAmount, count.getLineNumber());

            System.out.println("### deleting temporal data ###");
            assertTrue(tmpFile.delete());
            assertTrue(folder.delete());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}