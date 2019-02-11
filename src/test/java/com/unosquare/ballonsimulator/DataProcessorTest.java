package com.unosquare.ballonsimulator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import com.unosquare.ballonsimulator.model.Observatory.ObservatoryEnum;
import com.unosquare.ballonsimulator.util.DatasetProcessor;

import org.junit.Test;

/**
 * DataProcessorTest
 */
public class DataProcessorTest {

    @Test
    public void processDatasetAUTest() {
        float delta = 0.0001f;
        String outputTest = "outputtest.txt";
        DatasetProcessor dsp = new DatasetProcessor();

        try {
            dsp.processDataset("src/test/java/testdataset2.txt", outputTest, ObservatoryEnum.AU);
        } catch (Exception ex) {
            ex.printStackTrace();
            assertEquals(true, false);
        }
        assertEquals(40, dsp.getMaxTemp(), delta);
        assertEquals(10, dsp.getMinTemp(), delta);
        assertEquals(3, dsp.getDist(), delta);
        assertEquals(25, dsp.getAvgTemp(), delta);
        assertEquals(4, (int) dsp.getObservatories().get(ObservatoryEnum.AU));
        assertTrue(new File(outputTest).delete());
    }

    @Test
    public void processDatasetAllTest() {
        String outputTest = "outputtest.txt";
        DatasetProcessor dsp = new DatasetProcessor();

        try {
            dsp.processDataset("src/test/java/testdataset3.txt", outputTest, ObservatoryEnum.AU);
        } catch (Exception ex) {
            ex.printStackTrace();
            assertEquals(true, false);
        }

        assertEquals(1, (int) dsp.getObservatories().get(ObservatoryEnum.AU));
        assertEquals(1, (int) dsp.getObservatories().get(ObservatoryEnum.US));
        assertEquals(1, (int) dsp.getObservatories().get(ObservatoryEnum.FR));
        assertEquals(1, (int) dsp.getObservatories().get(ObservatoryEnum.OTHER));
        assertTrue(new File(outputTest).delete());

    }
}