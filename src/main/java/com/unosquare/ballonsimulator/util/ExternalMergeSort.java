package com.unosquare.ballonsimulator.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.unosquare.ballonsimulator.model.RecordBallon;

/**
 * ExternalSort
 */
public class ExternalMergeSort {

    private final File TMP_DIRECTORY = new File("tmp");

    private int chunks;

    public void sortDataset(String filePath) throws IOException {
        removeTmpFiles();
        splitDataset(filePath);

        File[] miniDatasets = TMP_DIRECTORY.listFiles();
        for (File miniDataset : miniDatasets) {
            List<RecordBallon> recordsBallon = new ArrayList<>(Consts.BATCH_SIZE);

            // reading a batch from the disk
            try (BufferedReader reader = new BufferedReader(new FileReader(miniDataset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    RecordBallon recordBallon = new RecordBallon(line);
                    recordsBallon.add(recordBallon);
                }
            }

            Collections.sort(recordsBallon);
            // writing the sorted batch in the disk
            writeBatch(miniDataset, false, recordsBallon);
        }

        //put mini batches in memory
        int miniBatchSize = Consts.BATCH_SIZE / (chunks + 1);
        Map<Integer, LinkedList<RecordBallon>> miniBatches = new LinkedHashMap<>();
        Map<Integer, BufferedReader> readers = new HashMap<>();
        int indexBatch = 0;
        for (File miniDataset : miniDatasets) {
            BufferedReader reader = new BufferedReader(new FileReader(miniDataset));
            readers.put(indexBatch, reader);
            miniBatches.put(indexBatch, getNextMiniBatch(reader, miniBatchSize));
            indexBatch++;
        }

        //merge mini batches in a single file
        File datasetMerged = new File(TMP_DIRECTORY + "/datasetsorted.txt");
        List<RecordBallon> batchMerged = new ArrayList<>(miniBatchSize);
        while (!miniBatches.isEmpty()) {

            // get lowest item from all mini batches
            RecordBallon lowestRecordBallon = null;
            for (Map.Entry<Integer, LinkedList<RecordBallon>> batch : miniBatches.entrySet()) {
                if (lowestRecordBallon == null) {
                    lowestRecordBallon = batch.getValue().getFirst();
                    indexBatch = batch.getKey();
                } else {
                    if (batch.getValue().getFirst().compareTo(lowestRecordBallon) < 0) {
                        lowestRecordBallon = batch.getValue().getFirst();
                        indexBatch = batch.getKey();
                    }
                }
            }

            batchMerged.add(lowestRecordBallon);
            // write merged items of current mini batch
            if (batchMerged.size() == miniBatchSize) {
                writeBatch(datasetMerged, true, batchMerged);
                batchMerged.clear();
            }

            miniBatches.get(indexBatch).removeFirst();
            // get next mini batch of file
            if (miniBatches.get(indexBatch).isEmpty()) {

                // if file does not have more items, it is excluded
                List<RecordBallon> newMiniBatch = getNextMiniBatch(readers.get(indexBatch), miniBatchSize);
                if (newMiniBatch.isEmpty()) {
                    readers.get(indexBatch).close();
                    readers.remove(indexBatch);
                    miniBatches.remove(indexBatch);
                }
            }
        }

        // write items remaining
        if (!batchMerged.isEmpty()) {
            writeBatch(datasetMerged, true, batchMerged);
        }
    }

    private void removeTmpFiles() {
        File[] contents = TMP_DIRECTORY.listFiles();
        if (contents != null) {
            for (File f : contents) {
                f.delete();
            }
        }
        TMP_DIRECTORY.delete();
    }

    private void writeBatch(File file, boolean append, List<RecordBallon> recordsBallon) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
            for (RecordBallon recordBallon : recordsBallon) {
                bw.write(recordBallon.toString() + "\n");
            }
        }
    }

    private LinkedList<RecordBallon> getNextMiniBatch(BufferedReader reader, int miniBatchSize) {
        LinkedList<RecordBallon> miniBatch = new LinkedList<>();
        try {
            if (reader.ready()) {
                for (String line; (line = reader.readLine()) != null;) {
                    RecordBallon recordBallon = new RecordBallon(line);
                    miniBatch.add(recordBallon);
                }
            }
        } catch (IOException e) {

        }
        return miniBatch;

    }

    private void splitDataset(String datasetPath) throws IOException {

        TMP_DIRECTORY.mkdirs();

        int maxlines = Consts.BATCH_SIZE;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        int indexBatch = 0;
        try {
            reader = new BufferedReader(new FileReader(datasetPath));

            int count = 0;
            for (String line; (line = reader.readLine()) != null;) {
                if (count++ % maxlines == 0) {
                    indexBatch++;
                    if (writer != null) {
                        writer.close();
                    }
                    writer = new BufferedWriter(new FileWriter(TMP_DIRECTORY + "/batch" + indexBatch + ".txt"));
                }
                writer.write(line);
                writer.newLine();
            }
        } finally {
            writer.close();
            reader.close();
        }
        this.chunks = indexBatch;
    }

}