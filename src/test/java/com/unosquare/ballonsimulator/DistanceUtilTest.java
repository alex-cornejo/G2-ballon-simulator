package com.unosquare.ballonsimulator;

import static org.junit.Assert.assertEquals;

import com.unosquare.ballonsimulator.util.DistanceUtil;

import org.junit.Test;

/**
 * DistanceUtilTest
 */
public class DistanceUtilTest {

    @Test
    public void computeEucDistTest() {
        float delta = 0.001f;
        assertEquals(0, DistanceUtil.computeEucDist(new int[] { 0, 0 }, new int[] { 0, 0 }), delta);
        assertEquals(26, DistanceUtil.computeEucDist(new int[] { -7, -4 }, new int[] { 17, 6 }), delta);
        assertEquals(55.901699f, DistanceUtil.computeEucDist(new int[] { -1, -3 }, new int[] { 54, 7 }), delta);
    }
}