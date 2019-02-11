package com.unosquare.ballonsimulator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.unosquare.ballonsimulator.model.RecordBallon;

/**
 * DatasetGenerator
 */
public class DatasetGenerator {

    private int recordsAmount;
    private String outputFile;

    public DatasetGenerator(String outputFile, int recordsAmount) {
        this.outputFile = outputFile;
        this.recordsAmount = recordsAmount;
    }

    /**
     * Generates a dataset with random RecordBallons items, the amount of record must be provided in constructor
     * @throws IOException if there is any issue when written the dataset
     */
    public void execute() throws IOException {

        // remove old file if exists
        File file = new File(this.outputFile);
        if (file.exists()) {
            file.delete();
        }

        int chunks = (int) Math.ceil((float) recordsAmount / Consts.BATCH_SIZE);

        for (int iChunk = 0; iChunk < chunks; iChunk++) {
            int tempAmount = 0;
            if (iChunk == chunks - 1 && recordsAmount % Consts.BATCH_SIZE != 0) {
                tempAmount = recordsAmount % Consts.BATCH_SIZE;
            } else {
                tempAmount = Consts.BATCH_SIZE;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                for (int iRecord = 0; iRecord < tempAmount; iRecord++) {
                    bw.write(RecordBallon.createRandomRecord().toString() + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}