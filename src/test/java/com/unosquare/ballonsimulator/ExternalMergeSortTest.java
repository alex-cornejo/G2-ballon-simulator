package com.unosquare.ballonsimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.unosquare.ballonsimulator.model.RecordBallon;
import com.unosquare.ballonsimulator.util.ExternalMergeSort;

import org.junit.Test;

/**
 * ExternalMergeSortTest
 */
public class ExternalMergeSortTest {

    @Test
    public void sortDatasetTest() {
        List<RecordBallon> dataset = new ArrayList<>();
        String datasetPath = "src/test/java/testdataset.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(datasetPath));
            for (String line; (line = br.readLine()) != null;) {
                RecordBallon recordBallon = new RecordBallon(line);
                dataset.add(recordBallon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            assertEquals(true, false);
        }

        try {
            ExternalMergeSort ems = new ExternalMergeSort();
            ems.sortDataset(datasetPath);
        } catch (IOException ex) {
            ex.printStackTrace();
            assertEquals(true, false);
        }

        List<RecordBallon> datasetSorted = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("tmp/datasetsorted.txt"));
            for (String line; (line = br.readLine()) != null;) {
                RecordBallon recordBallon = new RecordBallon(line);
                datasetSorted.add(recordBallon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            assertEquals(true, false);
        }

        assertEquals(dataset.size(), datasetSorted.size());

        // check dataset was sorted
        for (int i = 0; i < datasetSorted.size() - 1; i++) {
            assertTrue(datasetSorted.get(i).compareTo(datasetSorted.get(i + 1)) < 0);
        }

        // check both datasets are equal
        Collections.sort(dataset);
        assertEquals(dataset, datasetSorted);

    }

}