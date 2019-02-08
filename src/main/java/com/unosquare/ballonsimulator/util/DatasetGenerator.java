package com.unosquare.ballonsimulator.util;

import java.io.BufferedWriter;
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

    public void execute() throws IOException {

        int chunks = (int) Math.ceil((float) recordsAmount / Consts.BATCH_SIZE);

        for (int iChunk = 0; iChunk < chunks; iChunk++) {
            int tempAmount = 0;
            if (iChunk == chunks - 1) {
                tempAmount = recordsAmount % Consts.BATCH_SIZE;
            } else {
                tempAmount = Consts.BATCH_SIZE;
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.outputFile, true))) {
                for (int iRecord = 0; iRecord < tempAmount; iRecord++) {
                    bw.write(RecordBallon.createRandomRecord().toString() + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}