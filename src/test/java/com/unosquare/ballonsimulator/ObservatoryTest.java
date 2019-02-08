package com.unosquare.ballonsimulator;

import static org.junit.Assert.assertEquals;

import com.unosquare.ballonsimulator.model.Observatory;
import com.unosquare.ballonsimulator.model.Observatory.ObservatoryEnum;

import org.junit.Test;

/**
 * ObservatoryTest
 */
public class ObservatoryTest {

    @Test
    public void testTempConversions() {
        float t = 10;
        float delta = 0.001f;

        System.out.println("### testing celsius conversions ###");
        // celsius to fahrenheit
        assertEquals(50f, Observatory.getTempEquiv(ObservatoryEnum.AU, t, ObservatoryEnum.US), delta);

        // celsius to kelvin
        assertEquals(283.15f, Observatory.getTempEquiv(ObservatoryEnum.AU, t, ObservatoryEnum.FR), delta);
        assertEquals(283.15f, Observatory.getTempEquiv(ObservatoryEnum.AU, t, ObservatoryEnum.OTHER), delta);

        System.out.println("### testing fahrenheit conversions ###");
        // fahrenheit to celsius
        assertEquals(-12.2222f, Observatory.getTempEquiv(ObservatoryEnum.US, t, ObservatoryEnum.AU), delta);

        // fahrenheit to kelvin
        assertEquals(260.928f, Observatory.getTempEquiv(ObservatoryEnum.US, t, ObservatoryEnum.FR), delta);
        assertEquals(260.928f, Observatory.getTempEquiv(ObservatoryEnum.US, t, ObservatoryEnum.OTHER), delta);

        System.out.println("### testing kelvin conversions ###");
        // kelvin to celsius
        assertEquals(-263.15f, Observatory.getTempEquiv(ObservatoryEnum.FR, t, ObservatoryEnum.AU), delta);

        // kelvin to fahrenheit
        assertEquals(-441.67f, Observatory.getTempEquiv(ObservatoryEnum.FR, t, ObservatoryEnum.US), delta);

    }

    @Test
    public void testDistConversions() {
        System.out.println("### testing distance conversions ###");

        float dist = 10;
        float delta = 0.001f;

        // km to miles
        assertEquals(6.21371, Observatory.getDistEquiv(ObservatoryEnum.AU, dist, ObservatoryEnum.US), delta);

        // km to meters
        assertEquals(10000, Observatory.getDistEquiv(ObservatoryEnum.AU, dist, ObservatoryEnum.FR), delta);

        // miles to km
        assertEquals(16.0934, Observatory.getDistEquiv(ObservatoryEnum.US, dist, ObservatoryEnum.AU), delta);
        assertEquals(16.0934, Observatory.getDistEquiv(ObservatoryEnum.US, dist, ObservatoryEnum.OTHER), delta);

        // miles to meters
        assertEquals(16093.4, Observatory.getDistEquiv(ObservatoryEnum.US, dist, ObservatoryEnum.FR), delta);

        // meters to km
        assertEquals(0.01, Observatory.getDistEquiv(ObservatoryEnum.FR, dist, ObservatoryEnum.AU), delta);
        assertEquals(0.01, Observatory.getDistEquiv(ObservatoryEnum.FR, dist, ObservatoryEnum.OTHER), delta);

        // meters to miles
        assertEquals(0.00621371, Observatory.getDistEquiv(ObservatoryEnum.FR, dist, ObservatoryEnum.US), delta);
    }

    @Test
    public void testGetObservatoryByCode() {
        System.out.println("### testing get observatory by code ###");

        assertEquals(ObservatoryEnum.AU, Observatory.getObservatory("AU"));

        assertEquals(ObservatoryEnum.US, Observatory.getObservatory("US"));

        assertEquals(ObservatoryEnum.FR, Observatory.getObservatory("FR"));

        assertEquals(ObservatoryEnum.OTHER, Observatory.getObservatory("OTHER"));

        assertEquals(ObservatoryEnum.OTHER, Observatory.getObservatory("ANY"));
    }

}