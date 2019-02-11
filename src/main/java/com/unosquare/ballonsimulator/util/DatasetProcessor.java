package com.unosquare.ballonsimulator.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.unosquare.ballonsimulator.model.Observatory;
import com.unosquare.ballonsimulator.model.RecordBallon;
import com.unosquare.ballonsimulator.model.Observatory.ObservatoryEnum;

/**
 * DatasetProcessor
 */
public class DatasetProcessor {

    private float minTemp;
    private float maxTemp;
    private float avgTemp;
    private Map<ObservatoryEnum, Integer> observatories;
    private float dist;

    public DatasetProcessor() {
        minTemp = Float.MAX_VALUE;
        maxTemp = Float.MIN_VALUE;
        avgTemp = 0;
        dist = 0;
        observatories = new HashMap<>();
        observatories.put(ObservatoryEnum.AU, 0);
        observatories.put(ObservatoryEnum.US, 0);
        observatories.put(ObservatoryEnum.FR, 0);
        observatories.put(ObservatoryEnum.OTHER, 0);
    }

    /**
     * Sorted dataset is read from the disk line by line and next the requirements are computed.
     * min temperature 
     * max temperature 
     * average of temperature 
     * total items per observatory in the dataset 
     * total distance travelled
     * 
     * Final result is printed and logged.
     * It assumes the input dataset is sorted
     *
     * @param inputFile large dataset to be processed
     * @param outputFile output of the result
     * @param obsDesired observatory unit desired
     * @throws IOException
     */
    public void processDataset(String inputFile, String outputFile, ObservatoryEnum obsDesired) throws IOException {

        RecordBallon previousRecord = null;
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            for (String line; (line = reader.readLine()) != null;) {
                RecordBallon recordBallon = new RecordBallon(line);
                if (previousRecord == null) {
                    previousRecord = recordBallon;
                } else {
                    // computing distance from the previous location
                    dist += DistanceUtil.computeEucDist(
                            Observatory.getDistEquiv(previousRecord.getObservatory(), previousRecord.getLocation(),
                                    obsDesired),
                            Observatory.getDistEquiv(recordBallon.getObservatory(), recordBallon.getLocation(),
                                    obsDesired));
                    previousRecord = recordBallon;
                }
                float tempObsDesired = Observatory.getTempEquiv(recordBallon.getObservatory(),
                        recordBallon.getTemperature(), obsDesired);

                // accumulating temperature
                avgTemp += tempObsDesired;

                // looking for min temperature
                if (minTemp > tempObsDesired) {
                    minTemp = tempObsDesired;
                }

                // looking for max temperature
                if (maxTemp < tempObsDesired) {
                    maxTemp = tempObsDesired;
                }

                // accumulating records per observatory
                observatories.merge(recordBallon.getObservatory(), 1, Integer::sum);
                count++;
            }
        }

        // computing temperature average
        avgTemp /= count;

        printDetails(obsDesired);
        logDetails(outputFile, obsDesired);
    }

    private void printDetails(ObservatoryEnum obsDesired) {
        System.out.println("\nData is in " + obsDesired + " observatory...\n");
        System.out.println("Minimum temperature registered " + minTemp + " " + obsDesired.getTempUnit());
        System.out.println("Maximum temperature registered " + maxTemp + " " + obsDesired.getTempUnit());
        System.out.println("Mean temperature " + avgTemp + " " + obsDesired.getTempUnit());
        System.out.println("Number of observations per observatory:");
        for (Map.Entry<ObservatoryEnum, Integer> observatory : observatories.entrySet()) {
            System.out.println(observatory.getKey() + ": " + observatory.getValue());
        }
        System.out.println("Total distance travelled " + dist + " " + obsDesired.getDistUnit());
    }

    private void logDetails(String outputFile, ObservatoryEnum obsDesired) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write("\nData is in " + obsDesired + " observatory...\n\n");
        writer.write("Minimum temperature registered " + minTemp+" "+obsDesired.getTempUnit());
        writer.newLine();
        writer.write("Maximum temperature registered " + maxTemp+" "+obsDesired.getTempUnit());
        writer.newLine();
        writer.write("Mean temperature " + avgTemp+" "+obsDesired.getTempUnit());
        writer.newLine();
        writer.write("Number of observations per observatory:");
        writer.newLine();
        for (Map.Entry<ObservatoryEnum, Integer> observatory : observatories.entrySet()) {
            writer.write(observatory.getKey() + ": " + observatory.getValue());
            writer.newLine();
        }
        writer.write("Total distance travelled " + dist+" "+obsDesired.getDistUnit());
        writer.close();
    }

    public float getMinTemp() {
        return minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public float getAvgTemp() {
        return avgTemp;
    }

    public Map<ObservatoryEnum, Integer> getObservatories() {
        return observatories;
    }

    public float getDist() {
        return dist;
    }
}